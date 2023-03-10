package io.samituga.slumber.bard.javalin;

import static io.samituga.bard.endpoint.Verb.GET;

import io.samituga.bard.configuration.ServerConfig;
import io.samituga.bard.endpoint.HttpCode;
import io.samituga.bard.endpoint.Request;
import io.samituga.bard.endpoint.Response;
import io.samituga.bard.endpoint.Route;
import io.samituga.bard.endpoint.type.Path;
import io.samituga.bard.fixture.ResponseTestData;
import io.samituga.bard.fixture.RouteTestData;
import io.samituga.bard.fixture.ServerConfigTestData;
import java.util.function.Function;

public class StubServer {

    public static final Path PATH = Path.of("/test");
    public static final String RESPONSE_BODY = "Hello World";

    public static final Response<String> RESPONSE = ResponseTestData.<String>defaultResponse()
          .statusCode(HttpCode.OK)
          .responseBody(RESPONSE_BODY)
          .build();

    public static final Function<Request, Response<String>> HANDLER = (Request request) -> RESPONSE;

    public static final Route<String> ROUTE = RouteTestData.<String>aRoute()
          .path(PATH)
          .verb(GET)
          .handler(HANDLER)
          .build();
    public static final int PORT = 8080;

    public static final ServerConfig SERVER_CONFIG = ServerConfigTestData.defaultServerConfig()
          .routes(ROUTE)
          .port(PORT)
          .build();
}
