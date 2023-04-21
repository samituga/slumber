package io.samituga.bard.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

class MultipartUtilTest {

    private static final String ERROR_MESSAGE = "jakarta.servlet.ServletException: Unsupported Content-Type [null], expected [multipart/form-data]";
    private final HttpServletRequest request = mock(HttpServletRequest.class);

    private final Part part = mock(Part.class);

    @Test
    void should_get_uploaded_files_by_part_name() throws ServletException, IOException {
        // given
        when(request.getAttribute(MultipartUtil.MULTIPART_CONFIG_ATTRIBUTE)).thenReturn(null);
        when(request.getParts()).thenReturn(List.of(part));
        when(part.getSubmittedFileName()).thenReturn("test.txt");
        when(part.getName()).thenReturn("file");

        // when
        var result = MultipartUtil.getUploadedFiles(request, Optional.of("file"));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).filename()).isEqualTo("test.txt");
    }

    @Test
    void should_get_all_uploaded_files() throws ServletException, IOException {
        // given
        when(request.getAttribute(MultipartUtil.MULTIPART_CONFIG_ATTRIBUTE)).thenReturn(null);
        when(request.getParts()).thenReturn(List.of(part));
        when(part.getSubmittedFileName()).thenReturn("test.txt");

        // when
        var result = MultipartUtil.getUploadedFiles(request);

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).filename()).isEqualTo("test.txt");
    }

    @Test
    void should_get_uploaded_file_map() throws ServletException, IOException {
        // given
        when(request.getAttribute(MultipartUtil.MULTIPART_CONFIG_ATTRIBUTE)).thenReturn(null);
        when(request.getParts()).thenReturn(List.of(part));
        when(part.getSubmittedFileName()).thenReturn("test.txt");
        when(part.getName()).thenReturn("file");

        // when
        var result = MultipartUtil.getUploadedFileMap(request);

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get("file")).isNotEmpty();
        assertThat(result.get("file").get(0).filename()).isEqualTo("test.txt");
    }

    @Test
    void should_return_empty_when_uploaded_files_request_throws_servlet_exception()
          throws ServletException, IOException {
        // given
        when(request.getAttribute(MultipartUtil.MULTIPART_CONFIG_ATTRIBUTE)).thenReturn(null);
        when(request.getParts()).thenThrow(new ServletException(ERROR_MESSAGE));

        // when
        var result = MultipartUtil.getUploadedFiles(request, Optional.of("file"));

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void should_return_empty_when_uploaded_file_maprequest_throws_servlet_exception()
          throws ServletException, IOException {
        // given
        when(request.getAttribute(MultipartUtil.MULTIPART_CONFIG_ATTRIBUTE)).thenReturn(null);
        when(request.getParts()).thenThrow(new ServletException(ERROR_MESSAGE));

        // when
        var result = MultipartUtil.getUploadedFileMap(request);

        // then
        assertThat(result).isEmpty();
    }
}
