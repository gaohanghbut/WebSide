package cn.hang.mvc.spring.holder;

import org.springframework.context.ApplicationContext;

/**
 * 工具类，用于存放ApplicationContextHolder
 * 
 * @author GaoHang
 * 
 */
public abstract class ApplicationContextHolders {

	private static ApplicationContextHolder applicationContextHolder;

	/**
	 * 设置ApplicationContextHolder
	 * 
	 * @param contextHolder
	 */
	public static void setApplicationContextHolder(ApplicationContextHolder contextHolder) {
		applicationContextHolder = contextHolder;
	}
	
	public static ApplicationContextHolder getApplicationContextHolder() {
		return applicationContextHolder;
	}

	public static ApplicationContext get(String key) {
		return applicationContextHolder.get(key);
	}

	public static void set(String key, ApplicationContext ctx) {
		applicationContextHolder.set(key, ctx);
	}

	public static void setRootApplicationContext(ApplicationContext parent) {
		applicationContextHolder.setRootApplicationContext(parent);
	}

	public static ApplicationContext getRootApplicationContext() {
		return applicationContextHolder.getRootApplicationContext();
	}

	public static void constHolder() {
		applicationContextHolder.constHolder();
	}
	
}
