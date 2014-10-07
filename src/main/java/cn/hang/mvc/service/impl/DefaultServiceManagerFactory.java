package cn.hang.mvc.service.impl;

import cn.hang.mvc.service.ServiceManager;
import cn.hang.mvc.service.ServiceManagerFactory;

/**
 * 默认服务管理器对象的工厂实现
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
