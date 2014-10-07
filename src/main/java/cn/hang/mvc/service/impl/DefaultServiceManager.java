package cn.hang.mvc.service.impl;

import cn.hang.mvc.service.Service;
import cn.hang.mvc.service.ServiceManager;
import cn.hang.mvc.spring.context.ServiceApplicationContext;

/**
 * 服务管理器的默认实现，此实现中持有一个管理Service的ApplicationContext
 * 
 * @author GaoHang
 * 
 */
public class DefaultServiceManager implements ServiceManager {

	/**
	 * 服务装载容器
	 */
	private ServiceApplicationContext serviceApplicationContext;

	public DefaultServiceManager() {
		serviceApplicationContext = new ServiceApplicationContext();
	}

	@Override
	public Service getService(String serviceName) {
		return serviceApplicationContext.getBean(serviceName, Service.class);
	}

	@Override
	public void service() {
		//调用所有Service上的service方法，Service的配置都应该是单例，所以不允许有非单例的服务对象
		String[] serviceNames = serviceApplicationContext.getBeanNamesForType(Service.class, false, true);
		Service service;
		for (String serviceName : serviceNames) {
			service = serviceApplicationContext.getBean(serviceName, Service.class);
			service.service();
		}
	}

}
