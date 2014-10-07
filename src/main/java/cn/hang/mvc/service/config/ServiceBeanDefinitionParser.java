package cn.hang.mvc.service.config;

import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.w3c.dom.Element;

import cn.hang.mvc.exception.InitializeException;

/**
 * 服务标签解析类的父类，用于生成默认service bean的配置定义
 * 
 * @author GaoHang
 * 
 */
public abstract class ServiceBeanDefinitionParser implements BeanDefinitionParser {

	/**
	 * 服务对应的类名
	 */
	static final String CLASS = "class";

	public ServiceBeanDefinitionParser() {
		super();
	}

	/**
	 * 创建默认配置的bean定义对象，里面用于存储bean的基本信息，不包括属性信息
	 * 
	 * @param element
	 * @return
	 */
	protected GenericBeanDefinition getDefaultBeanDefinition(Element element) {
		GenericBeanDefinition def = new GenericBeanDefinition();
		def.setAbstract(false);
		def.setAutowireCandidate(true);
		def.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
		def.setBeanClassName(element.getAttribute(CLASS));
		def.setScope(GenericBeanDefinition.SCOPE_SINGLETON);
		try {
			def.setBeanClass(Class.forName(def.getBeanClassName()));
		} catch (ClassNotFoundException e) {
			throw new InitializeException(e);
		}
		return def;
	}

}