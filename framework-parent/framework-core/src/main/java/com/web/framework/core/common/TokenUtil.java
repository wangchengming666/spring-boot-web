package com.web.framework.core.common;

import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class TokenUtil {

  /**
   * 得到 jwt Token 信息
   * 
   * @return
   */
  public static Map<String, Object> getJwtTokenBody() {

    Subject subject = SecurityUtils.getSubject();

    // 未认证
    if (null == subject || !subject.isAuthenticated()) {
      throw new IllegalAccessError("用户未登录!");
    }
    return (Map<String, Object>) subject.getPrincipal();
  }

  /**
   * 得到 jwt Token 信息
   * 
   * @return
   */
  public static <T> T getJwtTokenValue(String key, Class<T> clazz) {
    Map<String, Object> map = getJwtTokenBody();

    return (T) map.get(key);
  }

}
