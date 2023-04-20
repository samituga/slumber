package io.samituga.slumber.heimer.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import io.samituga.slumber.heimer.util.IoUtils;
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

}
