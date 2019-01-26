
package com.web.framework.common.realm;

import java.util.Map;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import com.web.framework.common.matcher.JwtMatcher;
import com.web.framework.common.token.JwtToken;
import com.web.framework.common.utils.JWTUtil;

/**
 * 基于JWT（ JSON WEB TOKEN）的控制域
 * 
 * @author cm_wang
 */
public class JwtRealm extends AuthorizingRealm {

  public JwtRealm() {
    super();
    setCredentialsMatcher(new JwtMatcher());
  }

  @Override
  public boolean supports(AuthenticationToken token) {
    return token instanceof JwtToken;
  }

  public Class<?> getAuthenticationTokenClass() {
    return JwtToken.class;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
      throws AuthenticationException {
    // 只认证JwtToken
    if (!(token instanceof JwtToken))
      return null;

    String jwt = ((JwtToken) token).getJwt();
    Map<String, Object> body = null;
    // 预先解析Payload
    // 没有做任何的签名校验
    body = JWTUtil.getJwsNoSign(jwt).getBody();
    if (body == null) {
      throw new AuthenticationException("权限信息不正确，请重新登录！");
    }
    /**
     * principal:用户凭证 <br/>
     * credentials:信任，证书<br/>
     * 
     */
    return new SimpleAuthenticationInfo(body, jwt, getName());
  }

  /**
   * 授权(验证权限时调用),交给casbin
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    System.out.println("doGetAuthorizationInfo");
    return null;
  }

}
