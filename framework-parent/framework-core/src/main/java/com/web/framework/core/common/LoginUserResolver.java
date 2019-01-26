
package com.web.framework.core.common;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import com.web.framework.common.annotation.LoginUser;
import com.web.framework.core.entity.UcUser;

/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 */
public class LoginUserResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().isAssignableFrom(UcUser.class)
        && parameter.hasParameterAnnotation(LoginUser.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
      NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
    // 获取用户ID
    System.out.println(TokenUtil.getJwtTokenBody());

    // 获取用户信息

    return new Object();
  }
}
