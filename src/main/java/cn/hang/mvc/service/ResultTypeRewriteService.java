package cn.hang.mvc.service;

import cn.hang.mvc.result.ResultTypeHandler;
import cn.hang.mvc.result.ResultTypeMapHolder;

/**
 * ���ؽ����д����
 * 
 * @author Hang
 * 
 */
public interface ResultTypeRewriteService extends Service {

	/**
	 * ��Spring�е�bean���õ�name
	 */
	public static final String RESULT_TYPE_REWRITE_BEAN_NAME = "_result_type_rewrite_bean_";

	/**
	 * ���ش洢��дӳ��Ķ���
	 * 
	 * @return
	 */
	ResultTypeMapHolder getResultTypeMapHolder();

	/**
	 * ��ȡָ���������Ͷ�Ӧ�ķ��ؽ��������
	 * 
	 * @param resultType
	 *            ��д��ķ�������
	 * @return
	 */
	ResultTypeHandler getResultTypeHandler(String resultType);

	/**
	 * ��ȡָ��ԭʼ�������Ͷ�Ӧ�ķ��ؽ��������
	 * 
	 * @param originalResultType
	 *            ��дǰ�ķ�������
	 * @return
	 */
	ResultTypeHandler getOriginalResultTypeHandler(String originalResultType);
	
	/**
	 * ͨ��ԭʼ�������ͻ�ȡ��д��ķ�������
	 * 
	 * @param originalResultType ԭʼ��������
	 * 
	 * @return
	 */
	String getFinalResultType(String originalResultType);
	
	/**
	 * ͨ��ԭʼ�������ͻ�ȡ��д��ķ���������ͼ�ļ��ĺ�׺��
	 * 
	 * @param originalResultType ԭʼ��������
	 * 
	 * @return
	 */
	String getFinalResultSufix(String originalResultType);

}
