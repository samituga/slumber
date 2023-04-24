package io.samituga.bard.endpoint.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.samituga.bard.endpoint.request.type.MultipartRequestBody;
import io.samituga.bard.endpoint.request.type.RequestAsTypeBody;
import io.samituga.bard.endpoint.request.type.RequestBody;
import io.samituga.jayce.JsonException;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class HttpRequestTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpRequestImpl httpRequest = new HttpRequestImpl(request, "/path/{id}/value");

    @Test
    void should_return_path_params() {
        // given
        given(request.getPathInfo()).willReturn("/path/123/value");

        // when
        var pathParams = httpRequest.pathParams();

        // then
        assertThat(pathParams.get("id")).isEqualTo("123");
    }

    @Test
    void should_return_empty_path_params_if_no_match() {
        // given
        given(request.getPathInfo()).willReturn("/no/match");

        // when
        var pathParams = httpRequest.pathParams();

        // then
        assertThat(pathParams.value()).isEmpty();
    }

    @Test
    void should_return_query_params() {
        // given
        given(request.getQueryString()).willReturn("param1=value1&param2=value2");

        // when
        var queryParams = httpRequest.queryParams();

        // then
        assertThat(queryParams.value()).containsEntry("param1", Set.of("value1"));
        assertThat(queryParams.value()).containsEntry("param2", Set.of("value2"));
    }

    @Test
    void should_return_empty_query_params_when_query_string_is_null() {
        // given
        given(request.getQueryString()).willReturn(null);

        // when
        var queryParams = httpRequest.queryParams();

        // then
        assertThat(queryParams.value()).isEmpty();
    }

    @Test
    void should_return_empty_query_params_when_query_string_is_empty() {
        // given
        given(request.getQueryString()).willReturn("");

        // when
        var queryParams = httpRequest.queryParams();

        // then
        assertThat(queryParams.value()).isEmpty();
    }

    @Test
    void should_return_headers() {
        // given
        given(request.getHeaderNames()).willReturn(
              Collections.enumeration(List.of("Header1", "Header2")));
        given(request.getHeader("Header1")).willReturn("Value1");
        given(request.getHeader("Header2")).willReturn("Value2");

        // when
        var headers = httpRequest.headers();

        // then
        assertThat(headers.get("Header1")).isEqualTo("Value1");
        assertThat(headers.get("Header2")).isEqualTo("Value2");
    }

    @Test
    void should_return_empty_request_body() throws IOException {
        // given
        var mockInputStream = new DelegatingServletInputStream(
              new ByteArrayInputStream(new byte[0]));
        given(request.getInputStream()).willReturn(mockInputStream);

        // when
        Optional<RequestBody> requestBody = httpRequest.body();

        // then
        assertThat(requestBody).isEmpty();
    }

    @Test
    void should_return_request_body() throws IOException {
        // given
        var bodyStringBytes = "Hello World!".getBytes();
        var mockInputStream = new DelegatingServletInputStream(
              new ByteArrayInputStream(bodyStringBytes));
        given(request.getInputStream()).willReturn(mockInputStream);

        // when
        var requestBody = httpRequest.body();

        // then
        assertThat(requestBody).isPresent();
        // TODO: 19/04/2023 Arrays equals() returns reference so can't compare RequestBody directly
        assertThat(requestBody.get().value()).isEqualTo(RequestBody.of(bodyStringBytes).value());
    }

    @Test
    void should_return_multipart_file() throws IOException, ServletException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpRequestImpl httpRequest = new HttpRequestImpl(request, "/path/{id}/value");
        Part mockPart = mock(Part.class);
        given(request.getParts()).willReturn(Set.of(mockPart));
        given(mockPart.getName()).willReturn("fileName");
        given(mockPart.getSubmittedFileName()).willReturn("testFile.txt");
        given(mockPart.getInputStream()).willReturn(
              new ByteArrayInputStream("file content".getBytes()));

        // when
        Optional<MultipartRequestBody> multipartFile = httpRequest.multipartFile("fileName");

        // then
        assertThat(multipartFile).isPresent();
        assertThat(multipartFile.get().filename()).isEqualTo("testFile.txt");
    }

    @Test
    void should_return_empty_optional_when_multipart_file_not_found()
          throws IOException, ServletException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpRequestImpl httpRequest = new HttpRequestImpl(request, "/path/{id}/value");
        Part mockPart = mock(Part.class);
        given(request.getParts()).willReturn(Set.of(mockPart));
        given(mockPart.getName()).willReturn("otherFileName");
        given(mockPart.getSubmittedFileName()).willReturn("testFile.txt");
        given(mockPart.getInputStream()).willReturn(
              new ByteArrayInputStream("file content".getBytes()));

        // when
        Optional<MultipartRequestBody> multipartFile = httpRequest.multipartFile("fileName");

        // then
        assertThat(multipartFile).isEmpty();
    }

    @Test
    void should_return_empty_optional_for_empty_request_body() throws IOException {
        // given
        var mockInputStream = new DelegatingServletInputStream(
              new ByteArrayInputStream(new byte[0]));
        given(request.getInputStream()).willReturn(mockInputStream);

        // when
        Optional<RequestAsTypeBody<String>> requestBody = httpRequest.bodyAsType(
              String.class);

        // then
        assertThat(requestBody).isEmpty();
    }

    @Test
    void should_return_request_body_as_type() throws IOException {
        // given
        var bodyString = """
              {
                "name" : "John Doe",
                "age" : 25
              }
              """;
        var bodyStringBytes = bodyString.getBytes();
        var mockInputStream = new DelegatingServletInputStream(
              new ByteArrayInputStream(bodyStringBytes));
        given(request.getInputStream()).willReturn(mockInputStream);

        // when
        var requestBody = httpRequest.bodyAsType(Person.class);

        // then
        Person expected = new Person("John Doe", 25);

        assertThat(requestBody).isPresent();
        assertThat(requestBody.get().value()).isEqualTo(expected);
    }

    @Test
    void should_throw_exception_for_invalid_request_body_type() throws IOException {
        // given
        var bodyString = """
              {
                "name" : "John Doe",
                "age" : 25
              }
              """;
        var bodyStringBytes = bodyString.getBytes();
        var mockInputStream = new DelegatingServletInputStream(
              new ByteArrayInputStream(bodyStringBytes));
        given(request.getInputStream()).willReturn(mockInputStream);

        // when, then
        assertThatThrownBy(() -> httpRequest.bodyAsType(Car.class))
              .isInstanceOf(JsonException.class);
    }


    private static class DelegatingServletInputStream extends ServletInputStream {
        private final InputStream delegate;

        public DelegatingServletInputStream(InputStream delegate) {
            this.delegate = delegate;
        }

        @Override
        public int read() throws IOException {
            return delegate.read();
        }

        @Override
        public void close() throws IOException {
            delegate.close();
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }
    }

    private record Person(@JsonProperty("name") String name, @JsonProperty("age") int age) {
    }

    private record Car(@JsonProperty("color") String color) {
    }
}
