package io.samituga.bard.endpoint.response;

import io.samituga.slumber.ivern.http.type.Headers;
import io.samituga.slumber.ivern.structure.Structure;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

public interface HttpResponse extends Structure<HttpResponse, HttpResponseBuilder> {

    HttpCode statusCode();

    Headers headers();

    HttpServletResponse response();

    Optional<ResponseBody> responseBody();
}
