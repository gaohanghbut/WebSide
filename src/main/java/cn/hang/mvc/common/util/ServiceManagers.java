package cn.hang.mvc.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.util.Assert;

import cn.hang.mvc.service.ServiceManager;
import cn.hang.mvc.service.ServiceManagerFactory;

/**
 * ���ڻ�ȡServiceManager����Ĺ���
 * 
 * @author Hang
 *
 */
public abstract class ServiceManagers {
	
	/**
	 * ��СΪ1��List�����ڴ洢ServiceManagerFactory
	 */
	private static List<ServiceManagerFactory> SERVICE_MANAGER_FACTORYS = new ArrayList<ServiceManagerFactory>(1);

	/**
	 * ��ȡServiceManagerʵ��
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
	 * ע��serviceManagerFactory��һ��ע��󣬴�serviceManagerFactory����������Ӧ���д��ڣ��Ҳ�����ע����������
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
