package io.samituga.bard.fixture;

import static io.samituga.bard.endpoint.response.HttpResponseBuilder.httpResponseBuilder;

import io.samituga.bard.endpoint.response.HttpCode;
import io.samituga.bard.endpoint.response.HttpResponseBuilder;

public class HttpResponseTestData {

    public static HttpResponseBuilder aResponse() {
        return httpResponseBuilder().statusCode(HttpCode.OK);
    }

}
