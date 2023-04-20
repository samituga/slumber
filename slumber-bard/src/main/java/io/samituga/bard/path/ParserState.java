package io.samituga.bard.path;

public enum ParserState {
    NORMAL,
    INSIDE_SLASH_IGNORING_BRACKETS,
    INSIDE_SLASH_ACCEPTING_BRACKETS
}
