package com.web.framework.common.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.servlet.AdviceFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsProcessor;
import org.springframework.web.cors.DefaultCorsProcessor;

public class CorsFilter extends AdviceFilter {
  private CorsProcessor corsProcessor = new DefaultCorsProcessor();

  public final static String FILTER_NAME = "corsFilter";

  /**
   * 原方法 直接是 corsProcessor.processRequest(buildConfig(), req, res)
   * 
   * @return
   */
  @Override
  protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
    HttpServletRequest req = WebUtils.toHttp(request);
    HttpServletResponse res = WebUtils.toHttp(response);
    // 当为PreFlight options请求，才处理
    // 避免每个返回，加上Access-Control-Allow-Origin
    /*
     * if (CorsUtils.isPreFlightRequest(req)) { return corsProcessor.processRequest(buildConfig(),
     * req, res); } else { return true; }
     */

    return corsProcessor.processRequest(buildConfig(), req, res);
  }

  private CorsConfiguration buildConfig() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    // 允许跨域访问的域名
    corsConfiguration.addAllowedOrigin("*");
    // 请求头
    corsConfiguration.addAllowedHeader("*");
    // 请求方法
    corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
    corsConfiguration.addAllowedMethod(HttpMethod.POST);
    corsConfiguration.addAllowedMethod(HttpMethod.GET);
    corsConfiguration.addAllowedMethod(HttpMethod.PUT);
    corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
    corsConfiguration.addAllowedMethod(HttpMethod.OPTIONS);
    // 预检请求的有效期，单位为秒。
    corsConfiguration.setMaxAge(3600L);
    // 是否支持安全证书
    corsConfiguration.setAllowCredentials(true);

    return corsConfiguration;
  }

}
