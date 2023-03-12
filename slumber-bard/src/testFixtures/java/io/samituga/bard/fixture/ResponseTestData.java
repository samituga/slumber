package io.samituga.bard.fixture;

import io.samituga.bard.endpoint.HttpCode;
import io.samituga.bard.endpoint.Response;
import io.samituga.slumber.heimer.validator.AssertionUtility;
import io.samituga.slumber.ivern.http.type.Headers;
import java.util.Optional;

public class ResponseTestData {

    public static <T> ResponseBuilder<T> defaultResponse() {
        return ResponseTestData.<T>responseBuilder().statusCode(HttpCode.OK);
    }

    public static <T> ResponseBuilder<T> responseBuilder() {
        return new ResponseBuilder<>();
    }

    public static class ResponseBuilder<T> {

        private HttpCode statusCode;
        private Optional<T> responseBody = Optional.empty();
        private Headers headers = Headers.empty();

        private ResponseBuilder() {}

        public ResponseBuilder<T> statusCode(HttpCode statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public ResponseBuilder<T> responseBody(T responseBody) {
            this.responseBody = Optional.ofNullable(responseBody);
            return this;
        }

        public ResponseBuilder<T> headers(Headers headers) {
            this.headers = headers;
            return this;
        }

        public Response<T> build() {
            return build(false);
        }

        public Response<T> build(boolean skipValidation) {
            if (!skipValidation) {
                validate(this);
            }
            return new Response<>() {
                @Override
                public HttpCode statusCode() {
                    return statusCode;
                }

                @Override
                public Optional<T> responseBody() {
                    return responseBody;
                }

                @Override
                public Headers headers() {
                    return headers;
                }
            };
        }
    }

    private static void validate(ResponseBuilder<?> builder) {
        validateStatusCode(builder.statusCode);
    }

    private static void validateStatusCode(HttpCode statusCode) {
        AssertionUtility.required("statusCode", statusCode);
    }
}
