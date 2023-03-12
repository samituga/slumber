package io.samituga.slumber.bard.javalin.stub;

import static io.samituga.bard.endpoint.HttpCode.BAD_REQUEST;
import static io.samituga.bard.endpoint.HttpCode.EXPECTATION_FAILED;
import static io.samituga.bard.endpoint.HttpCode.OK;
import static io.samituga.bard.endpoint.Verb.DELETE;
import static io.samituga.bard.endpoint.Verb.GET;
import static io.samituga.bard.endpoint.Verb.POST;
import static io.samituga.bard.fixture.ServerConfigTestData.defaultServerConfig;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableMap;
import static java.util.stream.Collectors.toList;

import io.samituga.bard.ServerStatus;
import io.samituga.bard.configuration.ServerConfig;
import io.samituga.bard.endpoint.HttpCode;
import io.samituga.bard.endpoint.Request;
import io.samituga.bard.endpoint.Response;
import io.samituga.bard.endpoint.Route;
import io.samituga.bard.endpoint.type.Path;
import io.samituga.bard.endpoint.type.PathParamName;
import io.samituga.bard.endpoint.type.QueryParamName;
import io.samituga.bard.filter.Filter;
import io.samituga.bard.fixture.ResponseTestData;
import io.samituga.bard.fixture.RouteTestData;
import io.samituga.slumber.bard.javalin.JavalinApplication;
import io.samituga.slumber.ivern.http.type.Headers;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StubServer {

    private final JavalinApplication app = new JavalinApplication();

    public static final int PORT = 8080;
    public static final Path PATH_GET_TITLE = Path.of("/title/{uuid}");
    public static final Path PATH_GET_TITLE_BY_QUERY = Path.of("/title/");
    public static final Path PATH_POST_TITLE = Path.of("/title");
    public static final Path PATH_DELETE_TITLE = Path.of("/auth/title/{uuid}");
    public static final Path PATH_HELLO_WORLD = Path.of("/hello/world");
    public static final Path PATH_HEADERS = Path.of("/headers");
    private final Route<String> routeHelloWorld = RouteTestData.<String>aRoute()
          .path(PATH_HELLO_WORLD)
          .verb(GET)
          .handler(this::helloWorld)
          .build();

    private final Route<String> routeGetHeaders = RouteTestData.<String>aRoute()
          .path(PATH_HEADERS)
          .verb(GET)
          .handler(this::getHeaders)
          .build();
    private final Map<UUID, String> database;
    private final Route<String> routeGetTitle = RouteTestData.<String>aRoute()
          .path(PATH_GET_TITLE)
          .verb(GET)
          .handler(this::getTitle)
          .build();

    private final Route<String> routeGetTitleByQuery = RouteTestData.<String>aRoute()
          .path(PATH_GET_TITLE_BY_QUERY)
          .verb(GET)
          .handler(this::getTitleByQuery)
          .build();

    private final Route<String> routePostTitle = RouteTestData.<String>aRoute()
          .path(PATH_POST_TITLE)
          .verb(POST)
          .handler(this::postTitle)
          .build();

    private final Route<String> routeDeleteTitle = RouteTestData.<String>aRoute()
          .path(PATH_DELETE_TITLE)
          .verb(DELETE)
          .handler(this::deleteTitle)
          .build();

    public StubServer() {
        this.database = new HashMap<>();
    }


    public void init() {
        app.init(serverConfig(emptyList(), emptyList()));
    }

    public void init(Collection<Route<?>> extraRoutes, Collection<Filter> extraFilters) {
        app.init(serverConfig(extraRoutes, extraFilters));
    }

    public void cleanup() {
        app.shutdown();
    }

    public ServerConfig serverConfig(Collection<Route<?>> extraRoutes,
                                     Collection<Filter> extraFilters) {
        return defaultServerConfig()
              .routes(routes(extraRoutes))
              .filters(filters(extraFilters))
              .port(PORT)
              .build();
    }

    private Collection<Route<?>> routes(Collection<Route<?>> extraRoutes) {
        return Stream.concat(
                    Stream.of(routeHelloWorld,
                          routeGetHeaders,
                          routeGetTitle,
                          routeGetTitleByQuery,
                          routePostTitle,
                          routeDeleteTitle),
                    extraRoutes.stream())
              .collect(toList());
    }

    private Collection<Filter> filters(Collection<Filter> extraFilters) {
        return Stream.concat(
                    Stream.of(),
                    extraFilters.stream())
              .collect(toList());
    }


    public Response<String> getTitle(Request request) {

        var uuid = request.pathParams().get(PathParamName.of("uuid"));

        var result = database.get(UUID.fromString(uuid.value()));
        var statusCode = result == null ? HttpCode.NOT_FOUND : HttpCode.OK;

        return ResponseTestData.<String>responseBuilder()
              .statusCode(statusCode)
              .responseBody(result)
              .build();
    }

    public Response<String> getTitleByQuery(Request request) {

        var firstLetter = request.queryParams().getFirst(QueryParamName.of("firstLetter"));
        var ignoreCase = request.queryParams()
              .findFirst(QueryParamName.of("ignoreCase"))
              .map(ignoreCaseStr -> Boolean.parseBoolean(ignoreCaseStr.value()))
              .orElse(false);

        var result = database.values().stream()
              .filter(tittle -> ignoreCase
                    ? tittle.toLowerCase().startsWith(firstLetter.value().toLowerCase())
                    : tittle.startsWith(firstLetter.value()))
              .collect(Collectors.joining(","));

        var statusCode = HttpCode.OK;
        if (result.isBlank()) {
            statusCode = HttpCode.NOT_FOUND;
        }

        return ResponseTestData.<String>responseBuilder()
              .statusCode(statusCode)
              .responseBody(result)
              .build();
    }

    public Response<String> postTitle(Request request) {
        String body;
        try {
            body = request.request()
                  .getReader()
                  .lines()
                  .collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var uuid = UUID.randomUUID();

        var contains = database.containsValue(body);

        var statusCode = HttpCode.CONFLICT;
        if (!contains) {
            database.put(uuid, body);
            statusCode = HttpCode.CREATED;
        }

        return ResponseTestData.<String>responseBuilder()
              .statusCode(statusCode)
              .responseBody(uuid.toString())
              .build();
    }

    public Response<String> deleteTitle(Request request) {
        var uuid = request.pathParams().get(PathParamName.of("uuid"));

        var deleted = Optional.ofNullable(database.remove(UUID.fromString(uuid.value())))
              .isPresent();
        var statusCode = deleted ? HttpCode.NO_CONTENT : HttpCode.NOT_FOUND;

        return ResponseTestData.<String>responseBuilder()
              .statusCode(statusCode)
              .build();
    }

    public Response<String> helloWorld(Request request) {

        return ResponseTestData.<String>responseBuilder()
              .statusCode(HttpCode.OK)
              .responseBody("Hello world")
              .build();
    }

    public Response<String> getHeaders(Request request) {

        var statusCode = Optional.ofNullable(request.request()
                    .getHeader("req-header-name"))
              .map(headerValue -> headerValue.equals("req-header-value") ? OK : EXPECTATION_FAILED)
              .orElse(BAD_REQUEST);

        return ResponseTestData.<String>responseBuilder()
              .statusCode(statusCode)
              .responseBody("Hello world")
              .headers(Headers.of("resp-header-name", "resp-header-value"))
              .build();
    }

    public Map<UUID, String> getDatabase() {
        return unmodifiableMap(database);
    }

    public ServerStatus serverStatus() {
        return app.serverStatus();
    }
}
