package io.samituga.bard.endpoint.request;

import io.samituga.bard.endpoint.request.type.PathParams;
import io.samituga.bard.endpoint.request.type.QueryParams;
import io.samituga.slumber.ivern.builder.Structure;
import jakarta.servlet.http.HttpServletRequest;

public interface HttpRequest extends Structure<HttpRequest, HttpRequestBuilder> {

    PathParams pathParams();

    QueryParams queryParams();

    HttpServletRequest request();
}
