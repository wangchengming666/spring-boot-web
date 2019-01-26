package com.web.framework.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * 校对工具类
 * 
 * @author cm_wang
 */
public class VerifyUtil {
  /**
   * UNVALID Pattern
   */
  public static final Pattern UNVALID_CHARS =
      Pattern.compile("[ 　`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}‘；：”“’。，、？]|\n|\r|\t");

  public static final Pattern CELLPHONE = Pattern.compile("\\d{11}");

  /**
   * 无效的字符
   * 
   * @param value
   * @return 如果无效，返回 true
   */
  public static boolean notValid(String value) {
    Matcher m = UNVALID_CHARS.matcher(value);
    if (null == value || 0 == value.length() || m.matches()) {
      return true;
    }
    return false;
  }

  /**
   * 核对手机号
   * 
   * @param value
   * @return
   */
  public static boolean cellphone(String value) {
    Matcher m = CELLPHONE.matcher(value);
    if (null == value || 0 == value.length() || m.matches()) {
      return true;
    }
    return false;
  }

}
