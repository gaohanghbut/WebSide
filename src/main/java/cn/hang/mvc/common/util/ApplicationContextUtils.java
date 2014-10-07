package cn.hang.mvc.common.util;

import org.springframework.context.ApplicationContext;

import cn.hang.mvc.spring.holder.ApplicationContextHolder;
import cn.hang.mvc.spring.holder.ApplicationContextHolders;
import cn.hang.mvc.spring.holder.impl.ConcurrentApplicationContextHolder;

/**
 * ��ApplicationContext��صĹ�����
 * 
 * @author GaoHang
 * 
 */
public abstract class ApplicationContextUtils {

	private ApplicationContextUtils() {
	}
	/**
	 * ��ȡ����ʹ�õ�ApplicationContextHolder�����û���򴴽�Ĭ�϶���
	 * 
	 * @return
	 */
	public static ApplicationContextHolder getApplicationContextHolder() {
		ApplicationContextHolder applicationContextHolder = ApplicationContextHolders.getApplicationContextHolder();
		if (applicationContextHolder == null) {
			applicationContextHolder = new ConcurrentApplicationContextHolder();
			ApplicationContextHolders.setApplicationContextHolder(applicationContextHolder);
		}
		return applicationContextHolder;
	}

	/**
	 * ������ע�������л�ȡ����
	 * 
	 * @param applicationContextName
	 *            ��������������ģ��������ApplicationContextHolder.ROOT_APPLICATION_CONTEXT
	 * @param beanName
	 *            ���õ�bean������
	 * @return ��Ҫ��ȡ�Ķ���
	 */
	public static Object getBean(String applicationContextName, String beanName) {
		if (applicationContextName.endsWith("/")) {//����Ĳ���Action
			return null;
		}
		ApplicationContextHolder applicationContextHolder = getApplicationContextHolder();
		ApplicationContext applicationContext = applicationContextHolder.get(applicationContextName);
		if (applicationContext == null) {
			applicationContext = applicationContextHolder.getRootApplicationContext();
		}
		if (beanName == null) {
			return null;
		}
		try {
			return applicationContext.getBean(beanName);
		} catch (Exception e) {
			return null;
		}
	}

}
