package io.samituga.bard.path.exception;

public class DuplicatedParamNamesException extends RuntimeException{
    public DuplicatedParamNamesException(String rawPath) {
        super("There are duplicated param names: " + rawPath);
    }
}
