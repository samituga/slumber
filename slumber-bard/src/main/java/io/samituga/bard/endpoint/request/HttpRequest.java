package io.samituga.bard.endpoint.request;

import io.samituga.bard.endpoint.request.type.MultipartRequestBody;
import io.samituga.bard.endpoint.request.type.PathParams;
import io.samituga.bard.endpoint.request.type.QueryParams;
import io.samituga.bard.endpoint.request.type.RequestAsTypeBody;
import io.samituga.bard.endpoint.request.type.RequestBody;
import io.samituga.slumber.ivern.http.type.Headers;
import io.samituga.slumber.ivern.structure.Structure;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface HttpRequest {

    Headers headers();

    PathParams pathParams();

    QueryParams queryParams();

    HttpServletRequest request();

    Optional<RequestBody> requestBody();

    <T> Optional<RequestAsTypeBody<T>> requestBodyAsType(Class<T> type); // TODO: 23/04/2023 remove request suffix

    Optional<MultipartRequestBody> multipartFile(String fileName);
}
