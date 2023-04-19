package io.samituga.bard.endpoint.request.type;


import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MultipartRequestBodyTest {

    @Test
    void should_return_input_stream() throws IOException {
        // given
        byte[] content = "Test content".getBytes();
        MultipartRequestBody requestBody = new MultipartRequestBody(
              new TestPart("part-name", "text/plain", content));

        // when
        try (InputStream inputStream = requestBody.inputStream()) {

            // then
            assertThat(inputStream.readAllBytes()).isEqualTo(content);
        }

    }

    @ParameterizedTest
    @ValueSource(strings = {"", "test", "test.txt", "some/file/name.xml"})
    void should_return_filename(String filename) {
        // given
        MultipartRequestBody requestBody = new MultipartRequestBody(
              new TestPart("part-name", "text/plain", new byte[0], filename));

        // when
        String actualFilename = requestBody.filename();

        // then
        assertThat(actualFilename).isEqualTo(filename);
    }

    @Test
    void should_return_file_extension() {
        // given
        MultipartRequestBody requestBody = new MultipartRequestBody(
              new TestPart("part-name", "text/plain", new byte[0], "test.txt"));

        // when
        String extension = requestBody.extension();

        // then
        assertThat(extension).isEqualTo("txt");
    }

    @Test
    void should_return_file_size() {
        // given
        byte[] content = "Test content".getBytes();
        MultipartRequestBody requestBody = new MultipartRequestBody(
              new TestPart("part-name", "text/plain", content));

        // when
        long size = requestBody.size();

        // then
        assertThat(size).isEqualTo(content.length);
    }

    @Test
    void should_return_content_type() {
        // given
        MultipartRequestBody requestBody = new MultipartRequestBody(
              new TestPart("part-name", "text/plain", new byte[0]));

        // when
        Optional<String> contentType = requestBody.contentType();

        // then
        assertThat(contentType).contains("text/plain");
    }

    private static class TestPart implements jakarta.servlet.http.Part {

        private final String name;
        private final String contentType;
        private final byte[] content;
        private final String filename;

        public TestPart(String name, String contentType, byte[] content) {
            this(name, contentType, content, null);
        }

        public TestPart(String name, String contentType, byte[] content, String filename) {
            this.name = name;
            this.contentType = contentType;
            this.content = content;
            this.filename = filename;
        }

        @Override
        public InputStream getInputStream() {
            return new ByteArrayInputStream(content);
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getSubmittedFileName() {
            return filename;
        }

        // Other methods of the Part interface are not used in the tests
        // and can be implemented with empty bodies
        @Override
        public void delete() {}

        @Override
        public String getHeader(String name) {
            return null;
        }

        @Override
        public Collection<String> getHeaderNames() {
            return null;
        }

        @Override
        public Collection<String> getHeaders(String name) {
            return null;
        }

        @Override
        public long getSize() {
            return content.length;
        }

        @Override
        public void write(String fileName) {}

        @Override
        public String toString() {
            return "TestPart{" +
                  "name='" + name + '\'' +
                  ", contentType='" + contentType + '\'' +
                  ", content=" + Arrays.toString(content) +
                  ", filename='" + filename + '\'' +
                  '}';
        }
    }
}
