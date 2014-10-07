package cn.hang.mvc.result.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import cn.hang.mvc.config.AbstractSimpleSingletonBeanDefinitionParser;

/**
 * 返回类型跳转处理器配置信息解析器
 * 
 * @author Hang
 *
 */
public class ResultHandlerBeanDefinitionParser extends AbstractSimpleSingletonBeanDefinitionParser {

	/**
	 * target属性名，用于标记重写后的返回类型
	 */
	private static final String TARGET = "target";
	
	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		GenericBeanDefinition def = super.getDefaultBeanDefinition(element);
		parserContext.registerBeanComponent(new BeanComponentDefinition(def, element.getAttribute(TARGET)));
		return null;
	}

}
