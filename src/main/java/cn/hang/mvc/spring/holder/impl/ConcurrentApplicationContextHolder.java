package cn.hang.mvc.spring.holder.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationContext;


/**
 * 线程安全的用于存储ApplicationContext对象，getApplicationContextMap方法返回ConcurrentHashMap
 * 
 * @author GaoHang
 * 
 */ 
public class ConcurrentApplicationContextHolder extends AbstractApplicationContextHolder {
	
	/**
	 * 使用免锁容器存储ApplicationContext
	 */
	private Map<String, ApplicationContext> applicationContextMap = new ConcurrentHashMap<String, ApplicationContext>();

	@Override
	protected Map<String, ApplicationContext> getApplicationContextMap() {
		return applicationContextMap;
	}
}
