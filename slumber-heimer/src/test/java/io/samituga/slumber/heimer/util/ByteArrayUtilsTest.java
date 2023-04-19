package io.samituga.slumber.heimer.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ByteArrayUtilsTest {

    @Test
    void should_merge_two_byte_arrays() {
        // given
        byte[] array1 = {1, 2, 3};
        byte[] array2 = {4, 5, 6};

        // when
        byte[] result = ByteArrayUtils.mergeArrays(array1, array2);

        // then
        assertThat(result).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @Test
    void should_merge_empty_byte_arrays() {
        // given
        byte[] array1 = {};
        byte[] array2 = {};

        // when
        byte[] result = ByteArrayUtils.mergeArrays(array1, array2);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void should_merge_one_byte_array_and_empty_byte_array() {
        // given
        byte[] array1 = {1, 2, 3};
        byte[] array2 = {};

        // when
        byte[] result = ByteArrayUtils.mergeArrays(array1, array2);

        // then
        assertThat(result).containsExactly(1, 2, 3);
    }

    @Test
    void should_merge_empty_byte_array_and_one_byte_array() {
        // given
        byte[] array1 = {};
        byte[] array2 = {4, 5, 6};

        // when
        byte[] result = ByteArrayUtils.mergeArrays(array1, array2);

        // then
        assertThat(result).containsExactly(4, 5, 6);
    }
}

