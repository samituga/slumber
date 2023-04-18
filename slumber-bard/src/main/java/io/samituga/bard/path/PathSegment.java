package io.samituga.bard.path;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import java.util.Collection;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class PathSegment {
    public static PathSegment createNormal(String string) {
        return new Normal.RegexEscaped(string);
    }

    public static PathSegment.Parameter createSlashIgnoringParam(String string) {
        return new Parameter.SlashIgnoringParameter(string);
    }

    public static PathSegment.Parameter createSlashAcceptingParam(String string) {
        return new Parameter.SlashAcceptingParameter(string);
    }

    private static String grouped(String regex) {
        return "(" + regex + ")";
    }

    protected abstract String asRegexString();

    protected abstract String asGroupedRegexString();

    public Collection<String> pathParamNames() {
        if (this instanceof Normal || this instanceof Wildcard) {
            return emptyList();
        } else if (this instanceof Parameter parameter) {
            return singletonList(parameter.name);
        } else if (this instanceof MultipleSegments multipleSegments) {
            return multipleSegments.innerSegments.stream()
                  .filter(segment -> segment instanceof Parameter)
                  .map(segment -> ((Parameter) segment).name)
                  .toList();
        } else {
            throw new RuntimeException("Unexpected subclass of PathSegment");
        }
    }

    public static abstract class Normal extends PathSegment {
        public final String content;

        public Normal(String content) {
            this.content = content;
        }

        public static final class RegexEscaped extends Normal {
            public RegexEscaped(String content) {
                super(content);
            }

            @Override
            protected String asRegexString() {
                return Pattern.quote(content);
            }

            @Override
            protected String asGroupedRegexString() {
                return Pattern.quote(content);
            }
        }
    }

    public static abstract class Parameter extends PathSegment {
        public final String name;

        public Parameter(String name) {
            this.name = name;
        }

        public static final class SlashIgnoringParameter extends Parameter {
            public SlashIgnoringParameter(String name) {
                super(name);
            }

            @Override
            protected String asRegexString() {
                return "[^/]+?";
            }

            @Override
            protected String asGroupedRegexString() {
                return grouped(asRegexString());
            }
        }

        public static final class SlashAcceptingParameter extends Parameter {
            public SlashAcceptingParameter(String name) {
                super(name);
            }

            @Override
            protected String asRegexString() {
                return ".+?";
            }

            @Override
            protected String asGroupedRegexString() {
                return grouped(asRegexString());
            }
        }
    }

    public static final class Wildcard extends PathSegment {
        @Override
        protected String asRegexString() {
            return ".*?";
        }

        @Override
        protected String asGroupedRegexString() {
            return asRegexString();
        }
    }

    public static final class MultipleSegments extends PathSegment {
        public final Collection<PathSegment> innerSegments;

        public MultipleSegments(Collection<PathSegment> innerSegments) {
            if (innerSegments.stream().anyMatch(segment -> segment instanceof MultipleSegments)) {
                throw new IllegalStateException(
                      "Found MultipleSegment inside MultipleSegments! This is forbidden");
            }

            this.innerSegments = innerSegments.stream()
                  .filter(segment -> !(segment instanceof MultipleSegments))
                  .toList();
        }

        @Override
        protected String asRegexString() {
            return innerSegments.stream()
                  .map(PathSegment::asRegexString)
                  .collect(Collectors.joining());
        }

        @Override
        protected String asGroupedRegexString() {
            return innerSegments.stream()
                  .map(PathSegment::asGroupedRegexString)
                  .collect(Collectors.joining());
        }
    }
}
