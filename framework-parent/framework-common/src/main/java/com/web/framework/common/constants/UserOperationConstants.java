package com.web.framework.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义全局的 用户操作类型
 * 
 * @author cm_wang
 *
 */
public enum UserOperationConstants {
  // 出错
  ERR("ERR", "Exception"),

  // 正常
  U01("U01", "用户注册"), U02("U02", "用户登录"), U03("U03", "用户修改密码"), U04("U04", "用户退出");

  private String code;
  private String value;

  private UserOperationConstants(String code, String value) {
    this.code = code;
    this.value = value;
  }

  public static Map<String, String> toMap() {
    Map<String, String> map = new HashMap<String, String>();
    for (UserOperationConstants item : UserOperationConstants.values()) {
      map.put(item.getCode(), item.getValue());
    }
    return map;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }


}
