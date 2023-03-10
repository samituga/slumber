package io.samituga.slumber.bard.javalin.stub;

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
import io.samituga.bard.filter.Filter;
import io.samituga.bard.fixture.ResponseTestData;
import io.samituga.bard.fixture.RouteTestData;
import io.samituga.slumber.bard.javalin.JavalinApplication;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StubServer {

    private final JavalinApplication app = new JavalinApplication();

    public static final int PORT = 8080;
    public static final Path PATH_GET_TITLE = Path.of("/title/{uuid}");
    public static final Path PATH_POST_TITLE = Path.of("/title");

    public static final Path PATH_HELLO_WORLD = Path.of("/hello/world");
    private final Route<String> routeHelloWorld = RouteTestData.<String>aRoute()
          .path(PATH_HELLO_WORLD)
          .verb(GET)
          .handler(this::helloWorld)
          .build();
    private final Map<UUID, String> database;
    private final Route<String> routeGetTitle = RouteTestData.<String>aRoute()
          .path(PATH_GET_TITLE)
          .verb(GET)
          .handler(this::getTitle)
          .build();

    private final Route<String> routePostTitle = RouteTestData.<String>aRoute()
          .path(PATH_POST_TITLE)
          .verb(POST)
          .handler(this::postTitle)
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
              .filters(extraFilters)
              .port(PORT)
              .build();
    }

    private Collection<Route<?>> routes(Collection<Route<?>> extraRoutes) {
        return Stream.concat(
                    Stream.of(routeHelloWorld, routeGetTitle, routePostTitle),
                    extraRoutes.stream())
              .collect(toList());
    }


    public Response<String> getTitle(Request request) {

        var uuid = request.pathParams().get(PathParamName.of("uuid"));

        var result = database.get(UUID.fromString(uuid.value()));
        var statusCode = result == null ? HttpCode.NOT_FOUND : HttpCode.OK;

        return ResponseTestData.<String>defaultResponse()
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

        return ResponseTestData.<String>defaultResponse()
              .statusCode(statusCode)
              .responseBody(uuid.toString())
              .build();
    }

    public Response<String> helloWorld(Request request) {

        return ResponseTestData.<String>defaultResponse()
              .statusCode(HttpCode.OK)
              .responseBody("Hello world")
              .build();
    }

    public Map<UUID, String> getDatabase() {
        return unmodifiableMap(database);
    }

    public ServerStatus serverStatus() {
        return app.serverStatus();
    }
}
