package io.samituga.bard.fixture;

import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import io.samituga.bard.endpoint.HttpCode;
import io.samituga.bard.endpoint.Response;
import io.samituga.bard.endpoint.ResponseBody;
import io.samituga.slumber.ivern.http.type.Headers;
import java.util.Optional;

public class ResponseTestData {

    public static ResponseBuilder aResponse() {
        return responseBuilder().statusCode(HttpCode.OK);
    }

    public static ResponseBuilder responseBuilder() {
        return new ResponseBuilder();
    }

    public static class ResponseBuilder {

        private HttpCode statusCode;
        private Optional<ResponseBody> responseBody = Optional.empty();
        private Headers headers = Headers.empty();

        private ResponseBuilder() {}

        public ResponseBuilder statusCode(HttpCode statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public ResponseBuilder responseBody(ResponseBody responseBody) {
            this.responseBody = Optional.ofNullable(responseBody);
            return this;
        }

        public ResponseBuilder headers(Headers headers) {
            this.headers = headers;
            return this;
        }

        public Response build() {
            return build(false);
        }

        public Response build(boolean skipValidation) {
            if (!skipValidation) {
                validate(this);
            }
            return new Response() {
                @Override
                public HttpCode statusCode() {
                    return statusCode;
                }

                @Override
                public Optional<ResponseBody> responseBody() {
                    return responseBody;
                }

                @Override
                public Headers headers() {
                    return headers;
                }
            };
        }
    }

    private static void validate(ResponseBuilder builder) {
        validateStatusCode(builder.statusCode);
    }

    private static void validateStatusCode(HttpCode statusCode) {
        required("statusCode", statusCode);
    }
}
