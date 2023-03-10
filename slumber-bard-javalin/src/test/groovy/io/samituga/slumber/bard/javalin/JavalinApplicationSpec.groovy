package io.samituga.slumber.bard.javalin

import io.samituga.bard.ServerStatus
import io.samituga.bard.endpoint.HttpCode
import io.samituga.bard.filter.Precedence
import io.samituga.bard.filter.type.Order
import io.samituga.bard.fixture.FilterTestData
import io.samituga.slumber.bard.javalin.stub.StubClient
import io.samituga.slumber.bard.javalin.stub.StubServer
import io.samituga.slumber.ziggs.WaitFor
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import spock.lang.Specification

import java.time.Duration
import java.time.Instant
import java.util.function.BiConsumer

import static io.samituga.slumber.bard.javalin.stub.StubServer.PATH_HELLO_WORLD

class JavalinApplicationSpec extends Specification {


    private StubServer application
    private StubClient client

    def setup() {
        application = new StubServer()
        client = new StubClient()
    }

    def cleanup(){
        application.cleanup()
    }


    def 'should start server and create endpoint'() {
        given: 'server initialization'
        application.init()

        and: 'wait for the server to be initialized'
        WaitFor.waitFor({ application.serverStatus() == ServerStatus.STARTED }, Duration.ofSeconds(5))

        when: 'makes request'
        def result = client.getHelloWorld()

        then: 'result should have correct values'
        result.statusCode() == HttpCode.OK.code()
        result.body() == "Hello world"
    }

    def 'should make post request and create title'() {
        given: 'server initialization'
        application.init()
        def title = "The Big And The Small"

        and: 'wait for the server to be initialized'
        WaitFor.waitFor({ application.serverStatus() == ServerStatus.STARTED }, Duration.ofSeconds(5))

        when: 'makes request'
        def postTitleResponse = client.postTitle(title)

        then: 'result should have correct values'
        postTitleResponse.statusCode() == HttpCode.CREATED.code()
        !postTitleResponse.body().isBlank()
        def resultBody = postTitleResponse.body()
        def uuid = UUID.fromString(resultBody);
        uuid != null

        and: 'should get the newly crated title'
        def getTitleResponse = client.getTitle(uuid)

        then:
        getTitleResponse.statusCode() == HttpCode.OK.code()
        getTitleResponse.body() == title
    }


    def 'should execute filter doBefore before doAfter'() {
        given: 'server initialization'
        Instant doBeforeTimestamp = null
        Instant doAfterTimestamp = null

        BiConsumer<HttpServletRequest, HttpServletResponse> doBefore =
          (req, resp) -> { doBeforeTimestamp = Instant.now(); Thread.sleep(2) }
        BiConsumer<HttpServletRequest, HttpServletResponse> doAfter =
          (req, resp) -> { doAfterTimestamp = Instant.now(); Thread.sleep(2) }

        def filter = FilterTestData.filterBuilder()
          .doBefore(doBefore)
          .doAfter(doAfter)
          .order(Order.of(0))
          .path(PATH_HELLO_WORLD)
          .build()

        application.init(Collections.emptyList(), List.of(filter))


        and: 'wait for the server to be initialized'
        WaitFor.waitFor({ application.serverStatus() == ServerStatus.STARTED }, Duration.ofSeconds(5))

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

        BiConsumer<HttpServletRequest, HttpServletResponse> firstDoBefore =
          (req, resp) -> { firstDoBeforeTimestamp = Instant.now(); Thread.sleep(2) }
        BiConsumer<HttpServletRequest, HttpServletResponse> firstDoAfter =
          (req, resp) -> { firstDoAfterTimestamp = Instant.now(); Thread.sleep(2) }

        def firstFilter = FilterTestData.filterBuilder()
          .doBefore(firstDoBefore)
          .doAfter(firstDoAfter)
          .order(Order.of(Precedence.FIRST))
          .path(PATH_HELLO_WORLD)
          .build()

        Instant middleDoBeforeTimestamp = null
        Instant middleDoAfterTimestamp = null

        BiConsumer<HttpServletRequest, HttpServletResponse> middleDoBefore =
          (req, resp) -> { middleDoBeforeTimestamp = Instant.now(); Thread.sleep(2) }
        BiConsumer<HttpServletRequest, HttpServletResponse> middleDoAfter =
          (req, resp) -> { middleDoAfterTimestamp = Instant.now(); Thread.sleep(2) }

        def middleFilter = FilterTestData.filterBuilder()
          .doBefore(middleDoBefore)
          .doAfter(middleDoAfter)
          .order(Order.of(0))
          .path(PATH_HELLO_WORLD)
          .build()

        Instant lastDoBeforeTimestamp = null
        Instant lastDoAfterTimestamp = null

        BiConsumer<HttpServletRequest, HttpServletResponse> lastDoBefore =
          (req, resp) -> { lastDoBeforeTimestamp = Instant.now(); Thread.sleep(2) }
        BiConsumer<HttpServletRequest, HttpServletResponse> lastDoAfter =
          (req, resp) -> { lastDoAfterTimestamp = Instant.now(); Thread.sleep(2) }

        def lastFilter = FilterTestData.filterBuilder()
          .doBefore(lastDoBefore)
          .doAfter(lastDoAfter)
          .order(Order.of(Precedence.LAST))
          .path(PATH_HELLO_WORLD)
          .build()

        application.init(Collections.emptyList(), List.of(middleFilter, lastFilter,firstFilter))

        and: 'wait for the server to be initialized'
        WaitFor.waitFor({ application.serverStatus() == ServerStatus.STARTED }, Duration.ofSeconds(5))

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
}
