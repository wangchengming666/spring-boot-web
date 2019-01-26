package com.web.framework.common.filter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.web.framework.common.token.JwtToken;
import com.web.framework.common.utils.R;
import com.web.framework.common.utils.ResponseUtil;
import com.web.framework.common.utils.S;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

/**
 * JWT <br/>
 * 认证 (authentication)
 * 
 * @author cm_wang
 *
 */
public class JwtAuthcFilter extends StatelessFilter {
  protected Logger logger = LoggerFactory.getLogger(getClass());

  public static final String FILTER_NAME = "jwtAuthcFilter";
  /**
   * 不需要认证的paths
   */
  private List<String> excludePaths = Collections.EMPTY_LIST;

  @Override
  protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
      Object mappedValue) throws Exception {
    HttpServletRequest httpRequest = WebUtils.toHttp(request);
    String uri = httpRequest.getRequestURI();
    if (isExcludePaths(uri)) {
      return true;
    }
    if (null != getSubject(request, response) && getSubject(request, response).isAuthenticated()) {
      return true;
    }
    return false;
  }

  public AuthenticationToken createToken(ServletRequest request, ServletResponse response)
      throws Exception {
    String host = request.getRemoteHost();
    String token = getJwtToken(request);
    if (token == null) {
      logger.debug("jwt token 没有找到");
      throw new NullPointerException("jwt token 没有找到");
    }
    return new JwtToken(host, token);
  }

  @Override
  protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
      throws Exception {
    if (isJwtSubmission(request)) {
      try {
        AuthenticationToken token = createToken(request, response);
        Subject subject = getSubject(request, response);
        subject.login(token);
        return true;
      } catch (Exception e) {
        if (e.getCause() instanceof ExpiredJwtException) {
          ResponseUtil.response(WebUtils.toHttp(request), WebUtils.toHttp(response),
              R.data(S.S_402));
        } else if (e.getCause() instanceof JwtException) {
          ResponseUtil.response(WebUtils.toHttp(request), WebUtils.toHttp(response),
              R.data(S.S_403));
        } else {
          ResponseUtil.response(WebUtils.toHttp(request), WebUtils.toHttp(response),
              R.data(S.S_FAIL));
        }
      }
    }
    ResponseUtil.response(WebUtils.toHttp(request), WebUtils.toHttp(response), R.data(S.S_400));
    return false;

  }

  private boolean isExcludePaths(String uri) {
    if (StringUtils.isBlank(uri)) {
      return true;
    }

    for (String excludePath : excludePaths) {
      if (StringUtils.startsWith(uri, excludePath)) {
        return true;
      }
    }

    return false;
  }

  public List<String> getExcludePaths() {
    return excludePaths;
  }

  /**
   * 不需要认证的路径 prefix
   * 
   * @param excludePaths
   */
  public void setExcludePaths(List<String> excludePaths) {
    this.excludePaths = excludePaths;
  }

  /**
   * 不需要认证的路径 prefix
   * 
   * @param excludePath
   */
  public void setExcludePath(String excludePath) {
    setExcludePaths(Arrays.asList(excludePath));
  }
}
