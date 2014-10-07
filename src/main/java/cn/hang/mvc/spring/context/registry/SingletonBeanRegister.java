package cn.hang.mvc.spring.context.registry;

import org.springframework.beans.factory.config.SingletonBeanRegistry;

/**
 * ����ע������������ApplicationContext����BeanFactory����ӻ���ɾ��bean��Ϣ
 * 
 * @author GaoHang
 * 
 */
public interface SingletonBeanRegister {

	/**
	 * �����������bean����ɾ��bean
	 * 
	 * @param registry
	 */
	void register(SingletonBeanRegistry registry);
}
