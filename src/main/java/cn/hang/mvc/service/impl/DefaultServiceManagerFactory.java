package cn.hang.mvc.service.impl;

import cn.hang.mvc.service.ServiceManager;
import cn.hang.mvc.service.ServiceManagerFactory;

/**
 * Ĭ�Ϸ������������Ĺ���ʵ��
 * 
 * @author Hang
 *
 */
public class DefaultServiceManagerFactory implements ServiceManagerFactory {

	@Override
	public ServiceManager getServiceManager() {
		return ServiceManager.DEFAULT_SERVICE_MANAGER;
	}

}
