package cn.hang.mvc.service.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import cn.hang.mvc.config.AbstractSimpleSingletonBeanDefinitionParser;
import cn.hang.mvc.exception.InitializeException;
import cn.hang.mvc.service.ServiceManager;

/**
 * type-convert-service标签处理器
 * 
 * @author Hang
 *
 */
public class TypeConvertServiceBeanDefinitionParser extends AbstractSimpleSingletonBeanDefinitionParser {
	
	/**
	 * 子标签的类型转换器类的属性
	 */
	private static final String CONVERTER = "converter-class";
	
	/**
	 * 需要转换成的类型的属性
	 */
	private static final String TYPE = "type-class";
	
	/**
	 * bean类中的存储类型与类型转换器的映射的属性名
	 */
	private static final String CONVERT_TYPE_MAP_PROPERTY = "typeConverterMapping";
	
	/**
	 * 
	 */
	private ClassLoader classLoader;
	
	public TypeConvertServiceBeanDefinitionParser() {
		classLoader = java.lang.Thread.currentThread().getContextClassLoader();
	}

	public BeanDefinition parse(Element element, ParserContext parserContext) {
		GenericBeanDefinition def = super.getDefaultBeanDefinition(element);
		
		Map<Class<?>, Class<?>> typeMap = new HashMap<Class<?>, Class<?>>();
		List<Element> converterMappers = DomUtils.getChildElements(element);
		for (Element elem : converterMappers) {
			String key = elem.getAttribute(TYPE);
			String value = elem.getAttribute(CONVERTER);
			try {
				Class<?> type = classLoader.loadClass(key);
				Class<?> converter = classLoader.loadClass(value);
				typeMap.put(type, converter);
			} catch (ClassNotFoundException e) {
				throw new InitializeException(e.getMessage(), e);
			}
		}
		typeMap = Collections.unmodifiableMap(typeMap);
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		PropertyValue propertyValue = new PropertyValue(CONVERT_TYPE_MAP_PROPERTY, typeMap);
		propertyValues.addPropertyValue(propertyValue);
		def.setPropertyValues(propertyValues);
		parserContext.registerBeanComponent(new BeanComponentDefinition(def, ServiceManager.TYPE_CONVERT_SERVICE));
		return null;
	}

}
