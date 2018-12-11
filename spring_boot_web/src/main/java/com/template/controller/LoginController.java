package com.template.controller;

import org.apache.catalina.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.template.Response.ResponseData;
import com.template.Response.ResponseDataUtil;
import com.template.service.UserInfoService;

/**
 * 登陆 控制器
 * 
 * @author cm_wang
 *
 */
@RestController()
public class LoginController {

	@Autowired
	private UserInfoService userInfoService;

	@PostMapping("/login")
	public ResponseData<String> login(User user) {
		// 从SecurityUtils里边创建一个 subject
		Subject subject = SecurityUtils.getSubject();
		// 在认证提交前准备 token（令牌）
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
		// 执行认证登陆
		subject.login(token);
		// 根据权限，指定返回数据
		String role = userInfoService.getRole(user.getUsername());
		if ("user".equals(role)) {
			return ResponseDataUtil.toSuccess("欢迎进入用户页面。");
		}
		if ("admin".equals(role)) {
			return ResponseDataUtil.toSuccess("欢迎进入管理员页面。");
		}
		return ResponseDataUtil.toFail("没有相关权限");
	}

	/**
	 * 退出 控制器
	 * 
	 * @return
	 */
	@GetMapping("/logout")
	public ResponseData<String> logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return ResponseDataUtil.toSuccess("成功注销！");
	}

	@GetMapping("/notLogin")
	public ResponseData<String> notLogin() {
		return ResponseDataUtil.toSuccess("您尚未登陆！");
	}

	@GetMapping("/notRole")
	public ResponseData<String> notRole() {
		return ResponseDataUtil.toSuccess("您没有权限！");
	}
}
