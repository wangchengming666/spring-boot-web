package com.template.service;

public interface UserInfoService {

	/**
	 * 获得密码
	 * 
	 * @param username
	 * @return
	 */
	String getPassword(String username);
	
	/**
	 * 获得角色权限
	 * 
	 * @param username
	 * @return
	 */
	String getRole(String username);
}
