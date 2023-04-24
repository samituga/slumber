package io.samituga.slumber.bard.javalin

import static io.samituga.bard.filter.FilterBuilder.filterBuilder
import static io.samituga.slumber.bard.javalin.stub.StubServer.PATH_HELLO_WORLD
import static java.util.Collections.emptyList
import static java.util.UUID.randomUUID

import com.fasterxml.jackson.databind.module.SimpleModule
import io.samituga.bard.application.ServerStatus
import io.samituga.bard.endpoint.ContentType
import io.samituga.bard.endpoint.context.HttpContext
import io.samituga.bard.endpoint.response.HttpCode
import io.samituga.bard.endpoint.response.type.ByteResponseBody
import io.samituga.bard.filter.Precedence
import io.samituga.bard.filter.type.Order
import io.samituga.bard.handler.ExceptionHandler
import io.samituga.jayce.Json
import io.samituga.slumber.bard.javalin.stub.StubClient
import io.samituga.slumber.bard.javalin.stub.StubPerson
import io.samituga.slumber.bard.javalin.stub.StubPersonDeserializer
import io.samituga.slumber.bard.javalin.stub.StubPersonSerializer
import io.samituga.slumber.bard.javalin.stub.StubServer
import io.samituga.slumber.ivern.http.type.Headers
import io.samituga.slumber.ziggs.WaitFor
import spock.lang.Specification

import java.time.Duration
import java.time.Instant
import java.util.function.Consumer

class JavalinApplicationSpec extends Specification {

    private StubServer application
    private StubClient client

    def setup() {
        application = new StubServer()
        client = new StubClient()
        Json.initialized = false // TODO static variable doesn't reset from test to test
    }

    def cleanup() {
        application.cleanup()
    }

    def 'should start server and create endpoint'() {
        given: 'server initialization'
        application.init()

        and: 'wait for the server to be initialized'
        waitForServerInit()

        when: 'makes request'
        def result = client.getHelloWorld()

        then: 'result should have correct values'
        result.statusCode() == HttpCode.OK.code()
        result.body() == "Hello world"
    }

    def "should make get request and get title"() {
        given: 'server initialization'
        application.init()
        def title = "The Big And The Small"
        def titleUuid = randomUUID()
        application.addToDatabase(titleUuid, title)

        and: 'wait for the server to be initialized'
        waitForServerInit()

        when: 'make get request'
        def resultTitle = client.getTitle(titleUuid)

        then: 'should be correct title'
        resultTitle.statusCode() == HttpCode.OK.code()
        resultTitle.body() == title
    }

    def 'should make post request and create title'() {
        given: 'server initialization'
        application.init()
        def title = "The Big And The Small"

        and: 'wait for the server to be initialized'
        waitForServerInit()

        when: 'makes post request'
        def postTitleResponse = client.postTitle(title)

        then: 'result should have correct values'
        postTitleResponse.statusCode() == HttpCode.CREATED.code()
        !postTitleResponse.body().isBlank()
        def resultBody = postTitleResponse.body()
        def uuid = UUID.fromString(resultBody)
        uuid != null

        and: 'title should be available in the database'
        def resultTitle = application.getFromDatabase(uuid)
        resultTitle == title
    }

    def 'should execute filter doBefore before doAfter'() {
        given: 'server initialization'
        Instant doBeforeTimestamp = null
        Instant doAfterTimestamp = null

        Consumer<HttpContext> doBefore =
              (ctx) -> { doBeforeTimestamp = Instant.now(); Thread.sleep(2) }
        Consumer<HttpContext> doAfter =
              (ctx) -> { doAfterTimestamp = Instant.now(); Thread.sleep(2) }

        def filter = filterBuilder()
              .doBefore(doBefore)
              .doAfter(doAfter)
              .order(Order.of(0))
              .path(PATH_HELLO_WORLD)
              .build()

        application.init(emptyList(), List.of(filter), emptyList(), emptyList())


        and: 'wait for the server to be initialized'
        waitForServerInit()

        when: 'makes request'
        def result = client.getHelloWorld()

        then: 'result should have correct values'
        result.statusCode() == HttpCode.OK.code()
        result.body() == "Hello world"
        doBeforeTimestamp.isBefore(doAfterTimestamp)
    }

