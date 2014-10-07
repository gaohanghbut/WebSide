package cn.hang.mvc.result;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * 用于存储返回类型重写映射的数据结构
 * 
 * @author Hang
 * 
 */
public interface ResultTypeMapHolder extends Serializable {

	/**
	 * 添加一个映射
	 * 
	 * @param key
	 * 
	 * @param value
	 */
	void put(String key, SufixMapper value);
	
	void put(String key, String target, String sufix);

	/**
	 * 得到一个key对应的value，如果不存在则返回null
	 * 
	 * @param key
	 * @return
	 */
	String getFinalResultType(String key);
	
	/**
	 * 得到一个key对应的value，如果不存在则返回null
	 * 
	 * @param key
	 * @return
	 */
	String getFinalResultSufix(String key);

	/**
	 * 使ResultTypeMapHolder对象中的映射关系变成只读
	 */
	void unmodifyHolder();

	/**
	 * 返回key集合
	 * 
	 * @return
	 */
	Set<Map.Entry<String, SufixMapper>> keySet();

	/**
	 * 存储target到sufix的映射
	 * 
	 * @author Hang
	 *
	 */
	public static class SufixMapper{
		
		private String target;
		
		private String sufix;

		public SufixMapper(String target, String sufix) {
			super();
			this.target = target;
			this.sufix = sufix;
		}

		public String getTarget() {
			return target;
		}

		public String getSufix() {
			return sufix;
		}
		
	}
}
