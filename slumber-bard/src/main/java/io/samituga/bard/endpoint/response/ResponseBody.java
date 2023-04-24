package io.samituga.bard.endpoint.response;

import io.samituga.bard.endpoint.ContentType;
import java.io.InputStream;

public interface ResponseBody {

    InputStream toInputStream();
    ContentType contentType();
    Object responseBody();
}
