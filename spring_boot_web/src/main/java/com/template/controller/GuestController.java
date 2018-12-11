package com.template.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.template.Response.ResponseData;
import com.template.Response.ResponseDataUtil;

/**
 * 游客 控制器
 * 
 * @author cm_wang
 *
 */
@RestController("/guest")
public class GuestController {

	@GetMapping("/guestLogin")
	public ResponseData<String> login() {
		
        return ResponseDataUtil.toSuccess("您是游客，只能随便看看！");
    }
}
