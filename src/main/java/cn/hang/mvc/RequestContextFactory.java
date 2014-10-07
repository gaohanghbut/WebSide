package cn.hang.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ���������ļ�����
 * 
 * @author Hang
 * 
 */
public interface RequestContextFactory {

	/**
	 * �������󣬷��ش���һ��HTTP��������������Ķ���
	 * 
	 * @return RequestContext��ĳ��ʵ����Ķ���
	 */
	RequestContext getGenericRequestContext(HttpServletRequest req, HttpServletResponse resp, RequestContext parentRequestContext);
	
	/**
	 * �������󣬷��ش���һ��HTTP��������������Ķ���
	 * 
	 * @return RequestContext��ĳ��ʵ����Ķ���
	 */
	RequestContext getGenericRequestContext(RequestContext parentRequestContext);

	/**
	 * ���ش���һ��ģ������������Ķ���
	 * 
	 * @param applicationRequestContext
	 *            ����Ӧ�õ����������ļ�����
	 * @return
	 */
	RequestContext getModuleRequestContext(RequestContext applicationRequestContext, String moduleName);
	
	/**
	 * ���ش���һ��ģ������������Ķ���
	 * 
	 * @param applicationRequestContext
	 *            ����Ӧ�õ����������ļ�����
	 * @return
	 */
	RequestContext getGenericRequestContextWithNoneHttp(RequestContext applicationRequestContext);
	
	/**
	 * 
	 * @return
	 */
	RequestContext getApplicationRequestContext();
}
