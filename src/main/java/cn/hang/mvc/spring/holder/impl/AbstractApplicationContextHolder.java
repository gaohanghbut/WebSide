package cn.hang.mvc.spring.holder.impl;

import java.util.Collections;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import cn.hang.mvc.spring.holder.ApplicationContextHolder;

/**
 * 
 * @author GaoHang
 * 
 */
public abstract class AbstractApplicationContextHolder implements ApplicationContextHolder {

	/**
	 * �������ɸ���ģ���Ӧ��ApplicationContext����
	 */
	private Map<String, ApplicationContext> applicationContexts;

	public AbstractApplicationContextHolder() {
		super();
	}

	@Override
	public ApplicationContext get(String key) {
		insureContextMap();
		return applicationContexts.get(key);
	}

	/**
	 * ȷ��applicationContexts��ʼ��һ��
	 */
	private void insureContextMap() {
		if (applicationContexts == null) {
			synchronized (this) {
				if (applicationContexts == null) {
					applicationContexts = getApplicationContextMap();
				}
			}
		}
	}

	@Override
	public void set(String key, ApplicationContext ctx) {
		insureContextMap();
		applicationContexts.put(key, ctx);
	}

	@Override
	public ApplicationContext getRootApplicationContext() {
		insureContextMap();
		return applicationContexts.get(ROOT_APPLICATION_CONTEXT);
	}

	@Override
	public void setRootApplicationContext(ApplicationContext parent) {
		insureContextMap();
		applicationContexts.put(ROOT_APPLICATION_CONTEXT, parent);
	}

	/**
	 * ����ȡ�ô��ApplicationContext��Map
	 * 
	 * @return
	 */
	protected abstract Map<String, ApplicationContext> getApplicationContextMap();

	@Override
	public void constHolder() {
		applicationContexts = Collections.unmodifiableMap(applicationContexts);
	}
	
}