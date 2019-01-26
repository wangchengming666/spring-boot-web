package com.web.framework.common.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
  private static final String VALID_CHARS = "[^\\s\\(\\)<>@,;:\\\\\\\"\\.\\[\\]+]+";
  /**
   * Email Pattern
   */
  public static final Pattern EMAIL_PATTERN = Pattern.compile("(" + VALID_CHARS + "(\\."
      + VALID_CHARS + ")*@" + VALID_CHARS + "(\\." + VALID_CHARS + ")*)");

  /**
   * 核对是否是邮箱
   * 
   * @param value
   * @return
   */
  public static boolean verifyEmail(String value) {
    Matcher m = EMAIL_PATTERN.matcher(value);
    if (!m.matches()) {
      return true;
    }
    return false;
  }
}
