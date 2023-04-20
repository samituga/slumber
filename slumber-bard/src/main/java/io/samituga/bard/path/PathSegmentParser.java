package io.samituga.bard.path;

import static io.samituga.bard.path.ParserState.NORMAL;
import static io.samituga.bard.path.PathSegment.createNormal;
import static io.samituga.bard.path.PathSegment.createSlashAcceptingParam;
import static io.samituga.bard.path.PathSegment.createSlashIgnoringParam;
import static io.samituga.slumber.heimer.util.StringUtils.isEnclosedBy;
import static io.samituga.slumber.heimer.util.StringUtils.stripEnclosing;

import io.samituga.bard.path.PathSegment.MultipleSegments;
import io.samituga.bard.path.exception.MissingBracketsException;
import io.samituga.bard.path.exception.WildcardBracketAdjacentException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PathSegmentParser {

    private static final Set<Character> ALL_DELIMITERS = Set.of('{', '}', '<', '>');
    private static final List<String> ADJACENT_VIOLATIONS = List.of("*{", "*<", "}*", ">*");

    public static PathSegment convertSegment(String segment, String rawPath) {
        long bracketsCount = segment.chars().filter(c -> ALL_DELIMITERS.contains((char) c)).count();
        long wildcardCount = segment.chars().filter(c -> c == '*').count();

        if (bracketsCount % 2 != 0) {
            throw new MissingBracketsException(segment, rawPath);
        }
        if (ADJACENT_VIOLATIONS.stream().anyMatch(segment::contains)) {
            throw new WildcardBracketAdjacentException(segment, rawPath);
        }

        if (segment.equals("*")) {
            return new PathSegment.Wildcard();
        }
        if (bracketsCount == 0 && wildcardCount == 0) {
            return createNormal(segment);
        }
        if (isEnclosedBy(segment, "{", "}")) {
            return createSlashIgnoringParam(stripEnclosing(segment, "{", "}"));
        }
        if (isEnclosedBy(segment, "<", ">")) {
            return createSlashAcceptingParam(stripEnclosing(segment, "<", ">"));
        }
        return createPathSegment(segment, rawPath);
    }

    private static PathSegment createPathSegment(String segment, String rawPath) {
        ParserState state = NORMAL;
        StringBuilder pathNameAccumulator = new StringBuilder();
        List<PathSegment> innerSegments = new ArrayList<>();

        for (char c : segment.toCharArray()) {
            PathSegmentAndState mappedSegment = mapSingleChar(
                  c,
                  state,
                  pathNameAccumulator,
                  rawPath);
            mappedSegment.pathSegment().ifPresent(innerSegments::add);
            state = mappedSegment.state;
        }
        return mergeInnerSegments(innerSegments);
    }

    private static PathSegmentAndState mapSingleChar(char c,
                                                     ParserState state,
                                                     StringBuilder pathNameAccumulator,
                                                     String rawPath) {
        return switch (state) {
            case NORMAL -> processNormalState(c, state, rawPath);
            case INSIDE_SLASH_IGNORING_BRACKETS -> processInsideSlashIgnoringBracketsState(
                  c,
                  state,
                  pathNameAccumulator,
                  rawPath);
            case INSIDE_SLASH_ACCEPTING_BRACKETS -> processInsideSlashAcceptingBracketsState(
                  c,
                  state,
                  pathNameAccumulator,
                  rawPath);
        };
    }

    private static PathSegmentAndState processNormalState(char c,
                                                          ParserState state,
                                                          String rawPath) {
        switch (c) {
            case '*' -> {
                return new PathSegmentAndState(Optional.of(new PathSegment.Wildcard()), state);
            }
            case '{' -> {
                state = ParserState.INSIDE_SLASH_IGNORING_BRACKETS;
                return new PathSegmentAndState(Optional.empty(), state);
            }
            case '<' -> {
                state = ParserState.INSIDE_SLASH_ACCEPTING_BRACKETS;
                return new PathSegmentAndState(Optional.empty(), state);
            }
            case '}', '>' -> throw new MissingBracketsException(String.valueOf(c), rawPath);
            default -> {
                var pathSegment = createNormal(String.valueOf(c));
                return new PathSegmentAndState(Optional.of(pathSegment), state);
            }
        }
    }

    private static PathSegmentAndState processInsideSlashIgnoringBracketsState(char c,
                                                                               ParserState state,
                                                                               StringBuilder pathNameAccumulator,
                                                                               String rawPath) {
        switch (c) {
            case '}' -> {
                String name = pathNameAccumulator.toString();
                pathNameAccumulator.setLength(0);
                var pathSegment = createSlashIgnoringParam(name);
                return new PathSegmentAndState(Optional.of(pathSegment), NORMAL);
            }
            case '{', '<', '>' -> throw new MissingBracketsException(String.valueOf(c), rawPath);
            default -> {
                pathNameAccumulator.append(c);
                return new PathSegmentAndState(Optional.empty(), state);
            }
        }
    }

    private static PathSegmentAndState processInsideSlashAcceptingBracketsState(char c,
                                                                                ParserState state,
                                                                                StringBuilder pathNameAccumulator,
                                                                                String rawPath) {
        switch (c) {
            case '>' -> {
                state = NORMAL;
                String name = pathNameAccumulator.toString();
                pathNameAccumulator.setLength(0);
                var pathSegment = createSlashAcceptingParam(name);
                return new PathSegmentAndState(Optional.of(pathSegment), state);
            }
            case '{', '}', '<' -> throw new MissingBracketsException(String.valueOf(c), rawPath);
            default -> {
                pathNameAccumulator.append(c);
                return new PathSegmentAndState(Optional.empty(), state);
            }
        }
    }

    private static PathSegment mergeInnerSegments(List<PathSegment> innerSegments) {
        List<PathSegment> mergedSegments = new ArrayList<>();
        for (PathSegment segment : innerSegments) {
            mergeSegments(mergedSegments, segment);
        }
        return mergedSegments.size() == 1
              ? mergedSegments.get(0)
              : new MultipleSegments(mergedSegments);
    }

    private static void mergeSegments(List<PathSegment> mergedSegments, PathSegment segment) {
        Optional<PathSegment> lastAdditionOptional = mergedSegments.isEmpty()
              ? Optional.empty()
              : Optional.of(mergedSegments.get(mergedSegments.size() - 1));
        if (lastAdditionOptional.isEmpty()
              || bothSegmentsAreWildcards(lastAdditionOptional.get(), segment)) {
            return;
        }
        if (lastAdditionOptional.get() instanceof PathSegment.Normal normalLastAddition
              && segment instanceof PathSegment.Normal normalSegment) {
            PathSegment mergedSegment = createNormal(
                  normalLastAddition.content + normalSegment.content);
            mergedSegments.set(mergedSegments.size() - 1, mergedSegment);
        } else {
            mergedSegments.add(segment);
        }
    }

    private static boolean bothSegmentsAreWildcards(PathSegment lastAddition, PathSegment segment) {
        return lastAddition instanceof PathSegment.Wildcard
              && segment instanceof PathSegment.Wildcard;
    }

    private record PathSegmentAndState(Optional<PathSegment> pathSegment, ParserState state) {}
}
