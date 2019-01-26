package com.web.framework.common.utils;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;

public class ResponseUtil {
	protected static Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

	/**
	 * 判断是否AJAX请求
	 */
	public static boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
	}

	/**
	 * AJAX成功响应
	 */
	public static void ajaxSucceed(HttpServletResponse response, R.Data rData) {
		responseJson(response, rData);
	}

	/**
	 * AJAX失败响应
	 */
	public static void ajaxFailed(HttpServletResponse response, R.Data rData) {
		responseJson(response, rData);
	}

	/**
	 * 简单返回文本
	 */
	public static void response(HttpServletRequest request, HttpServletResponse response, R.Data rData) {
		// if (isAjax(request)) {
		if (true) {
			responseJson(WebUtils.toHttp(response), rData);
		} else {
			responseText(WebUtils.toHttp(response), rData);
		}
	}

	/**
	 * JSON响应
	 */
	public static void responseJson(HttpServletResponse response, R.Data rData) {
		response.setStatus(HttpServletResponse.SC_OK);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(JSON.toJSONString(rData));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (out != null)
				out.close();
		}
	}

	/**
	 * JSON响应
	 */
	public static void responseText(HttpServletResponse response, R.Data rData) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(rData.getMsg());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (out != null)
				out.close();
		}
	}
}
