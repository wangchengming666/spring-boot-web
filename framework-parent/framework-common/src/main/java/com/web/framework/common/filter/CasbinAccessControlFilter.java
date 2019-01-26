package com.web.framework.common.filter;

import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.web.framework.common.component.EnforcerComponent;
import com.web.framework.common.constants.AuthConstants;
import com.web.framework.common.constants.FrameworkConstants;
import com.web.framework.common.utils.R;
import com.web.framework.common.utils.ResponseUtil;
import com.web.framework.common.utils.S;

/**
 * Casbin 访问控制
 * 
 * @author cm_wang
 *
 */
public class CasbinAccessControlFilter extends StatelessFilter {

  public static final String FILTER_NAME = "casbinACFilter";

  @Autowired
  private EnforcerComponent enforcer;

  @Override
  protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
      Object mappedValue) {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    String subjectId = null;
    // 是否user,app登录
    Subject subject = getSubject(request, response);
    // 没有认证，使用匿名
    if (null == subject || !subject.isAuthenticated()) {
      subjectId = AuthConstants.ANONYM;
    } else {
      Map<String, Object> jwtMaps = getTokenBody(subject);
      if (jwtMaps.containsKey("uid")) {
        subjectId = FrameworkConstants.USER_PREFIX + jwtMaps.get("uid");
      } else if (jwtMaps.containsKey("appKey")) {
        subjectId = FrameworkConstants.APP_PREFIX + jwtMaps.get("appKey");
      } else {
        throw new IllegalArgumentException("Token 信息不完整!");
      }
    }
    // enforcer.refresh();

    String path = httpRequest.getRequestURI();

    //enforcer.enableEnforce(true);
    return enforcer.enforce(subjectId, path);
  }

  @Override
  protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
      throws Exception {
    ResponseUtil.response(WebUtils.toHttp(request), WebUtils.toHttp(response), R.data(S.S_401));
    return false;
  }

}
