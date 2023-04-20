package io.samituga.bard.path;

import static io.samituga.bard.path.PathSegmentParser.convertSegment;
import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import io.samituga.bard.path.exception.DuplicatedParamNamesException;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PathParser {

    private final String rawPath;
    private final String path;
    private final List<PathSegment> segments;
    private final List<String> pathParamNames;
    private final List<Pattern> matchRegex;
    private final List<Pattern> pathParamRegex;

    public PathParser(String rawPath) {
        this.rawPath = required("rawPath", rawPath);
        validatePath(rawPath);

        var matchPathAndEverySubPath = rawPath.endsWith(">*") || rawPath.endsWith("}*");
        this.path = matchPathAndEverySubPath ? rawPath.substring(0, rawPath.length() - 1) : rawPath;

        this.segments = parseSegments();
        this.pathParamNames = extractPathParamNames();
        validatePathParamNames();

        var regexSuffix = computeRegexSuffix();
        this.matchRegex = PathRegexHelper.constructRegexList(
              matchPathAndEverySubPath,
              segments,
              regexSuffix,
              PathSegment::asRegexString);
        this.pathParamRegex = PathRegexHelper.constructRegexList(
              matchPathAndEverySubPath,
              segments,
              regexSuffix,
              PathSegment::asGroupedRegexString);
    }


    public Map<String, String> extractPathParams(String url) {
        var index = IntStream.range(0, matchRegex.size())
              .filter(i -> matchRegex.get(i).matcher(url).matches())
              .findFirst();

        if (index.isEmpty()) {
            return Map.of();
        }

        return IntStream.range(0, pathParamNames.size())
              .boxed()
              .collect(Collectors.toMap(pathParamNames::get,
                    i -> urlDecode(values(pathParamRegex.get(index.getAsInt()), url).get(i))));
    }

    public boolean matches(String url) {
        return matchRegex.stream().anyMatch(m -> m.matcher(url).matches());
    }

    private List<String> values(Pattern regex, String input) {
        return regex.matcher(input).results()
              .flatMap(matcher -> IntStream.range(1, matcher.groupCount() + 1)
                    .mapToObj(matcher::group))
              .collect(Collectors.toList());
    }

    private String urlDecode(String s) {
        return URLDecoder.decode(s.replace("+", "%2B"),
              StandardCharsets.UTF_8).replace("%2B", "+");
    }

    private void validatePath(String rawPath) {
        if (rawPath.contains("/:")) {
            throw new IllegalArgumentException("Path '" + rawPath
                  + "' is invalid - params should be defined as '{param}' or <param>.");
        }
    }

    private List<PathSegment> parseSegments() {
        return Stream.of(path.split("/"))
              .filter(segment -> !segment.isEmpty())
              .map(segment -> convertSegment(segment, rawPath))
              .collect(Collectors.toList());
    }

    private List<String> extractPathParamNames() {
        return segments.stream()
              .flatMap(segment -> segment.pathParamNames().stream())
              .collect(Collectors.toList());
    }

    private void validatePathParamNames() {
        if (pathParamNames.size() != new ArrayList<>(Set.copyOf(pathParamNames)).size()) {
            throw new DuplicatedParamNamesException(rawPath);
        }
    }

    private String computeRegexSuffix() {
        if (rawPath.equals("/")) {
            return "";
        } else {
            return "/?";
        }
    }
}
