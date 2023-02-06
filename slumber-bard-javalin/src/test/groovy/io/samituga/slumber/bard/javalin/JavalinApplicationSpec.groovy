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
import io.samituga.slumber.ziggs.WaitFor
import jakarta.servlet.http.HttpServletRequest
import spock.lang.Specification

import java.net.http.HttpResponse
import java.time.Duration
import java.util.function.Function

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


    def 'should start server and create endpoint'() {
        given: 'server initialization'
        def uri = URI.create("http://localhost:" + port + path.value())
        application.init(serverConfig)

        and: 'wait for the server to be initialized'
        WaitFor.waitFor({ application.serverStatus() == ServerStatus.STARTED }, Duration.ofSeconds(1))

        when: 'makes request'
        def result = HttpRequestBuilder.request(String.class)
          .client(HttpRequestBuilderImpl.DEFAULT_HTTP_CLIENT)
          .uri(uri)
          .responseBodyHandler(HttpResponse.BodyHandlers.ofString())
          .httpGet()
          .execute()

        then: 'result should have correct values'
        result.statusCode() == HttpCode.OK.code()
        result.body() == responseBody
    }
}
