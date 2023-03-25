package io.samituga.bard.endpoint.response;

import io.samituga.bard.endpoint.HttpCode;
import io.samituga.slumber.ivern.http.type.Headers;
import java.util.Optional;

public interface HttpResponse {

    HttpCode statusCode();

    Headers headers();

    Optional<ResponseBody> responseBody();
}
