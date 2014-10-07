package cn.hang.mvc.service.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import cn.hang.mvc.common.util.InvocationUtils;
import cn.hang.mvc.config.AbstractSimpleSingletonBeanDefinitionParser;
import cn.hang.mvc.exception.InitializeException;
import cn.hang.mvc.service.PipelineService;
import cn.hang.mvc.service.ServiceManager;

/**
 * pipeline-service½âÎöÆ÷
 * 
 * @author GaoHang
 *
 */
public class PipelineServiceBeanDefinitionParser extends AbstractSimpleSingletonBeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		if (!parserContext.getRegistry().containsBeanDefinition(ServiceManager.PIPELINE_SERVICE)) {
			String className = element.getAttribute(CLASS);
			try {
				Class<?> c = Class.forName(className);
				if (InvocationUtils.isImplementationOf(c, PipelineService.class)) {
					GenericBeanDefinition def = getDefaultBeanDefinition(element);
					parserContext.registerBeanComponent(new BeanComponentDefinition(def, ServiceManager.PIPELINE_SERVICE));
				} else {
					throw new InitializeException("class is not a pipeline service:" + c.getName());
				}
			} catch (ClassNotFoundException e) {
				throw new InitializeException(e);
			}
		}
		return null;
	}

}
