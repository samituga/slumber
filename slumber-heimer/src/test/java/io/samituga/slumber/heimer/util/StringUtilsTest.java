package io.samituga.slumber.heimer.util;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class StringUtilsTest {

    private static final String ENCLOSED_BY_BRACKETS = "[value]";
    private static final String NOT_ENCLOSED = "value";

    @Test
    void isEnclosedBy() {
        // given
        // when
        var enclosedByBracketsIsEnclosedByBrackets =
              StringUtils.isEnclosedBy(ENCLOSED_BY_BRACKETS, "[", "]");
        var enclosedByBracketsIsEnclosedByParenthesis =
              StringUtils.isEnclosedBy(ENCLOSED_BY_BRACKETS, "(", ")");
        var notEnclosedIsEnclosedByBrackets = StringUtils.isEnclosedBy(NOT_ENCLOSED, "[", "]");

        // then
        assertThat(enclosedByBracketsIsEnclosedByBrackets).isTrue();
        assertThat(enclosedByBracketsIsEnclosedByParenthesis).isFalse();
        assertThat(notEnclosedIsEnclosedByBrackets).isFalse();
    }

    @Test
    void stripEnclosing() {
        // given
        // when
        var enclosedByBracketWithoutBracketEnclosing =
              StringUtils.stripEnclosing(ENCLOSED_BY_BRACKETS, "[", "]");
        var enclosedByBracketWithoutParenthesisEnclosing =
              StringUtils.stripEnclosing(ENCLOSED_BY_BRACKETS, "(", ")");
        var notEnclosedWithoutBracketEnclosing =
              StringUtils.stripEnclosing(NOT_ENCLOSED, "[", "]");

        // then
        assertThat(enclosedByBracketWithoutBracketEnclosing).isEqualTo("value");
        assertThat(enclosedByBracketWithoutParenthesisEnclosing).isEqualTo("[value]");
        assertThat(notEnclosedWithoutBracketEnclosing).isEqualTo("value");
    }

    @Test
    void should_strip_prefix() {
        // given
        // when
        var enclosedByBracketWithoutBracketPrefix =
              StringUtils.stripPrefix(ENCLOSED_BY_BRACKETS, "[");
        var enclosedByBracketWithoutParenthesisPrefix =
              StringUtils.stripPrefix(ENCLOSED_BY_BRACKETS, "(");

        // then
        assertThat(enclosedByBracketWithoutBracketPrefix).isEqualTo("value]");
        assertThat(enclosedByBracketWithoutParenthesisPrefix).isEqualTo("[value]");
    }

    @Test
    void should_strip_suffix() {
        // given
        // when
        var enclosedByBracketWithoutBracketSuffix =
              StringUtils.stripSuffix(ENCLOSED_BY_BRACKETS, "]");
        var enclosedByBracketWithoutParenthesisSuffix =
              StringUtils.stripPrefix(ENCLOSED_BY_BRACKETS, ")");

        // then
        assertThat(enclosedByBracketWithoutBracketSuffix).isEqualTo("[value");
        assertThat(enclosedByBracketWithoutParenthesisSuffix).isEqualTo("[value]");
    }
}
