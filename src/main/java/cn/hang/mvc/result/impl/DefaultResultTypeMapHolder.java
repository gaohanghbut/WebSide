/**
 * 
 */
package cn.hang.mvc.result.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.hang.mvc.result.ResultTypeMapHolder;

/**
 * @author Hang
 *
 */
public class DefaultResultTypeMapHolder implements ResultTypeMapHolder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2856522295731793274L;
	
	/**
	 * 真正存储映射关系的map
	 */
	private Map<String, SufixMapper> resultTypeMap = new HashMap<String, SufixMapper>();

	@Override
	public void put(String key, SufixMapper value) {
		resultTypeMap.put(key, value);
	}

	@Override
	public String getFinalResultType(String key) {
		SufixMapper mapper = resultTypeMap.get(key);
		if (mapper == null) {
			return null;
		}
		return resultTypeMap.get(key).getTarget();
	}

	@Override
	public void unmodifyHolder() {
		resultTypeMap = Collections.unmodifiableMap(resultTypeMap);
	}

	@Override
	public Set<Entry<String, SufixMapper>> keySet() {
		return resultTypeMap.entrySet();
	}

	public Map<String, SufixMapper> getResultTypeMap() {
		return resultTypeMap;
	}

	public void setResultTypeMap(Map<String, SufixMapper> resultTypeMap) {
		this.resultTypeMap = resultTypeMap;
	}

	@Override
	public String getFinalResultSufix(String key) {
		SufixMapper mapper = resultTypeMap.get(key);
		if (mapper == null) {
			return null;
		}
		return resultTypeMap.get(key).getSufix();
	}

	@Override
	public void put(String key, String target, String sufix) {
		resultTypeMap.put(key, new SufixMapper(target, sufix));
	}

}
