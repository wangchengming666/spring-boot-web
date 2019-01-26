package com.web.framework.common.constants;

import java.nio.charset.Charset;

/**
 * 密钥相关 keys
 * 
 * @author cm_wang
 *
 */
public class KeysConstants {
	/**
	 * JWT 密钥，默认值,可修改
	 */
	public static String SECRET_KEY = "ThisIsASecret1234asf314asdf2341s3asdf1asdfpasd7as0df70f";
	/**
	 * jwt secret key
	 */
	public static String COMMAND_LINE_SECRET_KEY = "framework.key.secret";
	public static byte[] JWT_KEY_BYTES = SECRET_KEY.getBytes(Charset.forName("UTF-8"));

	/**
	 * 加密Salt，默认值,可修改
	 */
	public static String SALT_KEY = "81a5f9bf7d7744c0933c";
	/**
	 * 加密 salt key
	 */
	public static String COMMAND_LINE_SALT_KEY = "framework.key.salt";

	/**
	 * JSON Web Tokens key
	 */
	public static final String JWT_ID = "JWTID";

}
