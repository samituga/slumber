package io.samituga.slumber.heimer.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

class IoUtilsTest {

    @Test
    public void should_return_bytes_from_stream() throws IOException {
        // given
        var bytes = "test".getBytes();
        var inputStream = new ByteArrayInputStream(bytes);
        var servletInputStream = new InputStream() {
            @Override
            public int read() {
                return inputStream.read();
            }
        };

        // when
        var result = IoUtils.toBytes(servletInputStream);

        // then
        assertThat(result).isEqualTo(bytes);
    }

    @Test
    void should_copy_bytes_from_input_stream_to_output_stream() throws IOException {
        // given
        byte[] bytes = "test".getBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // when
        IoUtils.copyTo(inputStream, outputStream);

        // then
        assertThat(outputStream.toByteArray()).isEqualTo(bytes);
    }
}
