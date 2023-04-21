package io.samituga.bard.util;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

import io.samituga.bard.endpoint.request.type.MultipartRequestBody;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MultipartUtil {
    static final String MULTIPART_CONFIG_ATTRIBUTE = "org.eclipse.jetty.multipartConfig";
    private static final Logger LOG = LoggerFactory.getLogger(MultipartUtil.class);
    private static final MultipartConfigElement DEFAULT_CONFIG =
          new MultipartConfigElement(System.getProperty("java.io.tmpdir"));

    private MultipartUtil() {
    }

    public static List<MultipartRequestBody> getUploadedFiles(HttpServletRequest req) {
        return getUploadedFiles(req, Optional.empty());
    }

    public static List<MultipartRequestBody> getUploadedFiles(HttpServletRequest req,
                                                              Optional<String> partName) {
        ensureMultipartConfig(req);
        try {
            return req.getParts().stream()
                  .filter(part -> isValidFile(part, partName))
                  .map(MultipartRequestBody::new)
                  .collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (ServletException e) {
            LOG.warn(e.getLocalizedMessage(), e);
            return emptyList();
        }
    }

    public static Map<String, List<MultipartRequestBody>> getUploadedFileMap(
          HttpServletRequest req) {
        ensureMultipartConfig(req);
        try {
            return req.getParts().stream()
                  .filter(MultipartUtil::isFile)
                  .collect(Collectors.groupingBy(Part::getName,
                        Collectors.mapping(MultipartRequestBody::new, Collectors.toList())));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (ServletException e) {
            LOG.warn(e.getLocalizedMessage(), e);
            return emptyMap();
        }
    }

    private static void ensureMultipartConfig(HttpServletRequest req) {
        if (req.getAttribute(MULTIPART_CONFIG_ATTRIBUTE) == null) {
            req.setAttribute(MULTIPART_CONFIG_ATTRIBUTE, DEFAULT_CONFIG);
        }
    }

    private static boolean isValidFile(Part part, Optional<String> partName) {
        return isFile(part) && (partName.isEmpty() || part.getName().equals(partName.get()));
    }

    private static boolean isField(Part filePart) {
        return filePart.getSubmittedFileName() == null;
    }

    private static boolean isFile(Part filePart) {
        return !isField(filePart);
    }
}
