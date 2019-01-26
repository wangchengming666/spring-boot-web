package com.web.framework.core.controller.web;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.web.framework.common.constants.FrameworkConstants;
import com.web.framework.common.constants.KeysConstants;
import com.web.framework.common.listener.CreateUserListener;
import com.web.framework.common.utils.JWTUtil;
import com.web.framework.common.utils.R;
import com.web.framework.common.utils.S;
import com.web.framework.core.base.ControllerBase;
import com.web.framework.core.common.TokenUtil;
import com.web.framework.core.entity.UcUser;
import com.web.framework.core.model.vo.UserVO;
import com.web.framework.core.service.UcUserService;

/**
 * 
 * @author cm_wang
 *
 */
@RestController
@RequestMapping("/user")
public class UserController extends ControllerBase {

	@Autowired
	private UcUserService ucUserService;

	@Autowired
	private List<CreateUserListener> listeners;

	@RequestMapping(value = "/who", method = RequestMethod.GET)
	public R.Data who() {

		return R.success("我是" + TokenUtil.getJwtTokenValue("username", String.class));
	}
}
