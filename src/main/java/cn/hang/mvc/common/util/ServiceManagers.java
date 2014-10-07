package cn.hang.mvc.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.util.Assert;

import cn.hang.mvc.service.ServiceManager;
import cn.hang.mvc.service.ServiceManagerFactory;

/**
 * 用于获取ServiceManager对象的工厂
 * 
 * @author Hang
 *
 */
public abstract class ServiceManagers {
	
	/**
	 * 大小为1的List，用于存储ServiceManagerFactory
	 */
	private static List<ServiceManagerFactory> SERVICE_MANAGER_FACTORYS = new ArrayList<ServiceManagerFactory>(1);

	/**
	 * 获取ServiceManager实例
	 * 
	 * @return
	 */
	public static ServiceManager getServiceManager() {
		if (SERVICE_MANAGER_FACTORYS.isEmpty()) {
			return null;
		}
		return SERVICE_MANAGER_FACTORYS.get(0).getServiceManager();
	}
	
	/**
	 * 注册serviceManagerFactory，一旦注册后，此serviceManagerFactory对象将在整个应用中存在，且不能再注册其它对象
	 * @param serviceManagerFactory
	 */
	public static void registerServiceManagerFactory(ServiceManagerFactory serviceManagerFactory) {
		if (SERVICE_MANAGER_FACTORYS.isEmpty()) {
			synchronized (SERVICE_MANAGER_FACTORYS) {
				if (SERVICE_MANAGER_FACTORYS.isEmpty()) {
					Assert.notNull(serviceManagerFactory);
					SERVICE_MANAGER_FACTORYS.add(serviceManagerFactory);
					SERVICE_MANAGER_FACTORYS = Collections.unmodifiableList(SERVICE_MANAGER_FACTORYS);
				}
			}
		}
	}
}
