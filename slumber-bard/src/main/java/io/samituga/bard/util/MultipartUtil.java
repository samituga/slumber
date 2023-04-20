package io.samituga.bard.util;

import io.samituga.bard.endpoint.request.type.MultipartRequestBody;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class MultipartUtil {
    static final String MULTIPART_CONFIG_ATTRIBUTE = "org.eclipse.jetty.multipartConfig";
    private static final MultipartConfigElement DEFAULT_CONFIG =
          new MultipartConfigElement(System.getProperty("java.io.tmpdir"));

    private MultipartUtil() {
    }

    public static List<MultipartRequestBody> getUploadedFiles(HttpServletRequest req)
          throws ServletException, IOException {
        return getUploadedFiles(req, Optional.empty());
    }

    public static List<MultipartRequestBody> getUploadedFiles(HttpServletRequest req,
                                                              Optional<String> partName)
          throws ServletException, IOException {
        ensureMultipartConfig(req);
        return req.getParts().stream()
              .filter(part -> isValidFile(part, partName))
              .map(MultipartRequestBody::new)
              .collect(Collectors.toList());
    }

    public static Map<String, List<MultipartRequestBody>> getUploadedFileMap(HttpServletRequest req)
          throws ServletException, IOException {
        ensureMultipartConfig(req);
        return req.getParts().stream()
              .filter(MultipartUtil::isFile)
              .collect(Collectors.groupingBy(Part::getName,
                    Collectors.mapping(MultipartRequestBody::new, Collectors.toList())));
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
