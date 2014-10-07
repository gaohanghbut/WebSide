package cn.hang.mvc.config;

import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.w3c.dom.Element;

import cn.hang.common.util.StringUtils;
import cn.hang.mvc.exception.InitializeException;

/**
 * 服务标签解析类的父类，用于生成默认bean的配置定�?
 * 
 * @author GaoHang
 * 
 */
public abstract class AbstractSimpleSingletonBeanDefinitionParser implements BeanDefinitionParser {

	/**
	 * 服务对应的类�?
	 */
	public static final String CLASS = "class";

	public AbstractSimpleSingletonBeanDefinitionParser() {
		super();
	}

	/**
	 * 创建默认配置的bean定义对象，里面用于存储bean的基本信息，不包括属性信�?
	 * 
	 * @param element
	 * @return
	 */
	protected GenericBeanDefinition getDefaultBeanDefinition(Element element) {
		GenericBeanDefinition def = new GenericBeanDefinition();
		def.setAbstract(false);
		def.setAutowireCandidate(true);
		def.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_NAME);
		def.setBeanClassName(element.getAttribute(CLASS));
		def.setScope(GenericBeanDefinition.SCOPE_SINGLETON);
		if (StringUtils.isNotEmpty(def.getBeanClassName())) {
			try {
				def.setBeanClass(Class.forName(def.getBeanClassName()));
			} catch (ClassNotFoundException e) {
				throw new InitializeException(e);
			}
		}
		return def;
	}

}