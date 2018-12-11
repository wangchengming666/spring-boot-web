package com.template.serviceimpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.template.mapper.UserInfoMapper;
import com.template.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService{
	
	@Resource
	private UserInfoMapper userInfoMapper;

	@Override
	public String getPassword(String username) {
		
		return userInfoMapper.getPassword(username);
	}

	@Override
	public String getRole(String username) {
		
		return userInfoMapper.getRole(username);
	}

}
