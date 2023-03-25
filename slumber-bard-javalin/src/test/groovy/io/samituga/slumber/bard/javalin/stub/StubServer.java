package io.samituga.slumber.bard.javalin.stub;

import static io.samituga.bard.endpoint.HttpCode.BAD_REQUEST;
import static io.samituga.bard.endpoint.HttpCode.EXPECTATION_FAILED;
import static io.samituga.bard.endpoint.HttpCode.OK;
import static io.samituga.bard.endpoint.Verb.DELETE;
import static io.samituga.bard.endpoint.Verb.GET;
import static io.samituga.bard.endpoint.Verb.POST;
import static io.samituga.bard.endpoint.response.HttpResponseBuilder.httpResponseBuilder;
import static io.samituga.bard.fixture.RouteTestData.aRoute;
import static io.samituga.bard.fixture.ServerConfigTestData.aServerConfig;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableMap;
import static java.util.stream.Collectors.toList;

import io.samituga.bard.ServerStatus;
import io.samituga.bard.configuration.ServerConfig;
import io.samituga.bard.endpoint.HttpCode;
import io.samituga.bard.endpoint.Route;
import io.samituga.bard.endpoint.request.HttpRequest;
import io.samituga.bard.endpoint.response.HttpResponse;
import io.samituga.bard.endpoint.response.type.ByteResponseBody;
import io.samituga.bard.endpoint.type.Path;
import io.samituga.bard.endpoint.type.PathParamName;
import io.samituga.bard.endpoint.type.QueryParamName;
import io.samituga.bard.filter.Filter;
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

    public static final int PORT = 8080;
    public static final Path PATH_GET_TITLE = Path.of("/title/{uuid}");
    public static final Path PATH_GET_TITLE_BY_QUERY = Path.of("/title/");
    public static final Path PATH_POST_TITLE = Path.of("/title");
    public static final Path PATH_DELETE_TITLE = Path.of("/auth/title/{uuid}");
    public static final Path PATH_HELLO_WORLD = Path.of("/hello/world");
    public static final Path PATH_HEADERS = Path.of("/headers");
    private final JavalinApplication app = new JavalinApplication();
    private final Route routeHelloWorld = aRoute()
          .path(PATH_HELLO_WORLD)
          .verb(GET)
          .handler(this::helloWorld)
          .build();

    private final Route routeGetHeaders = aRoute()
          .path(PATH_HEADERS)
          .verb(GET)
          .handler(this::getHeaders)
          .build();
    private final Map<UUID, String> database;
    private final Route routeGetTitle = aRoute()
          .path(PATH_GET_TITLE)
          .verb(GET)
          .handler(this::getTitle)
          .build();

    private final Route routeGetTitleByQuery = aRoute()
          .path(PATH_GET_TITLE_BY_QUERY)
          .verb(GET)
          .handler(this::getTitleByQuery)
          .build();

    private final Route routePostTitle = aRoute()
          .path(PATH_POST_TITLE)
          .verb(POST)
          .handler(this::postTitle)
          .build();

    private final Route routeDeleteTitle = aRoute()
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

    public void init(Collection<Route> extraRoutes, Collection<Filter> extraFilters) {
        app.init(serverConfig(extraRoutes, extraFilters));
    }

    public void cleanup() {
        app.shutdown();
        database.clear();
    }

    public Optional<String> addToDatabase(UUID uuid, String title) {
        return Optional.ofNullable(database.put(uuid, title));
    }

    public Optional<String> findInDatabase(UUID uuid) {
        return Optional.ofNullable(database.get(uuid));
    }

    public String getFromDatabase(UUID uuid) {
        return Optional.ofNullable(database.get(uuid)).orElseThrow();
    }

    public Map<UUID, String> getDatabase() {
        return unmodifiableMap(database);
    }

    public ServerStatus serverStatus() {
        return app.serverStatus();
    }

    private ServerConfig serverConfig(Collection<Route> extraRoutes,
                                      Collection<Filter> extraFilters) {
        return aServerConfig()
              .routes(routes(extraRoutes))
              .filters(filters(extraFilters))
              .port(PORT)
              .build();
    }

    private Collection<Route> routes(Collection<Route> extraRoutes) {
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


    private HttpResponse getTitle(HttpRequest httpRequest) {

        var uuid = httpRequest.pathParams().get(PathParamName.of("uuid"));

        return Optional.ofNullable(database.get(UUID.fromString(uuid.value())))
              .map(title -> httpResponseBuilder()
                    .statusCode(HttpCode.OK)
                    .responseBody(ByteResponseBody.of(title))
                    .build())
              .orElse(httpResponseBuilder()
                    .statusCode(HttpCode.NOT_FOUND)
                    .build());
    }

    private HttpResponse getTitleByQuery(HttpRequest httpRequest) {

        var firstLetter = httpRequest.queryParams().getFirst(QueryParamName.of("firstLetter"));
        var ignoreCase = httpRequest.queryParams()
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

        return httpResponseBuilder()
              .statusCode(statusCode)
              .responseBody(ByteResponseBody.of(result))
              .build();
    }

    private HttpResponse postTitle(HttpRequest httpRequest) {
        String body;
        try {
            body = httpRequest.request()
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

        return httpResponseBuilder()
              .statusCode(statusCode)
              .responseBody(ByteResponseBody.of(uuid.toString()))
              .build();
    }

    private HttpResponse deleteTitle(HttpRequest httpRequest) {
        var uuid = httpRequest.pathParams().get(PathParamName.of("uuid"));

        var deleted = Optional.ofNullable(database.remove(UUID.fromString(uuid.value())))
              .isPresent();
        var statusCode = deleted ? HttpCode.NO_CONTENT : HttpCode.NOT_FOUND;

        return httpResponseBuilder()
              .statusCode(statusCode)
              .build();
    }

    private HttpResponse helloWorld(HttpRequest httpRequest) {

        return httpResponseBuilder()
              .statusCode(HttpCode.OK)
              .responseBody(ByteResponseBody.of("Hello world"))
              .build();
    }

    private HttpResponse getHeaders(HttpRequest httpRequest) {

        var statusCode = Optional.ofNullable(httpRequest.request()
                    .getHeader("req-header-name"))
              .map(headerValue -> headerValue.equals("req-header-value") ? OK : EXPECTATION_FAILED)
              .orElse(BAD_REQUEST);

        return httpResponseBuilder()
              .statusCode(statusCode)
              .responseBody(ByteResponseBody.of("Hello world"))
              .headers(Headers.of("resp-header-name", "resp-header-value"))
              .build();
    }
}
