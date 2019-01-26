package com.web.framework.core.controller.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.web.framework.common.utils.R;
import com.web.framework.core.base.ControllerBase;
import com.web.framework.core.common.TokenUtil;

@Controller
public class ViewController extends ControllerBase {

	@RequestMapping(value = { "**/*.html" }, method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request) {
		return StringUtils.substringBefore(request.getRequestURI(), ".html");
	}

}
