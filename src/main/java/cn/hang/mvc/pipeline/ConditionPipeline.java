package cn.hang.mvc.pipeline;

import java.util.List;

/**
 * �����жϹܵ�
 * 
 * @author GaoHang
 * 
 */
public interface ConditionPipeline extends Pipeline {
	
	/**
	 * �����У���׺�ķָ���
	 */
	public static final String SUFIX_DILIMETER = " ";

	/**
	 * ���ùܵ�ִ�е���������������URI�ĺ�׺�����ڲ�����
	 * 
	 * @param urisufix
	 *            ���������ĺ�׺����
	 */
	void setUrisufixs(List<String> urisufix);

	/**
	 * �ж�ָ����uri��׺�ܷ��ùܵ�ִ��
	 * 
	 * @param urisufix
	 * @return
	 */
	boolean accept(String urisufix);
}
