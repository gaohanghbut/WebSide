package cn.hang.mvc.spring.holder.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;


/**
 * 默认用于存储ApplicationContext对象，getApplicationContextMap方法直接返回HashMap
 * 
 * @author GaoHang
 * 
 */
public class DefaultApplicationContextHolder extends AbstractApplicationContextHolder {
	
	/**
	 * 存储ApplicationContext的Map
	 */
	private Map<String, ApplicationContext> applicationContextMap = new HashMap<String, ApplicationContext>();

	@Override
	protected Map<String, ApplicationContext> getApplicationContextMap() {
		return applicationContextMap;
	}
}
