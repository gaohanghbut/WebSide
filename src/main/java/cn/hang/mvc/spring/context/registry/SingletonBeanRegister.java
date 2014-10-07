package cn.hang.mvc.spring.context.registry;

import org.springframework.beans.factory.config.SingletonBeanRegistry;

/**
 * 依赖注入器，用于向ApplicationContext或者BeanFactory中添加或者删除bean信息
 * 
 * @author GaoHang
 * 
 */
public interface SingletonBeanRegister {

	/**
	 * 向容器中清册bean或者删除bean
	 * 
	 * @param registry
	 */
	void register(SingletonBeanRegistry registry);
}
