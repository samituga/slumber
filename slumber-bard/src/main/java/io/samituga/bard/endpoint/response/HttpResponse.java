package io.samituga.bard.endpoint.response;

import io.samituga.slumber.ivern.structure.Structure;
import io.samituga.slumber.ivern.http.type.Headers;
import java.util.Optional;

public interface HttpResponse extends Structure<HttpResponse, HttpResponseBuilder> {

    HttpCode statusCode();

    Headers headers();

    Optional<ResponseBody> responseBody();
}
