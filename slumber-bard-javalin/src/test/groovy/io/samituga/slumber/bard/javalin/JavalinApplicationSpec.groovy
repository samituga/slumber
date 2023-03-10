package io.samituga.slumber.bard.javalin

import io.samituga.bard.ServerStatus
import io.samituga.bard.endpoint.HttpCode
import io.samituga.slumber.bard.javalin.stub.StubClient
import io.samituga.slumber.bard.javalin.stub.StubServer
import io.samituga.slumber.ziggs.WaitFor
import spock.lang.Specification

import java.time.Duration

class JavalinApplicationSpec extends Specification {


    private StubServer application
    private StubClient client

    def setup() {
        application = new StubServer()
        client = new StubClient()
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
}
