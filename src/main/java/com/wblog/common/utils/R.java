package com.wblog.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author Mark sunlightcs@gmail.com
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public R() {
    }

    public static R ok() {
        return R.ok(200, "交易成功");
    }

    public static R ok(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

	public static R error() {
		return R.error(500, "交易失败");
	}

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }


    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public <T> T get(Object key, Class<T> clazz) {
        Object o = super.get(key);
        T t = objectMapper.convertValue(o, clazz);
        return t;
    }

    public <T> T get(Object key, TypeReference<T> typeReference) {
        Object o = super.get(key);
        T t = objectMapper.convertValue(o, typeReference);
        return t;
    }
}
