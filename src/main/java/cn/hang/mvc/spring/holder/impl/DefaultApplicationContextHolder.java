package cn.hang.mvc.spring.holder.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;


/**
 * Ĭ�����ڴ洢ApplicationContext����getApplicationContextMap����ֱ�ӷ���HashMap
 * 
 * @author GaoHang
 * 
 */
public class DefaultApplicationContextHolder extends AbstractApplicationContextHolder {
	
	/**
	 * �洢ApplicationContext��Map
	 */
	private Map<String, ApplicationContext> applicationContextMap = new HashMap<String, ApplicationContext>();

	@Override
	protected Map<String, ApplicationContext> getApplicationContextMap() {
		return applicationContextMap;
	}
}
