package com.template.Response;

public class ResponseDataUtil<T> {

	public static <T> ResponseData<T> toFail() {
		return to(false, null, null);
	}

	public static <T> ResponseData<T> toFail(String msg) {
		return to(false, msg, null);
	}

	public static <T> ResponseData<T> toFail(String msg, T data) {
		return to(false, msg, data);
	}

	public static <T> ResponseData<T> toSuccess() {
		return to(true, null, null);
	}

	public static <T> ResponseData<T> toSuccess(String msg) {
		return to(true, msg, null);
	}

	public static <T> ResponseData<T> toSuccess(T data) {
		return to(true, null, data);
	}

	public static <T> ResponseData<T> toSuccess(String msg, T data) {
		return to(true, msg, data);
	}

	public static <T> ResponseData<T> to(boolean isSuccess, String msg, T data) {

		return new ResponseData<T>(isSuccess, msg, data);
	}
}
