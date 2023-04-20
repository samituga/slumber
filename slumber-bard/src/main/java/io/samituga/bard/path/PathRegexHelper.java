package io.samituga.bard.path;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PathRegexHelper {

    public static List<Pattern> constructRegexList(boolean matchEverySubPath,
                                                   List<PathSegment> segments,
                                                   String regexSuffix,
                                                   Function<PathSegment, String> mapper) {
        Optional<Pattern> extraWildcardRegexList = addRegexForExtraWildcard(
              matchEverySubPath,
              segments,
              regexSuffix,
              mapper);
        Pattern mainRegex = constructRegex(segments, regexSuffix, mapper);
        return extraWildcardRegexList.map(e -> List.of(e, mainRegex))
              .orElseGet(() -> List.of(mainRegex));
    }

    public static List<String> values(Pattern regex, String url) {
        Matcher matcher = regex.matcher(url);
        if (!matcher.matches()) {
            return Collections.emptyList();
        }
        return IntStream.rangeClosed(1, matcher.groupCount())
              .mapToObj(matcher::group)
              .toList();
    }

    private static Optional<Pattern> addRegexForExtraWildcard(boolean matchEverySubPath,
                                                              List<PathSegment> segments,
                                                              String regexSuffix,
                                                              Function<PathSegment, String> mapper) {
        if (!matchEverySubPath) {
            return Optional.empty();
        }
        List<PathSegment> segmentsWithWildcard = new java.util.ArrayList<>(segments);
        segmentsWithWildcard.add(new PathSegment.Wildcard());
        return Optional.of(constructRegex(segmentsWithWildcard, regexSuffix, mapper));
    }

    private static Pattern constructRegex(List<PathSegment> segments,
                                          String regexSuffix,
                                          Function<PathSegment, String> mapper) {
        String slashRegex = "/";
        String sb = "^/"
              + segments.stream().map(mapper)
              .collect(Collectors.joining(slashRegex))
              + regexSuffix
              + "$";
        return Pattern.compile(sb);
    }
}
