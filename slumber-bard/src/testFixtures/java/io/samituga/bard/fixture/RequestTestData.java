package io.samituga.bard.fixture;

import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import io.samituga.bard.endpoint.Request;
import io.samituga.bard.endpoint.type.PathParamName;
import io.samituga.bard.endpoint.type.PathParamValue;
import io.samituga.bard.endpoint.type.PathParams;
import io.samituga.bard.endpoint.type.QueryParamName;
import io.samituga.bard.endpoint.type.QueryParamValue;
import io.samituga.bard.endpoint.type.QueryParams;
import jakarta.servlet.http.HttpServletRequest;

public class RequestTestData {

    public static RequestBuilder aRequest() {
        return requestBuilder()
              .pathParams(PathParams.of(PathParamName.of("name"), PathParamValue.of("value")))
              .queryParams(QueryParams.of(QueryParamName.of("query"), QueryParamValue.of("Qvalue")));
    }

    public static RequestBuilder requestBuilder() {
        return new RequestBuilder();
    }

    public static class RequestBuilder {

        private PathParams pathParams;
        private QueryParams queryParams;
        private HttpServletRequest request;

        private RequestBuilder() {}

        public RequestBuilder pathParams(PathParams pathParams) {
            this.pathParams = pathParams;
            return this;
        }

        public RequestBuilder queryParams(QueryParams queryParams) {
            this.queryParams = queryParams;
            return this;
        }

        public RequestBuilder request(HttpServletRequest request) {
            this.request = request;
            return this;
        }

        public Request build() {
            return build(false);
        }

        public Request build(boolean skipValidation) {
            if (!skipValidation) {
                validate(this);
            }
            return new Request() {

                @Override
                public PathParams pathParams() {
                    return pathParams;
                }

                @Override
                public QueryParams queryParams() {
                    return queryParams;
                }

                @Override
                public HttpServletRequest request() {
                    return request;
                }
            };
        }
    }

    private static void validate(RequestBuilder builder) {
        validatePathParams(builder.pathParams);
        validateQueryParams(builder.queryParams);
    }

    private static void validatePathParams(PathParams pathParams) {
        required("pathParams", pathParams);
    }

    private static void validateQueryParams(QueryParams queryParams) {
        required("queryParams", queryParams);
    }
}
