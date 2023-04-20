package io.samituga.bard.path.exception;

public class WildcardBracketAdjacentException extends RuntimeException {
    public WildcardBracketAdjacentException(String segment, String rawPath) {
        super("Adjacent wildcard and bracket characters in segment " + segment
              + " in path " + rawPath);
    }
}
