package io.samituga.bard.path.exception;

public class MissingBracketsException extends RuntimeException {
    public MissingBracketsException(String segment, String rawPath) {
        super("Missing brackets in segment '" + segment + "' in path '" + rawPath + "'");
    }
}
