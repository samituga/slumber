package io.samituga.bard.endpoint.context;

import io.samituga.bard.endpoint.request.HttpRequest;
import io.samituga.bard.endpoint.response.HttpResponse;
import io.samituga.slumber.ivern.structure.Structure;

public interface HttpContext extends Structure<HttpContext, HttpContextBuilder> {

    HttpRequest request();

    HttpResponse response();

    HttpContext withResponse(HttpResponse response);
}
