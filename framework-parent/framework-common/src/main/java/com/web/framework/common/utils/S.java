package com.web.framework.common.utils;

/**
 * 全局的状态码
 * 
 * @author cm_wang
 *
 */
public enum S {

  /**
   * 失败
   */
  S_FAIL(0, "失败"),
  /**
   * 成功
   */
  S_SUCCESS(1, "成功"),

  // 以下为以后扩展
  /**
   * 操作对象为App
   */
  S_1002(1002, "不存在的App或者appSecret不正确"),

  /**
   * 操作对象为用户
   */
  S_3001(3001, "参数不正确"), S_3002(3002, "用户不存在或者密码错误"), S_3003(3003, "用户登录信息不全"), S_3004(3004,
      "用户注册信息不能全为空"), S_3005(3005, "身份证或账号已经注册过"), S_3006(3006,
          "手机号码已经注册过"), S_3007(3007, "邮箱已经注册过"), S_3008(3008, "手机号码格式不正确"), S_3009(3009, "邮箱格式不正确"),
  /**
   * token相关
   */
  S_400(400, "token不存在"),
  /**
   * 无权访问!
   */
  S_401(401, "无权访问!"),
  /**
   * token过期
   */
  S_402(402, "token过期"),
  /**
   * token不正确
   */
  S_403(403, "token不正确"),
  /**
   * 新token
   */
  S_405(405, "新token，需要保存");

  // 成员变量
  private int code;
  private String value;

  private S(int code, String value) {
    this.code = code;
    this.value = value;
  }

  public int getCode() {
    return code;
  }

  public String getValue() {
    return value;
  }

  public static S to(int code) {
    for (S s : S.values()) {
      if (code == s.getCode()) {
        return s;
      }
    }
    return null;
  }
}
