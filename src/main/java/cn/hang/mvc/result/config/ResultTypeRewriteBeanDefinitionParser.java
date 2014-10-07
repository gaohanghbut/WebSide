package cn.hang.mvc.result.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.Assert;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import cn.hang.mvc.config.AbstractSimpleSingletonBeanDefinitionParser;
import cn.hang.mvc.result.ResultTypeMapHolder;
import cn.hang.mvc.result.impl.DefaultResultTypeMapHolder;
import cn.hang.mvc.service.ResultTypeRewriteService;

/**
 * 
 * @author Hang
 *
 */
public class ResultTypeRewriteBeanDefinitionParser extends AbstractSimpleSingletonBeanDefinitionParser {

	/**
	 * target属性名，在此处的意义为target指向的bean
	 */
	private static final String TARGET = "target";
	
	/**
	 * source属性名，在此处的意义为重写后的结果类型
	 */
	private static final String SOURCE = "source";
	
	/**
	 * 视图文件的扩展名
	 */
	private static final String SUFIX = "sufix";
	
	/**
	 * 存储返回类型与返回类型处理器映射的bean
	 */
	private static final String RESULT_TYPE_MAPPER_BEAN_CLASS_NAME = "cn.hang.mvc.result.impl.DefaultResultTypeMapHolder";
	
	/**
	 * RESULT_TYPE_MAPPER_BEAN_CLASS_NAME中存储映射的属性
	 */
	private static final String MAPPING_PROPERTY_NAME = "resultTypeMap";
	
	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		GenericBeanDefinition def = super.getDefaultBeanDefinition(element);
		def.setBeanClassName(RESULT_TYPE_MAPPER_BEAN_CLASS_NAME);
		def.setBeanClass(DefaultResultTypeMapHolder.class);
		
		MutablePropertyValues propertyValues = getMappingPropertyValue(element);
		def.setPropertyValues(propertyValues);
		
		parserContext.registerBeanComponent(new BeanComponentDefinition(def, ResultTypeRewriteService.RESULT_TYPE_REWRITE_BEAN_NAME));
		return null;
	}

	/**
	 * 获取返回类型到返回类型处理器的映射
	 * 
	 * @param element
	 * @return
	 */
	private MutablePropertyValues getMappingPropertyValue(Element element) {
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		PropertyValue propertyValue = new PropertyValue(MAPPING_PROPERTY_NAME, getMappings(element));
		propertyValues.addPropertyValue(propertyValue);
		return propertyValues;
	}
	
	/**
	 * 拿到重写后的返回类型与返回结果处理器的bean name的映射
	 * 
	 * @param element
	 * @return
	 */
	private Map<String, ResultTypeMapHolder.SufixMapper> getMappings(Element element) {
		//获取映射配置
		List<Element> subelems = DomUtils.getChildElements(element);
		Map<String, ResultTypeMapHolder.SufixMapper> map = new HashMap<String, ResultTypeMapHolder.SufixMapper>();
		for (Element elem : subelems) {
			String source = elem.getAttribute(SOURCE);
			Assert.notNull(source);
			String target = elem.getAttribute(TARGET);
			Assert.notNull(target);
			String sufix = elem.getAttribute(SUFIX);
			Assert.notNull(sufix);
			map.put(source, new ResultTypeMapHolder.SufixMapper(target, sufix));
		}
		return map;
	}

}
