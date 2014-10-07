package cn.hang.mvc.service.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import cn.hang.mvc.config.AbstractSimpleSingletonBeanDefinitionParser;
import cn.hang.mvc.service.ServiceManager;

/**
 * 返回结果类型重服务标签写处理程序
 * 
 * @author GaoHang
 *
 */
public class RunDataServiceBeanDefinitionParser extends AbstractSimpleSingletonBeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		GenericBeanDefinition def = getDefaultBeanDefinition(element);
		parserContext.registerBeanComponent(new BeanComponentDefinition(def, ServiceManager.RUN_DATA_SERVICE));
		return null;
	}

}
