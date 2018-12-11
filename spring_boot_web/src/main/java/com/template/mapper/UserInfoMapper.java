package com.template.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {

	String getPassword(String username);
	
	String getRole(String username);
}
