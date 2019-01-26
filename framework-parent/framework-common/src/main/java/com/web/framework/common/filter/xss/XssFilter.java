package com.web.framework.common.filter.xss;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * XSS过滤
 */
public class XssFilter implements Filter {
  
  public final static String FILTER_NAME = "xssFilter";

  @Override
  public void init(FilterConfig config) throws ServletException {}

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    XssHttpServletRequestWrapper xssRequest =
        new XssHttpServletRequestWrapper((HttpServletRequest) request);
    chain.doFilter(xssRequest, response);
  }

  @Override
  public void destroy() {}

}
