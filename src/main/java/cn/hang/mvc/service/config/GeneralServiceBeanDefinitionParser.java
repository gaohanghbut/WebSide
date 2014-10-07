package cn.hang.mvc.service.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import cn.hang.mvc.common.util.StringUtils;
import cn.hang.mvc.config.AbstractSimpleSingletonBeanDefinitionParser;
import cn.hang.mvc.exception.InitializeException;

/**
 * 自定义Service处理程序
 * 
 * @author GaoHang
 *
 */
public class GeneralServiceBeanDefinitionParser extends AbstractSimpleSingletonBeanDefinitionParser {

	/**
	 * name属性
	 */
	private static final String NAME = "name";
	
	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		GenericBeanDefinition def = getDefaultBeanDefinition(element);
		String name = element.getAttribute(NAME);
		if (StringUtils.isEmpty(name)) {
			throw new InitializeException("Service name cannot be null " + element.getLocalName());
		}
		parserContext.registerBeanComponent(new BeanComponentDefinition(def, name));
		return null;
	}

}
