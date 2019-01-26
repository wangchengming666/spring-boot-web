package com.web.framework.common.wrapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.web.framework.common.utils.S;

public class RDataWrapper {
	private JSONObject jsonObject;

	public RDataWrapper(String json) {
		super();
		jsonObject = JSON.parseObject(json);
	}

	public RDataWrapper setExtension(Object extension) {
		jsonObject.put("extension", extension);
		return this;
	}

	public RDataWrapper setStatus(S s) {
		jsonObject.put("code", s.getCode());
		jsonObject.put("msg", s.getValue());
		return this;
	}

	@Override
	public String toString() {
		return jsonObject.toJSONString();
	}

}
