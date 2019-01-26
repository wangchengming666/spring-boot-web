/*
 * Copyright 2017-2018 the original author(https://github.com/wj596)
 * 
 * <p> Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License. </p>
 */
package com.web.framework.common.realm;

import java.util.Set;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import com.web.framework.common.matcher.PasswordMatcher;

/**
 * 基于用户、名密码的控制域
 * 
 * @author cm_wang
 */
public class PasswordRealm extends AuthorizingRealm {


  public PasswordRealm() {
    super();
    setCredentialsMatcher(new PasswordMatcher());
  }

  public Class<?> getAuthenticationTokenClass() {
    return UsernamePasswordToken.class;
  }

  /**
   * 认证
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
      throws AuthenticationException {

    if (null == token.getPrincipal() || null == token.getCredentials()) {
      throw new AuthenticationException("AccountPasswordEmpty");
    }
    String account = (String) token.getPrincipal();
    return new SimpleAuthenticationInfo(account, null, getName());

  }

  /**
   * 授权
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    String account = (String) principals.getPrimaryPrincipal();
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    Set<String> roles = null;
    Set<String> permissions = null;
    if (null != roles && !roles.isEmpty())
      info.setRoles(roles);
    if (null != permissions && !permissions.isEmpty())
      info.setStringPermissions(permissions);
    return info;
  }


}
