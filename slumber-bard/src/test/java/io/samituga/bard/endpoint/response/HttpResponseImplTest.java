package io.samituga.bard.endpoint.response;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.samituga.bard.endpoint.ContentType;
import io.samituga.bard.endpoint.response.type.ByteResponseBody;
import io.samituga.bard.endpoint.response.type.InputStreamResponseBody;
import io.samituga.bard.endpoint.response.type.TypeResponseBody;
import io.samituga.jayce.Json;
import io.samituga.slumber.ivern.http.type.Headers;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.Map;

import org.junit.jupiter.api.Test;

class HttpResponseImplTest {

    private final HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

    private final HttpResponseImpl httpResponse = new HttpResponseImpl(httpServletResponse);

    @Test
    void should_set_status_code() {
        // given
        var statusCode = HttpCode.OK;

        // when
        var result = httpResponse.statusCode(statusCode);

        // then
        verify(httpServletResponse, times(1)).setStatus(statusCode.code());
        assertThat(result).isSameAs(httpResponse);
    }

    @Test
    void should_set_headers() {
        // given
        var headers = Headers.of(Map.of("key1", "value1", "key2", "value2"));

        // when
        var result = httpResponse.headers(headers);

        // then
        verify(httpServletResponse, times(1)).setHeader("key1", "value1");
        verify(httpServletResponse, times(1)).setHeader("key2", "value2");
        assertThat(result).isSameAs(httpResponse);
    }

    @Test
    void should_set_response_body_with_byte_array() throws Exception {
        // given
        var httpServletResponse = mock(HttpServletResponse.class);
        var byteArrayOutputStream = new ByteArrayOutputStream();
        var outputStream = new DelegatingServletInputStream(byteArrayOutputStream);

        given(httpServletResponse.getOutputStream()).willReturn(outputStream);

        var bytes = "Hello, world!".getBytes();

        // when
        var response = new HttpResponseImpl(httpServletResponse);
        response.responseBody(ByteResponseBody.of(bytes));

        // then
        verify(httpServletResponse).getOutputStream();
        verify(httpServletResponse).setHeader("Content-Type", ContentType.TEXT_PLAIN.value());
        verifyNoMoreInteractions(httpServletResponse);

        assertThat(bytes.length).isEqualTo(byteArrayOutputStream.toByteArray().length);
        assertThat(bytes).isEqualTo(byteArrayOutputStream.toByteArray());
    }

    @Test
    void should_set_response_body_with_type_response() throws Exception {
        // given
        var httpServletResponse = mock(HttpServletResponse.class);
        var byteArrayOutputStream = new ByteArrayOutputStream();
        var outputStream = new DelegatingServletInputStream(byteArrayOutputStream);

        given(httpServletResponse.getOutputStream()).willReturn(outputStream);

        var type = new Person("John Doe", 25);
        var typeResponseBody = TypeResponseBody.of(type);

        // when
        var response = new HttpResponseImpl(httpServletResponse);
        response.responseBody(typeResponseBody);

        // then
        verify(httpServletResponse).getOutputStream();
        verify(httpServletResponse).setHeader("Content-Type", ContentType.APPLICATION_JSON.value());
        verifyNoMoreInteractions(httpServletResponse);

        var bytes = Json.mapper().writeValueAsBytes(type);

        assertThat(bytes.length).isEqualTo(byteArrayOutputStream.toByteArray().length);
        assertThat(bytes).isEqualTo(byteArrayOutputStream.toByteArray());
    }

    @Test
    void should_throw_exception_when_response_body_input_stream_throws_exception() {
        // given
        var responseBody = new InputStreamResponseBody(new MockedInputStream(true));

        // when / then
        assertThatThrownBy(() -> httpResponse.responseBody(responseBody))
              .isInstanceOf(UncheckedIOException.class);
    }

    private static class MockedInputStream extends ByteArrayInputStream {

        private final boolean throwError;

        public MockedInputStream(boolean throwError) {
            super(new byte[] {});
            this.throwError = throwError;
        }

        @Override
        public synchronized int read(byte[] b, int off, int len) {
            if (throwError) {
                throw new UncheckedIOException(new IOException());
            } else {
                return super.read(b, off, len);
            }
        }
    }

    private static class DelegatingServletInputStream extends ServletOutputStream {
        private final OutputStream delegate;

        public DelegatingServletInputStream(OutputStream delegate) {
            this.delegate = delegate;
        }


        @Override
        public void write(int b) throws IOException {
            delegate.write(b);
        }

        @Override
        public void close() throws IOException {
            delegate.close();
        }


        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
        }
    }

    private record Person(@JsonProperty("name") String name, @JsonProperty("age") int age) {
    }
}
