package com.rabbitmq.commons;

import lombok.Data;

@Data
public class R {

	private String msg;

	private Integer code;

	private Object data;

	public static R ok() {
		R r = new R();
		r.setCode(200);
		r.setMsg("OK");
		return r;
	}

	public static R ok(Object object) {
		R r = new R();
		r.setCode(200);
		r.setMsg("OK");
		r.setData(object);
		return r;
	}

	public static R error(Object object) {
		R r = new R();
		r.setCode(309);
		r.setMsg("error");
		r.setData(object);
		return r;
	}
}
