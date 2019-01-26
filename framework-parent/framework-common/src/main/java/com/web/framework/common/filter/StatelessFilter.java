package com.web.framework.common.filter;

import java.util.Map;
import javax.servlet.ServletRequest;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import com.web.framework.common.utils.RequestUtil;

/**
 * 无状态过滤器--抽象父类 <br/>
 * 认证 (authentication) 和授权 (authorization) 的区别
 * 
 */
public abstract class StatelessFilter extends AccessControlFilter {

  protected boolean isJwtSubmission(ServletRequest request) {
    return getJwtToken(request) != null;
  }

  /**
   * 尝试得到 jwt token
   * 
   * @param request
   * @return
   */
  protected String getJwtToken(ServletRequest request) {
    return RequestUtil.getJwtToken(request);
  }

  public Map<String, Object> getTokenBody(Subject subject) {
    return (Map<String, Object>) subject.getPrincipal();
  }

}
