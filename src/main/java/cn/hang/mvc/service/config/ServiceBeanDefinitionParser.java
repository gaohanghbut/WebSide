package cn.hang.mvc.service.config;

import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.w3c.dom.Element;

import cn.hang.mvc.exception.InitializeException;

/**
 * �����ǩ������ĸ��࣬��������Ĭ��service bean�����ö���
 * 
 * @author GaoHang
 * 
 */
public abstract class ServiceBeanDefinitionParser implements BeanDefinitionParser {

	/**
	 * �����Ӧ������
	 */
	static final String CLASS = "class";

	public ServiceBeanDefinitionParser() {
		super();
	}

	/**
	 * ����Ĭ�����õ�bean��������������ڴ洢bean�Ļ�����Ϣ��������������Ϣ
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