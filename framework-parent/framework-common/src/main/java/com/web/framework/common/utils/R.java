package com.web.framework.common.utils;

import java.io.Serializable;

/**
 * 返回数据
 */
public class R implements Serializable {
  private static final long serialVersionUID = 1L;

  public static Data success() {
    return new R.Data(S.S_SUCCESS, null);
  }

  public static Data success(String msg, Object data) {
    return new R.Data(S.S_SUCCESS.getCode(), msg, data);
  }

  public static Data success(Object data) {
    return new R.Data(S.S_SUCCESS, data);
  }

  public static R.Data fail(String msg) {

    return new R.Data(S.S_FAIL, msg);
  }

  public static R.Data fail() {

    return new R.Data(S.S_FAIL, null);
  }

  public static R.Data data(S statusCode) {

    return new R.Data(statusCode);
  }

  public static class Data implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    private Object data;
    private Object extension;

    public Data() {}

    public Data(S statusCode) {
      this.code = statusCode.getCode();
      this.msg = statusCode.getValue();
    }

    public Data(S statusCode, String msg) {
      this.code = statusCode.getCode();
      this.msg = msg;
    }

    public Data(S statusCode, Object data) {
      this.code = statusCode.getCode();
      this.msg = statusCode.getValue();
      this.data = data;
    }

    public Data(int code, String msg, Object data) {
      this.code = code;
      this.msg = msg;
      this.data = data;
    }

    public int getCode() {
      return code;
    }

    public void setCode(int code) {
      this.code = code;
    }

    public String getMsg() {
      return msg;
    }

    public void setMsg(String msg) {
      this.msg = msg;
    }

    public Object getData() {
      return data;
    }

    public void setData(Object data) {
      this.data = data;
    }

    public Object getExtension() {
      return extension;
    }

    public void setExtension(Object extension) {
      this.extension = extension;
    }

  }
}
