package com.web.framework.common.constants;

import java.io.File;

public class FrameworkConstants {
  /**
   * 项目使用的文件根目录
   */
  public static File ROOT_DIR;

  /**
   * 外部配置文件位置
   */
  public static File FRAMEWORK_PROPERTIES;
  /**
   * 项目自身编码
   */
  public static final String ENCODING_NAME = "UTF-8";

  public static final String APP_CODE = "AUTH_CORE";

  public static final String USER_PREFIX = "u_";

  public static final String ROLE_PREFIX = "r_";

  public static final String APP_PREFIX = "a_";

  public static final String MESSAGE_HASTEXT = "this string must be has text";

  public static final String MESSAGE_ISTRUE = "this expression must be true";

  /**
   * 数据库字段合集分割符
   */
  public static final String CHAR_DELIMITER = ",";


}
