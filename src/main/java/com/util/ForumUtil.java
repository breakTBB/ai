package com.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.model.Image;

public class ForumUtil {
	public static String getJSONString(int code) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		return json.toJSONString();
	}

	public static String getJSONString(int code, String msg) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		return json.toJSONString();
	}

	public static String getJSONString(int code, Map<String, Object> map) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			json.put(entry.getKey(), entry.getValue());
		}
		return json.toJSONString();
	}

	public static String getJSONString(int code, List<com.model.Image> list) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		Iterator<Image> it = list.iterator();
		int i = 0;
		while (it.hasNext()) {
			json.put("msg" + String.valueOf(i++), it.next());
		}
		return json.toJSONString();
	}
}
