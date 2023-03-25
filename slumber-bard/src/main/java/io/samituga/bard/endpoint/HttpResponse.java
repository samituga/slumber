package io.samituga.bard.endpoint;

import io.samituga.slumber.ivern.http.type.Headers;
import java.util.Optional;

public interface HttpResponse {

    HttpCode statusCode();

    Optional<ResponseBody> responseBody();

    Headers headers();

}
