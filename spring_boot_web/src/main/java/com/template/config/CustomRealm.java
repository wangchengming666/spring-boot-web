package com.template.config;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.template.service.UserInfoService;

@Component
public class CustomRealm extends AuthorizingRealm {

	@Autowired
	private UserInfoService userInfoService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("————权限认证————");
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 获得该用户角色
		String role = userInfoService.getRole(username);
		Set<String> set = new HashSet<>();
		// 需要将 role 封装到 Set 作为 info.setRoles() 的参数
		set.add(role);
		// 设置该用户拥有的角色
		info.setRoles(set);
		return info;
	}

	/*
	 * 主要是用来进行身份认证
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache
	 * .shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("————身份认证方法————");
		UsernamePasswordToken authenticationToken = null;
		UsernamePasswordToken userToken = authenticationToken;
		// 从数据库获取对应用户名密码的用户
		String password = userInfoService.getPassword(userToken.getUsername());
		if (null == password) {
			throw new AccountException("用户名不正确");
		} else if (!password.equals(new String((char[]) userToken.getCredentials()))) {
			throw new AccountException("密码不正确");
		}
		return new SimpleAuthenticationInfo(userToken.getPrincipal(), password, getName());
	}

}
