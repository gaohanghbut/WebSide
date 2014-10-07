package cn.hang.mvc.service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hang.mvc.RequestContext;

/**
 * ���ڹ���ͬscope��RunData��RunData�����֣�һ����Application��Χ�ڵ�RunData����RunData�����ǵ�����
 * �ڶ�����Request��Χ��RunData���˶�����ͬһ���������ǵ�������������Module RunData���˶�����ͬһ��ģ�����ǵ���
 * 
 * @author GaoHang
 * 
 */
public interface RunDataService extends Service {

	/**
	 * ��ȡApplication��Χ�ڵ�RunData
	 * 
	 * @return
	 * @deprecated ��Ϊʹ�� {@link #getApplicationRunData()} 
	 */
//	RunData getApplicationRunData();

	/**
	 * ��ȡApplication��Χ�ڵ�RunData
	 * 
	 * @return
	 */
	RequestContext getApplicationRunData();

	/**
	 * ��ȡRequest��Χ�ڵ�RunData
	 * 
	 * @return
	 * @deprecated ��Ϊʹ�� {@link #getRequestRunData(ServletRequest)} 
	 */
//	RunData getRequestRunData();

	/**
	 * ��ȡRequest��Χ�ڵ�RunData
	 * @param req
	 * 
	 * @return
	 */
	RequestContext getRequestRunData(ServletRequest req);

	/**
	 * ��ȡModule RunData
	 * 
	 * @return
	 * @deprecated ��Ϊʹ�� {@link #getModuleRunData(String)} 
	 */
//	RunData getModuleRunData();

	/**
	 * ��ȡModule RunData
	 * @param module
	 * 
	 * @return
	 */
	RequestContext getModuleRunData(String module);

	RequestContext getRequestRunData(HttpServletRequest req, HttpServletResponse resp);
}