    def "should execute filters in order"() {
        given: 'server initialization'
        Instant firstDoBeforeTimestamp = null
        Instant firstDoAfterTimestamp = null

        Consumer<HttpContext> firstDoBefore =
              (ctx) -> { firstDoBeforeTimestamp = Instant.now(); Thread.sleep(2) }
        Consumer<HttpContext> firstDoAfter =
              (ctx) -> { firstDoAfterTimestamp = Instant.now(); Thread.sleep(2) }

        def firstFilter = filterBuilder()
              .doBefore(firstDoBefore)
              .doAfter(firstDoAfter)
              .order(Order.of(Precedence.FIRST))
              .path(PATH_HELLO_WORLD)
              .build()

        Instant middleDoBeforeTimestamp = null
        Instant middleDoAfterTimestamp = null

        Consumer<HttpContext> middleDoBefore =
              (ctx) -> { middleDoBeforeTimestamp = Instant.now(); Thread.sleep(2) }
        Consumer<HttpContext> middleDoAfter =
              (ctx) -> { middleDoAfterTimestamp = Instant.now(); Thread.sleep(2) }

        def middleFilter = filterBuilder()
              .doBefore(middleDoBefore)
              .doAfter(middleDoAfter)
              .order(Order.of(0))
              .path(PATH_HELLO_WORLD)
              .build()

        Instant lastDoBeforeTimestamp = null
        Instant lastDoAfterTimestamp = null

        Consumer<HttpContext> lastDoBefore =
              (ctx) -> { lastDoBeforeTimestamp = Instant.now(); Thread.sleep(2) }
        Consumer<HttpContext> lastDoAfter =
              (ctx) -> { lastDoAfterTimestamp = Instant.now(); Thread.sleep(2) }

        def lastFilter = filterBuilder()
              .doBefore(lastDoBefore)
              .doAfter(lastDoAfter)
              .order(Order.of(Precedence.LAST))
              .path(PATH_HELLO_WORLD)
              .build()

        application.init(emptyList(), List.of(middleFilter, lastFilter, firstFilter), emptyList(), emptyList())

        and: 'wait for the server to be initialized'
        waitForServerInit()

        when: 'makes request'
        def result = client.getHelloWorld()

        then: 'filters should be executed in the correct order'
        result.statusCode() == HttpCode.OK.code()
        firstDoBeforeTimestamp.isBefore(middleDoBeforeTimestamp)
        middleDoBeforeTimestamp.isBefore(lastDoBeforeTimestamp)
        lastDoBeforeTimestamp.isBefore(firstDoAfterTimestamp)
        firstDoAfterTimestamp.isBefore(middleDoAfterTimestamp)
        middleDoAfterTimestamp.isBefore(lastDoAfterTimestamp)
    }

    def 'should get title by query'() {
        given: 'server initialization'
        application.init()
        def titleStartsWithT1 = "The Big And The Small"
        def titleStartsWithT2 = "The Amazing"
        def title3 = "All Or Nothing"
        def title4 = "Cold"

        and: 'wait for the server to be initialized'
        waitForServerInit()

        and: 'populates db with titles'
        application.addToDatabase(randomUUID(), titleStartsWithT1)
        application.addToDatabase(randomUUID(), titleStartsWithT2)
        application.addToDatabase(randomUUID(), title3)
        application.addToDatabase(randomUUID(), title4)

        when: 'should get the correct titles by the query'
        def getTitleResponse = client.getTitlesByQuery("t", true)

        then:
        getTitleResponse.statusCode() == HttpCode.OK.code()
        !getTitleResponse.body().isBlank()
        with(getTitleResponse.body().split(",")) {
            it.size() == 2
            it.contains(titleStartsWithT1)
            it.contains(titleStartsWithT2)
        }
    }


