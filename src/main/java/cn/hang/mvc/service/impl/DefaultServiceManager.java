package cn.hang.mvc.service.impl;

import cn.hang.mvc.service.Service;
import cn.hang.mvc.service.ServiceManager;
import cn.hang.mvc.spring.context.ServiceApplicationContext;

/**
 * �����������Ĭ��ʵ�֣���ʵ���г���һ������Service��ApplicationContext
 * 
 * @author GaoHang
 * 
 */
public class DefaultServiceManager implements ServiceManager {

	/**
	 * ����װ������
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
		//��������Service�ϵ�service������Service�����ö�Ӧ���ǵ��������Բ������зǵ����ķ������
		String[] serviceNames = serviceApplicationContext.getBeanNamesForType(Service.class, false, true);
		Service service;
		for (String serviceName : serviceNames) {
			service = serviceApplicationContext.getBean(serviceName, Service.class);
			service.service();
		}
	}

}
