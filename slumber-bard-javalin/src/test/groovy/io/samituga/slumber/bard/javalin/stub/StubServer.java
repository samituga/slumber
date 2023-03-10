package io.samituga.slumber.bard.javalin.stub;

import static io.samituga.bard.endpoint.Verb.GET;

import io.samituga.bard.ServerStatus;
import io.samituga.bard.configuration.ServerConfig;
import io.samituga.bard.endpoint.HttpCode;
import io.samituga.bard.endpoint.Request;
import io.samituga.bard.endpoint.Response;
import io.samituga.bard.endpoint.Route;
import io.samituga.bard.endpoint.type.Path;
import io.samituga.bard.endpoint.type.PathParamName;
import io.samituga.bard.fixture.ResponseTestData;
import io.samituga.bard.fixture.RouteTestData;
import io.samituga.bard.fixture.ServerConfigTestData;
import io.samituga.slumber.bard.javalin.JavalinApplication;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StubServer {

    public static final JavalinApplication app = new JavalinApplication();

    public static final int PORT = 8080;
    public static final Path PATH_TITLE = Path.of("/title/{uuid}");

    public static final Path PATH_HELLO_WORLD = Path.of("/hello/world");
    private final Route<String> routeHelloWorld = RouteTestData.<String>aRoute()
          .path(PATH_HELLO_WORLD)
          .verb(GET)
          .handler(this::helloWorld)
          .build();
    private final Map<UUID, String> database;
    private final Route<String> routeGetTitle = RouteTestData.<String>aRoute()
          .path(PATH_TITLE)
          .verb(GET)
          .handler(this::getTitle)
          .build();

    public StubServer() {
        this.database = new HashMap<>();
    }


    public void init() {
        app.init(serverConfig());
    }

    public ServerConfig serverConfig() {
        return ServerConfigTestData.defaultServerConfig()
              .routes(routeGetTitle, routeHelloWorld)
              .port(PORT)
              .build();
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

    public Response<String> helloWorld(Request request) {

        return ResponseTestData.<String>defaultResponse()
              .statusCode(HttpCode.OK)
              .responseBody("Hello world")
              .build();
    }

    public Map<UUID, String> getDatabase() {
        return Collections.unmodifiableMap(database);
    }

    public ServerStatus serverStatus() {
        return app.serverStatus();
    }
}
