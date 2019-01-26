package com.web.framework.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.util.WebUtils;

/**
 * 1 secure属性 <br/>
 * 当设置为true时，表示创建的 Cookie 会被以安全的形式向服务器传输，也就是只能在 HTTPS 连接中被浏览器传递到服务器端进行会话验证，如果是
 * HTTP 连接则不会传递该信息，所以不会被窃取到Cookie 的具体内容。
 * 
 * 2 HttpOnly属性<br/>
 * 如果在Cookie中设置了"HttpOnly"属性，那么通过程序(JS脚本、Applet等)将无法读取到Cookie信息，这样能有效的防止XSS攻击。
 * 
 * @author cm_wang
 *
 */
public class CookieUtil {
	public static void create(HttpServletResponse httpServletResponse, String name, String value) {
		create(httpServletResponse, name, value, null, null, null);
	}

	public static void create(HttpServletResponse httpServletResponse, String name, String value, Boolean secure,
			Integer maxAge) {
		create(httpServletResponse, name, value, secure, maxAge, null);
	}

	public static void create(HttpServletResponse httpServletResponse, String name, String value, Boolean secure,
			Integer maxAge, String domain) {
		Cookie cookie = new Cookie(name, value);
		if (secure != null) {
			cookie.setSecure(secure);
		}
		cookie.setHttpOnly(true);
		if (maxAge != null) {
			cookie.setMaxAge(maxAge);
		}
		if (domain != null) {
			cookie.setDomain(domain);
		}
		cookie.setPath("/");
		httpServletResponse.addCookie(cookie);
	}

	public static void clear(HttpServletResponse httpServletResponse, String name) {
		Cookie cookie = new Cookie(name, null);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(0);
		httpServletResponse.addCookie(cookie);
	}

	public static String getValue(HttpServletRequest httpServletRequest, String name) {
		Cookie cookie = WebUtils.getCookie(httpServletRequest, name);
		return cookie != null ? cookie.getValue() : null;
	}
}
