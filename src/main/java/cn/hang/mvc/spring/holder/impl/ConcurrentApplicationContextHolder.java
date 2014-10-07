package cn.hang.mvc.spring.holder.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationContext;


/**
 * �̰߳�ȫ�����ڴ洢ApplicationContext����getApplicationContextMap��������ConcurrentHashMap
 * 
 * @author GaoHang
 * 
 */ 
public class ConcurrentApplicationContextHolder extends AbstractApplicationContextHolder {
	
	/**
	 * ʹ�����������洢ApplicationContext
	 */
	private Map<String, ApplicationContext> applicationContextMap = new ConcurrentHashMap<String, ApplicationContext>();

	@Override
	protected Map<String, ApplicationContext> getApplicationContextMap() {
		return applicationContextMap;
	}
}
