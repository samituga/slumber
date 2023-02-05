package io.samituga.slumber.bard.javalin

import io.samituga.bard.ServerStatus
import io.samituga.bard.endpoint.HttpCode
import io.samituga.bard.endpoint.Response
import io.samituga.bard.endpoint.type.Path
import io.samituga.bard.fixture.ResponseTestData
import io.samituga.bard.fixture.RouteTestData
import io.samituga.bard.fixture.ServerConfigTestData
import io.samituga.slumber.rakan.request.HttpRequestBuilder
import io.samituga.slumber.rakan.request.HttpRequestBuilderImpl
import jakarta.servlet.http.HttpServletRequest
import spock.lang.Specification

import java.net.http.HttpResponse
import java.time.Duration
import java.util.function.Function
import java.util.function.Supplier

import static io.samituga.bard.endpoint.Verb.GET

class JavalinApplicationSpec extends Specification {

    private JavalinApplication application

    def path = Path.of("/test")
    def responseBody = "Hello World"

    private Response<String> response = ResponseTestData.<String> defaultResponse()
      .statusCode(HttpCode.OK)
      .responseBody(responseBody)
      .build()

    private Function<HttpServletRequest, Response<String>> handler = { HttpServletRequest request -> response }

    def route = RouteTestData.defaultRoute()
      .path(path)
      .verb(GET)
      .handler(handler)
      .build()

    def port = 8080

    def serverConfig = ServerConfigTestData.defaultServerConfig()
      .routes(route)
      .port(port)
      .build()

    def setup() {
        application = new JavalinApplication()
    }


    def 'test'() {
        given:
        def uri = URI.create("http://localhost:" + port + path.value())
        application.init(serverConfig)

        and:
        waitFor { application.serverStatus() == ServerStatus.STARTED }

        when:
        def result = HttpRequestBuilder.request(String.class)
          .client(HttpRequestBuilderImpl.DEFAULT_HTTP_CLIENT)
          .uri(uri)
          .responseBodyHandler(HttpResponse.BodyHandlers.ofString())
          .httpGet()
          .execute()

        then:
        result.statusCode() == HttpCode.OK.code()
        result.body() == responseBody
    }

    private void waitFor(Supplier<Boolean> probe) { // TODO common place for this
        for (i in 0..<Duration.ofSeconds(5).seconds * 10) {
            if (probe.get()) {
                break
            }
            Thread.sleep(100)
        }
    }
}
