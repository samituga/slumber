package io.samituga.slumber.bard.javalin.stub;

import static io.samituga.bard.endpoint.response.HttpCode.BAD_REQUEST;
import static io.samituga.bard.endpoint.response.HttpCode.EXPECTATION_FAILED;
import static io.samituga.bard.endpoint.response.HttpCode.NOT_FOUND;
import static io.samituga.bard.endpoint.response.HttpCode.NO_CONTENT;
import static io.samituga.bard.endpoint.response.HttpCode.OK;
import static io.samituga.bard.endpoint.route.RouteBuilder.routeBuilder;
import static io.samituga.bard.endpoint.route.Verb.DELETE;
import static io.samituga.bard.endpoint.route.Verb.GET;
import static io.samituga.bard.endpoint.route.Verb.POST;
import static io.samituga.bard.fixture.ServerConfigTestData.aServerConfig;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableMap;
import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.databind.Module;
import io.samituga.bard.application.ServerStatus;
import io.samituga.bard.configuration.ServerConfig;
import io.samituga.bard.endpoint.context.HttpContext;
import io.samituga.bard.endpoint.response.HttpCode;
import io.samituga.bard.endpoint.response.type.ByteResponseBody;
import io.samituga.bard.endpoint.response.type.TypeResponseBody;
import io.samituga.bard.endpoint.route.Route;
import io.samituga.bard.filter.Filter;
import io.samituga.bard.handler.ExceptionHandler;
import io.samituga.bard.type.Path;
import io.samituga.bard.type.Port;
import io.samituga.slumber.bard.javalin.JavalinApplication;
import io.samituga.slumber.ivern.http.type.Headers;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StubServer {

    public static final Port PORT = Port.of(8080);
    public static final Path PATH_GET_TITLE = Path.of("/title/{uuid}");
    public static final Path PATH_GET_TITLE_BY_QUERY = Path.of("/title/");
    public static final Path PATH_POST_TITLE = Path.of("/title");
    public static final Path PATH_POST_BODY = Path.of("/titleBody");
    public static final Path PATH_DELETE_TITLE = Path.of("/auth/title/{uuid}");
    public static final Path PATH_HELLO_WORLD = Path.of("/hello/world");
    public static final Path PATH_HEADERS = Path.of("/headers");
    public static final Path PATH_THROWS_EXCEPTION = Path.of("/exception");
    public static final Path PATH_JSON_MODULE = Path.of("/json");

    private final JavalinApplication app = new JavalinApplication();
    private final Route routeHelloWorld = routeBuilder()
          .path(PATH_HELLO_WORLD)
          .verb(GET)
          .handler(this::helloWorld)
          .build();

    private final Route routeGetHeaders = routeBuilder()
          .path(PATH_HEADERS)
          .verb(GET)
          .handler(this::getHeaders)
          .build();
    private final Map<UUID, String> database;
    private final Route routeGetTitle = routeBuilder()
          .path(PATH_GET_TITLE)
          .verb(GET)
          .handler(this::getTitle)
          .build();

    private final Route routeGetTitleByQuery = routeBuilder()
          .path(PATH_GET_TITLE_BY_QUERY)
          .verb(GET)
          .handler(this::getTitleByQuery)
          .build();

    private final Route routePostTitle = routeBuilder()
          .path(PATH_POST_TITLE)
          .verb(POST)
          .handler(this::postTitle)
          .build();

    private final Route routePostBodyTitle = routeBuilder()
          .path(PATH_POST_BODY)
          .verb(POST)
          .handler(this::postTitle)
          .build();

    private final Route routeDeleteTitle = routeBuilder()
          .path(PATH_DELETE_TITLE)
          .verb(DELETE)
          .handler(this::deleteTitle)
          .build();

    private final Route routeThrowsException = routeBuilder()
          .path(PATH_THROWS_EXCEPTION)
          .verb(GET)
          .handler(this::throwsRuntimeException)
          .build();

    private final Route routeJsonModule = routeBuilder()
          .path(PATH_JSON_MODULE)
          .verb(POST)
          .handler(this::jsonModule)
          .build();

    public StubServer() {
        this.database = new HashMap<>();
    }


    public void init() {
        app.init(serverConfig(emptyList(), emptyList(), emptyList(), emptyList()));
    }

    // TODO: 2023-03-31 Clean this
    public void init(Collection<Route> extraRoutes,
                     Collection<Filter> extraFilters,
                     Collection<ExceptionHandler<? extends Exception>> exceptionHandlers,
                     Collection<Module> jsonModules) {
        app.init(serverConfig(extraRoutes, extraFilters, exceptionHandlers, jsonModules));
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
                                      Collection<Filter> extraFilters,
                                      Collection<ExceptionHandler<? extends Exception>> extraExceptionHandlers,
                                      Collection<Module> jsonModules) {
        return aServerConfig()
              .routes(routes(extraRoutes))
              .filters(filters(extraFilters))
              .exceptionHandlers(exceptionHandlers(extraExceptionHandlers))
              .jacksonModules(jsonModules)
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
                          routeDeleteTitle,
                          routeThrowsException,
                          routeJsonModule),
                    extraRoutes.stream())
              .collect(toList());
    }

    private Collection<Filter> filters(Collection<Filter> extraFilters) {
        return Stream.concat(
                    Stream.of(),
                    extraFilters.stream())
              .collect(toList());
    }

    private Collection<ExceptionHandler<? extends Exception>> exceptionHandlers(
          Collection<ExceptionHandler<? extends Exception>> exceptionHandlers) {
        return Stream.concat(
                    Stream.of(),
                    exceptionHandlers.stream())
              .collect(toList());
    }


    private void getTitle(HttpContext ctx) {

        var uuid = ctx.request().pathParams().get("uuid");

        Optional.ofNullable(database.get(UUID.fromString(uuid)))
              .ifPresentOrElse(title -> ctx.response()
                          .statusCode(OK)
                          .responseBody(ByteResponseBody.of(title)),
                    () -> ctx.response().statusCode(NOT_FOUND));
    }

    private void getTitleByQuery(HttpContext ctx) {

        var firstLetter = ctx.request().queryParams().getFirst("firstLetter");
        var ignoreCase = ctx.request().queryParams()
              .findFirst("ignoreCase")
              .map(Boolean::parseBoolean)
              .orElse(false);

        var result = database.values().stream()
              .filter(tittle -> ignoreCase
                    ? tittle.toLowerCase().startsWith(firstLetter.toLowerCase())
                    : tittle.startsWith(firstLetter))
              .collect(Collectors.joining(","));

        var statusCode = OK;
        if (result.isBlank()) {
            statusCode = NOT_FOUND;
        }

        ctx.response()
              .statusCode(statusCode)
              .responseBody(ByteResponseBody.of(result));
    }

    private void postTitle(HttpContext ctx) {
        var requestBody = ctx.request().requestBody();
        if (requestBody.isEmpty()) {
            ctx.response().statusCode(BAD_REQUEST);
            return;
        }
        String body = new String(requestBody.get().value(), StandardCharsets.UTF_8);

        var uuid = UUID.randomUUID();

        var contains = database.containsValue(body);

        var statusCode = HttpCode.CONFLICT;
        if (!contains) {
            database.put(uuid, body);
            statusCode = HttpCode.CREATED;
        }

        ctx.response()
              .statusCode(statusCode)
              .responseBody(ByteResponseBody.of(uuid.toString()));
    }

    private void deleteTitle(HttpContext ctx) {
        var uuid = ctx.request().pathParams().get("uuid");

        var deleted = Optional.ofNullable(database.remove(UUID.fromString(uuid)))
              .isPresent();
        var statusCode = deleted ? NO_CONTENT : NOT_FOUND;

        ctx.response().statusCode(statusCode);
    }

    private void helloWorld(HttpContext ctx) {
        ctx.response()
              .statusCode(OK)
              .responseBody(ByteResponseBody.of("Hello world"));
    }

    private void getHeaders(HttpContext ctx) {

        var statusCode = Optional.ofNullable(ctx.request().headers().value().get("req-header-name"))
              .map(headerValue -> headerValue.equals("req-header-value") ? OK : EXPECTATION_FAILED)
              .orElse(BAD_REQUEST);

        ctx.response()
              .statusCode(statusCode)
              .responseBody(ByteResponseBody.of("Hello world"))
              .headers(Headers.of("resp-header-name", "resp-header-value"));
    }

    private void jsonModule(HttpContext ctx) {
        var reqBody = ctx.request().requestBodyAsType(StubPerson.class);

        if (reqBody.isEmpty()) {
            ctx.response().statusCode(BAD_REQUEST);
            return;
        }
        var body = reqBody.get().value();
        var statusCode = body.getP1().equals("John Doe") && body.getP2() == 25 ? OK : BAD_REQUEST;
        ctx.response()
              .statusCode(statusCode)
              .responseBody(TypeResponseBody.of(body));
    }

    private void throwsRuntimeException(HttpContext ctx) {
        throw new RuntimeException("Test message");
    }
}
