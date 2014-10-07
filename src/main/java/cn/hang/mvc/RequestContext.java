package cn.hang.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ���������ģ���Ϊ���࣬Application RequestContext��ʾ��Ӧ�õ���������Ӧ����ֻ��һ����
 * �����ڱ���ĳЩ�������ݣ�Module RequestContext��ʾ��ģ�������һ��ģ��ֻ��һ����Request
 * ��Χ��RequestContext��ʾһ��HTTP�����Ӧ�������ģ�һ�������Ӧһ����
 * 
 * @author Hang
 *
 */
public interface RequestContext extends RunData {

	/**
	 * ����key��ȡvalue�����ڵ�ǰ�����л�ȡvalue�����û�������丸�������в��ң�Ȼ����request,session,application�в��ң�ֱ���ҵ���
	 * 
	 * @param key
	 * @return
	 */
	Object getValue(String key);

	/**
	 * ���ö����ֵ�����õĶ�����session��request,application�����Է�Χ����
	 * 
	 * @param key
	 * @param value
	 */
	void putValue(String key, Object value);
	
	/**
	 * ���ظ�RequestContext��Application RequestContext�ĸ�RequestContextΪ��
	 * Module RequestContext�ĸ�RequestContext��Application RequestContext,
	 * Request��Χ��RequestContext�ĸ�RequestContext��Module RequestContext
	 * 
	 * @return
	 */
	RequestContext getParent();
	
	/**
	 * ����������ɾ������Լ��ֻ��ɾ��Request��Χ���������еĶ���
	 * 
	 * @param key
	 * @return
	 */
	Object removeValue(String key);
	
	/**
	 * �Ƿ����key
	 * 
	 * @param key
	 * @return
	 */
	boolean contains(String key);
	
	/**
	 * ����key������
	 * 
	 * @return
	 */
	String[] keys();

	/**
	 * �ض���
	 */
//	void redirect(String url);
	/**
	 * ���ص�ǰ�����HttpServletRequest���������ǰRequestContext������Module����Application���������ģ��򷵻ؿ�
	 * 
	 * @return
	 */
	HttpServletRequest getHttpServletRequest();
	
	/**
	 * ���ص�ǰ�����HttpServletResponse���������ǰRequestContext������Module����Application���������ģ��򷵻ؿ�
	 * 
	 * @return
	 */
	HttpServletResponse getHttpServletResponse();

}
