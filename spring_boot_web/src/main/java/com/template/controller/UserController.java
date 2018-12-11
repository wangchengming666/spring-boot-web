package com.template.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.template.Response.ResponseData;
import com.template.Response.ResponseDataUtil;

/**
 * 普通用户 控制器
 * 
 * @author cm_wang
 *
 */
@RestController("/user")
public class UserController {

	@GetMapping("/userLogin")
	public ResponseData<String> login() {
		
        return ResponseDataUtil.toSuccess("您是普通用户，拥有用户权限！");
    }
}
