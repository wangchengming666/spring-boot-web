package com.web.framework.common.utils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import com.web.framework.common.constants.KeysConstants;

/**
 * ServletRequest Util
 * 
 * @author cm_wang
 *
 */
public class RequestUtil {

  /**
   * 从 reqeust得到 jwt token<br/>
   * 顺序是 head Authorization, cookies, url
   * 
   * @param request
   * @return
   */
  public static String getJwtToken(ServletRequest request) {
    String jwt = null;
    if ((request instanceof HttpServletRequest)) {
      HttpServletRequest httpServletRequest = (HttpServletRequest) request;

      jwt = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
      if (StringUtils.isNotBlank(jwt)) {
        return autoRemoveBearerSchema(jwt);
      }
      jwt = CookieUtil.getValue(httpServletRequest, KeysConstants.JWT_ID);
      if (StringUtils.isNotBlank(jwt)) {
        return autoRemoveBearerSchema(jwt);
      }

    }
    jwt = request.getParameter(KeysConstants.JWT_ID);
    if (StringUtils.isNotBlank(jwt)) {
      return autoRemoveBearerSchema(jwt);
    }

    return null;
  }

  private static String autoRemoveBearerSchema(String authorization) {
    return StringUtils.removeStart(authorization, JWTUtil.BEARER_SCHEMA);
  }
}
