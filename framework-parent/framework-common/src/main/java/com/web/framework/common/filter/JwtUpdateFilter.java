package com.web.framework.common.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import com.web.framework.common.constants.KeysConstants;
import com.web.framework.common.utils.CookieUtil;
import com.web.framework.common.utils.JWTUtil;
import com.web.framework.common.utils.RequestUtil;
import com.web.framework.common.utils.ResponseUtil;
import com.web.framework.common.utils.S;
import com.web.framework.common.wrapper.JwtResponseWrapper;
import com.web.framework.common.wrapper.RDataWrapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwt;

/**
 * 自动续期 Jwt <br/>
 * 如果是ajax访问，修改返回码<br/>
 * 如果是页面访问，生成新的JWT Cookie
 * 
 * @author cm_wang
 *
 */
public class JwtUpdateFilter implements Filter {

  protected Logger logger = LoggerFactory.getLogger(getClass());

  public final static String FILTER_NAME = "jwtExpirationFilter";

  private List<MediaType> supportedMediaTypes = new ArrayList<>();

  /**
   * token过期之前 多少（秒）内，自动续期<br/>
   */
  private Integer beforeExpiration = 3600 - 1800;
  /**
   * 控制开关
   */
  private boolean enableUpdate = true;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    supportedMediaTypes.add(MediaType.APPLICATION_JSON);
    // supportedMediaTypes.add(MediaType.TEXT_HTML);
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;

    String jwt = RequestUtil.getJwtToken(request);
    /**
     * 需要续期
     */
    if (enableUpdate && StringUtils.isNotBlank(jwt) && isExpirationIncrese(jwt)) {
      if (beforeExpiration == null) {
        throw new NullPointerException();
      }

      JwtResponseWrapper wrapper = new JwtResponseWrapper(httpServletResponse);

      chain.doFilter(request, wrapper);

      String newJwt = buildNewToken(jwt);
      // ajax请求
      if (ResponseUtil.isAjax(httpServletRequest)) {

        if (canHandle(MediaType.parseMediaType(response.getContentType()))) {
          expirationIncrese(newJwt, wrapper, response);
        }

        // Web请求
      } else {
        // new token放入 Cookie
        CookieUtil.create(httpServletResponse, KeysConstants.JWT_ID, newJwt);
      }
      rewrite(wrapper.toByteArray(), httpServletResponse);
    } else {

      chain.doFilter(request, response);

    }

  }

  protected boolean canHandle(MediaType mediaType) {
    if (mediaType == null) {
      return true;
    }
    for (MediaType supportedMediaType : getSupportedMediaTypes()) {
      if (supportedMediaType.includes(mediaType)) {
        return true;
      }
    }
    return false;
  }

  private String buildNewToken(String jwt) {
    Jwt<JwsHeader, Claims> jwtData = JWTUtil.getJwsNoSign(jwt);
    Claims body = jwtData.getBody();
    Map<String, Object> jwsHeader = jwtData.getHeader();
    Date exp = body.getExpiration();
    Date iat = body.getIssuedAt();
    long ttlmilliseconds = exp.getTime() - iat.getTime();
    long now = System.currentTimeMillis();
    if (ttlmilliseconds > 0) {
      body.setExpiration(new Date(now + ttlmilliseconds));
      body.setIssuedAt(new Date(now));

      String netJwt = JWTUtil.generateToken(KeysConstants.JWT_KEY_BYTES, jwsHeader, body);
      logger.debug("自动延期 JWTtoken ", netJwt);
      return netJwt;
    } else {
      return null;
    }

  }

  /**
   * 生成新的token，延长过期时间,修改 exp,iat，和摘要，其它值不改
   * 
   * @param newJwt
   * @param response
   */
  private void expirationIncrese(String newJwt, JwtResponseWrapper response,
      ServletResponse srcResponse) {
    String newData = wrapperJson(new String(response.toByteArray()), newJwt);
    rewrite(newData.getBytes(), srcResponse);
  }

  private String wrapperJson(String jsonString, String newJwt) {
    RDataWrapper wrapper = new RDataWrapper(jsonString).setExtension(newJwt).setStatus(S.S_405);
    return wrapper.toString();
  }

  private void rewrite(byte[] content, ServletResponse srcResponse) {
    try {
      OutputStream out = srcResponse.getOutputStream();
      // 必须设置ContentLength
      srcResponse.setContentLength(content.length);
      out.write(content);
      out.flush();
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  /**
   * 是否需要续期
   * 
   * @param jwt
   * @return
   */
  private boolean isExpirationIncrese(String jwt) {
    Claims body = null;
    try {
      body = JWTUtil.getJwsNoSign(jwt).getBody();
    } catch (Exception e) {
      // ignore
      return false;
    }
    Date exp = body.getExpiration();
    Date iat = body.getIssuedAt();
    long maxInterval = beforeExpiration * 1000L; // 最大间隔 seconds
    long now = System.currentTimeMillis();

    if (exp.getTime() - iat.getTime() < maxInterval) {
      throw new IllegalArgumentException("beforeExpiration 不能大于，会话时间段！");
    }

    // 当前时间>=过期时间-间隔
    if (exp.getTime() - maxInterval <= now && now < exp.getTime()) {
      return true;
    } else {
      return false;
    }

  }

  public List<MediaType> getSupportedMediaTypes() {
    return Collections.unmodifiableList(this.supportedMediaTypes);
  }

  @Override
  public void destroy() {}

}