    def 'should delete title'() {
        given: 'server initialization'
        application.init()

        and: 'wait for the server to be initialized'
        waitForServerInit()

        and: 'populates db with title'
        def title = "The Big And The Small"
        var uuid = randomUUID()
        application.addToDatabase(uuid, title)

        when: 'should delete the newly crated title'
        def getTitleResponse = client.deleteTitle(uuid)

        then:
        getTitleResponse.statusCode() == HttpCode.NO_CONTENT.code()
    }

    def "should send request with headers and receive response with new headers"() {
        given: 'server initialization'
        application.init()
        def headers = Headers.of("req-header-name", "req-header-value")

        and: 'wait for the server to be initialized'
        waitForServerInit()

        when: 'sends request with headers'
        def response = client.sendHeaders(headers)

        then: 'should receive the response headers'
        response.statusCode() == HttpCode.OK.code()
        with(response.headers()) {
            it.firstValue("resp-header-name").orElseThrow() == "resp-header-value"
        }
    }

    def 'should execute exception handler'() {
        given: 'exception handler'
        def statusCode = HttpCode.SERVICE_UNAVAILABLE
        ExceptionHandler<RuntimeException> handler = new ExceptionHandler<RuntimeException>() {
            @Override
            Class<RuntimeException> exceptionClass() {
                return RuntimeException.class
            }

            @Override
            void handle(RuntimeException exception, HttpContext ctx) {
                ctx.response()
                      .statusCode(statusCode)
                      .body(ByteResponseBody.of(exception.message))
            }
        }


        and: 'server initialization with exception handler'
        application.init(emptyList(), emptyList(), List.of(handler), emptyList())

        and: 'wait for the server to be initialized'
        waitForServerInit()

        when: 'makes request'
        def result = client.throwsException()

        then: 'result should have correct values'
        result.statusCode() == statusCode.code()
        result.body() == "Test message"
    }

    def "should modify HttpContext inside filter"() {
        given: 'given filter'
        def key = "My-Key"
        def value = "My-Value"
        Consumer<HttpContext> doBefore = (ctx) -> {
            ctx.response().headers(Headers.of(key, value))
        }

        def filter = filterBuilder()
              .doBefore(doBefore)
              .order(Order.of(0))
              .path(PATH_HELLO_WORLD)
              .build()

        and: 'server initialization'
        application.init(emptyList(), List.of(filter), emptyList(), emptyList())
        waitForServerInit()

        when: 'makes request'
        def result = client.getHelloWorld()

        then: 'result should have correct values'
        result.statusCode() == HttpCode.OK.code()
        result.body() == "Hello world"
        result.headers().firstValue(key).orElseThrow() == value
    }

    def "should register module"() {
        given: 'given json module'
        def jsonModule = new SimpleModule()
              .addSerializer(StubPerson.class, new StubPersonSerializer())
              .addDeserializer(StubPerson.class, new StubPersonDeserializer());
        def jsonBody = """
                                {
                                  "name": "John Doe",
                                  "age": 25
                                }
                              """

        and: 'server initialization'
        application.init(emptyList(), emptyList(), emptyList(), List.of(jsonModule))
        waitForServerInit()

        when: 'makes request'
        def result = client.jsonModule(jsonBody.bytes)

        then: 'result should have correct values'
        result.statusCode() == HttpCode.OK.code()
        result.headers().firstValue("Content-Type").get() == ContentType.APPLICATION_JSON.value()
        def body = Json.mapper().readValue(result.body(), StubPerson.class)
        body.p1 == "John Doe"
        body.p2 == 25
    }

    def waitForServerInit() {
        waitForServerInit(Duration.ofSeconds(5))
    }

    def waitForServerInit(Duration duration) {
        WaitFor.waitFor({ application.serverStatus() == ServerStatus.STARTED }, duration)
    }
}
