package cn.hang.mvc.common.util;

import org.springframework.context.ApplicationContext;

import cn.hang.mvc.spring.holder.ApplicationContextHolder;
import cn.hang.mvc.spring.holder.ApplicationContextHolders;
import cn.hang.mvc.spring.holder.impl.ConcurrentApplicationContextHolder;

/**
 * 与ApplicationContext相关的工具类
 * 
 * @author GaoHang
 * 
 */
public abstract class ApplicationContextUtils {

	private ApplicationContextUtils() {
	}
	/**
	 * 获取正在使用的ApplicationContextHolder，如果没有则创建默认对象
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
	 * 从依赖注入容器中获取对象
	 * 
	 * @param applicationContextName
	 *            容器名，可以是模块名或者ApplicationContextHolder.ROOT_APPLICATION_CONTEXT
	 * @param beanName
	 *            配置的bean的名称
	 * @return 需要获取的对象
	 */
	public static Object getBean(String applicationContextName, String beanName) {
		if (applicationContextName.endsWith("/")) {//请求的不是Action
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
