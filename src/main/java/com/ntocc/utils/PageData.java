package com.ntocc.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class PageData extends HashMap implements Map {
	
	private static final long serialVersionUID = 1L;

	Map map = null;
	HttpServletRequest request;


	public PageData(){
		HttpSession session = request.getSession();
		map = new HashMap();
	}
	public PageData(Map<String, Object> paramMap){
		
		map = paramMap;
	}
	/**
	 * 获取request中的参数 
	 * @param request
	 * @throws UnsupportedEncodingException
	 */
	public PageData(HttpServletRequest request) throws UnsupportedEncodingException {
		this.request = request;
//		request.setCharacterEncoding("UTF-8");
		Map properties = request.getParameterMap();//获取前端提交的表单数据，并封装伟map
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Entry entry;//key--value 键值对的map
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			if("GET".equals(request.getMethod())) {
				value = new String(value.getBytes("ISO-8859-1"),"UTF-8");
			}
			returnMap.put(name, value);
		}
		map = returnMap;
	}


	@Override
	public Object get(Object key) {
		Object obj = null;
		if (map.get(key) instanceof Object[]) {
			Object[] arr = (Object[]) map.get(key);
			obj = request == null ? arr : (request.getParameter((String) key) == null ? arr : arr[0]);
		} else {
			obj = map.get(key);
		}
		return obj;
	}

	public String getString(Object key) {
		return (String) get(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object put(Object key, Object value) {
		return map.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return map.remove(key);
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public Set entrySet() {
		return map.entrySet();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Set keySet() {
		return map.keySet();
	}

	@SuppressWarnings("unchecked")
	public void putAll(Map t) {
		map.putAll(t);
	}

	public int size() {
		return map.size();
	}

	public Collection values() {
		return map.values();
	}

}
