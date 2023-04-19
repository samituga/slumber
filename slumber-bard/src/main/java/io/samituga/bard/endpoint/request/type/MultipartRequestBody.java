package io.samituga.bard.endpoint.request.type;

import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import io.samituga.slumber.ivern.type.Type;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.function.Function;

public class MultipartRequestBody extends Type<Part> {

    public MultipartRequestBody(Part part) {
        super(required("part", part));
    }


    public InputStream inputStream() {
        try {
            return value().getInputStream();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public <T> T contentAndClose(Function<InputStream, T> callback) throws IOException {
        try (InputStream inputStream = inputStream()) {
            return callback.apply(inputStream);
        }
    }

    public String filename() {
        return value().getSubmittedFileName();
    }

    public String extension() {
        var filename = filename();
        return filename.substring(filename.lastIndexOf('.') + 1);
    }

    public long size() {
        return value().getSize();
    }

    public Optional<String> contentType() {
        return Optional.ofNullable(value().getContentType());
    }
}
