package com.web.framework.common.handler;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.util.UrlPathHelper;
import com.web.framework.common.constants.FrameworkConstants;

public class WebFileHttpRequestHandler extends ResourceHttpRequestHandler {
  protected Logger logger = LoggerFactory.getLogger(getClass());
  private UrlPathHelper urlPathHelper = new UrlPathHelper();

  @Override
  public void handleRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    super.handleRequest(request, response);

  }

  @Override
  protected Resource getResource(HttpServletRequest request) throws IOException {
    String path = urlPathHelper.getLookupPathForRequest(request);

    Resource resource = new FileSystemResource(
        new File(FrameworkConstants.ROOT_DIR, "webfile" + File.separatorChar + path));
    if (resource.exists()) {
      if (resource.isReadable()) {
        return resource;
      } else {
        logger.error(" Can't read {} file!",
            new AccessDeniedException(resource.getFile().getPath()));
      }
    }
    return null;
  }
}
