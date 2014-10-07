package cn.hang.mvc;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * ����������Ϣ���������������Request��Response��
 * 
 * @author GaoHang
 * 
 */
public interface RunData {

	/**
	 * ��ȡ����ֵ
	 * 
	 * @param paramName
	 *            ������
	 * @return
	 */
	String getParameter(String paramName);
	
	/**
	 * ����ָ���ַ�����ȡ����ֵ
	 * 
	 * @param paramName
	 *            ������
	 * @return
	 */
	String getParameter(String paramName, String charset);
	
	/**
	 * ����ָ���ַ�����ȡ����ֵ
	 * 
	 * @param paramName
	 *            ������
	 * @return
	 */
	String getParameter(String paramName, Charset charset);

	/**
	 * ��ȡ����ֵ���縴ѡ��
	 * 
	 * @param paramName
	 *            ������
	 * @return
	 */
	String[] getParameterValues(String paramName);

	/**
	 * ��ȡ�����URI
	 * 
	 * @return
	 */
	String getURI();

	/**
	 * �������ض���
	 * 
	 * @param path
	 *            ��ת��·��
	 */
	void forward(String path);

	/**
	 * �ͻ����ض���
	 * 
	 * @param path
	 *            �ض����·��
	 */
	void redirect(String path);

	/**
	 * ��ȡ���Ͳ���
	 * 
	 * @param param
	 *            ������
	 * @return
	 */
	int getIntParameter(String param);

	/**
	 * ��ȡ�����Ͳ���
	 * 
	 * @param param
	 *            ������
	 * @return
	 */
	boolean getBooleanParameter(String param);

	/**
	 * ��ȡLong�Ͳ���
	 * 
	 * @param param
	 *            ������
	 * @return
	 */
	long getLongParameter(String param);

	/**
	 * ��ȡshort�Ͳ���
	 * 
	 * @param param
	 *            ������
	 * @return ����ֵ
	 */
	short getShortParameter(String param);

	/**
	 * ���������д����Ĳ�������ʵ����Ķ���
	 * 
	 * @param c
	 * @return
	 */
	@Deprecated
	<T> T getEntiry(Class<T> c);

	/**
	 * ��ȡ�����URL
	 * 
	 * @return
	 */
	String getURL();

	/**
	 * �Ƿ��Ѿ�����
	 * 
	 * @return ���򷵻�true�����򷵻�false
	 */
	boolean hasReturned();

	/**
	 * ���ص�ǰ�����ģ����
	 * 
	 * @return
	 */
	String getRequestModuleName();

	/**
	 * ����action�����е�ǰ׺���˴���ǰ׺����Ϊ"action"ǰ���ַ������磺�����actionΪ��helloAction������
	 * ����"hello"�����action������û��"Action"�򷵻ز���ֵ
	 * 
	 * @return
	 */
	String getResource();

	/**
	 * ȡ�������Action��Ӧ��Screen������
	 * 
	 * @return
	 */
	String getScreenName();

	/**
	 * ��ȡ�����Action
	 * 
	 * @return �����Action
	 */
	String getActionName();
	
	/**
	 * ��ȡ�����������ֵ��ӳ��
	 * 
	 * @return �����������ֵ��ӳ��
	 */
	Map<String, String> getParameterMap();
}
