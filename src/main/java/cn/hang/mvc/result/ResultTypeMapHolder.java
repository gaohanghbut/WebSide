package cn.hang.mvc.result;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * ���ڴ洢����������дӳ������ݽṹ
 * 
 * @author Hang
 * 
 */
public interface ResultTypeMapHolder extends Serializable {

	/**
	 * ���һ��ӳ��
	 * 
	 * @param key
	 * 
	 * @param value
	 */
	void put(String key, SufixMapper value);
	
	void put(String key, String target, String sufix);

	/**
	 * �õ�һ��key��Ӧ��value������������򷵻�null
	 * 
	 * @param key
	 * @return
	 */
	String getFinalResultType(String key);
	
	/**
	 * �õ�һ��key��Ӧ��value������������򷵻�null
	 * 
	 * @param key
	 * @return
	 */
	String getFinalResultSufix(String key);

	/**
	 * ʹResultTypeMapHolder�����е�ӳ���ϵ���ֻ��
	 */
	void unmodifyHolder();

	/**
	 * ����key����
	 * 
	 * @return
	 */
	Set<Map.Entry<String, SufixMapper>> keySet();

	/**
	 * �洢target��sufix��ӳ��
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
