package com.web.framework.common.filter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.web.servlet.AdviceFilter;

/**
 * 打印请求日志，java依赖 shiro,apache commons
 * 
 * @author cm_wang
 * @version 20181203
 */
public class LogFilter extends AdviceFilter {

  public final static String FILTER_NAME = "logFilter";

  private static String BREAK = "\n";

  private int length = -20;

  private Long start;
  private StringBuffer sb;
  /**
   * 不打印的paths
   */
  private List<String> ignorePaths = Collections.EMPTY_LIST;

  @Override
  protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
    HttpServletRequest req = toHttp(request);
    HttpServletResponse res = toHttp(response);
    if (isIgnorePaths(req.getRequestURI())) {
      return true;
    }
    sb = new StringBuffer();
    sb.append(">>>>").append(BREAK);
    sb.append(formatString(req.getScheme() + "      " + req.getMethod(), req.getRequestURI()))
        .append(BREAK);
    sb.append(formatString("QueryString", req.getQueryString())).append(BREAK);
    sb.append(formatString("ContentType", req.getContentType())).append(BREAK);
    sb.append(formatString("X-Requested-With", req.getHeader("X-Requested-With"))).append(BREAK);
    sb.append(formatString("Parameter", parameters(req))).append(BREAK);
    sb.append(formatString("RemoteHost", req.getRemoteHost() + ":" + req.getRemotePort()))
        .append(BREAK);
    sb.append(formatString("Headers", toStr(req.getHeaderNames()))).append(BREAK);
    sb.append(formatString("Cookies", printCookie(req, BREAK))).append(BREAK);

    start = System.currentTimeMillis();
    return true;
  }

  @Override
  protected void postHandle(ServletRequest request, ServletResponse response) throws Exception {
    HttpServletRequest req = toHttp(request);
    HttpServletResponse res = toHttp(response);
    if (!isIgnorePaths(req.getRequestURI())) {

      sb.append("<<<<").append(BREAK);
      sb.append(formatString("ContentType", response.getContentType())).append(BREAK);
      sb.append(formatString("Headers", printHeaders(res, BREAK))).append(BREAK);
      sb.append(formatString("Cookies", printCookie(req, BREAK)));
      sb.append(formatString("cost", (System.currentTimeMillis() - start) + " ms")).append(BREAK);

      /**
       * 打印红色
       */
      System.err.println(sb.toString());
    }
  }

  private boolean isIgnorePaths(String path) {
    return ignorePaths.stream().anyMatch(t -> StringUtils.startsWith(path, t));
  }

  public List<String> getIgnorePaths() {
    return ignorePaths;
  }

  public void setIgnorePaths(List<String> ignorePaths) {
    this.ignorePaths = ignorePaths;
  }

  /**
   * 下面是帮助类
   * 
   */

  public String printHeaders(HttpServletResponse response, String breakStr) {
    StringBuffer sb = new StringBuffer();
    Collection<String> headers = response.getHeaderNames();

    for (String headName : headers) {
      sb.append("\t").append(headName).append(" = ").append(response.getHeader(headName))
          .append(breakStr);
    }

    return sb.toString();
  }

  private String parameters(HttpServletRequest req) {

    StringBuffer sb = new StringBuffer();

    Enumeration<String> enumeration = req.getParameterNames();
    while (enumeration.hasMoreElements()) {
      String param = enumeration.nextElement();

      String[] values = req.getParameterValues(param);
      sb.append(BREAK).append("\t");
      if (values != null) {
        if (values.length == 1) {
          sb.append(formatString(length + 10, new Object[] {param, values[0]}));
        } else {
          sb.append(formatString(length + 10, new Object[] {param, Arrays.toString(values)}));
        }
      }

    }
    if (sb.length() == 0) {
      return null;
    } else {
      return sb.toString();
    }
  }

  private String toStr(Object object) {
    return ReflectionToStringBuilder.toString(object, ToStringStyle.SHORT_PREFIX_STYLE);
  }

  private HttpServletRequest toHttp(ServletRequest request) {
    return (HttpServletRequest) request;
  }

  public HttpServletResponse toHttp(ServletResponse response) {
    return (HttpServletResponse) response;
  }

  public String printCookie(HttpServletRequest request, String breakStr) {
    StringBuffer sb = new StringBuffer();
    // 获取所有的cookie值
    Cookie[] cookies = request.getCookies();
    if (cookies != null && cookies.length > 0) {
      for (int i = 0; i < cookies.length; i++) {
        Cookie cookie = null;
        cookie = cookies[i];
        sb.append("\t").append(cookie.getName()).append(" = ")
            .append(getValue(request, cookie.getName())).append(breakStr);
      }
    }
    return sb.toString();
  }

  public String getValue(HttpServletRequest httpServletRequest, String name) {
    Cookie cookie = getCookie(httpServletRequest, name);
    return cookie != null ? cookie.getValue() : null;
  }

  private String formatString(int length, Object... args) {
    if (args.length == 1) {
      return String.format("%" + length + "s:", args);
    } else if (args.length == 2) {
      return String.format("%" + length + "s: %s", args);
    } else {
      return String.format("%" + length + "s: %s %s", args);
    }
  }

  public Cookie getCookie(HttpServletRequest request, String name) {
    Objects.requireNonNull(request);
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (name.equals(cookie.getName())) {
          return cookie;
        }
      }
    }
    return null;
  }

  private String formatString(Object... args) {
    return formatString(length, args);
  }

}
