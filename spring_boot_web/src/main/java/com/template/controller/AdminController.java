package com.template.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.template.Response.ResponseData;
import com.template.Response.ResponseDataUtil;

/**
 * 管理员 控制器
 * 
 * @author cm_wang
 *
 */
@RestController("/admin")
public class AdminController {

	@GetMapping("/adminLogin")
	public ResponseData<String> login() {
		
		return ResponseDataUtil.toSuccess("您拥有管理员权限，您的权限最大！");
	}
}
