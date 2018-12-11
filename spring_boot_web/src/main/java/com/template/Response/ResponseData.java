package com.template.Response;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * 通用 返回值
 *
 * @param <T>
 */
public class ResponseData<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 操作是否成功
	 */
	private boolean isSuccess;
	/**
	 * 消息
	 */
	private String msg;
	/**
	 * 具体的内容
	 */
	private T data;

	public ResponseData(boolean isSuccess, String msg, T data) {
		super();
		this.isSuccess = isSuccess;
		this.msg = msg;
		this.data = data;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
