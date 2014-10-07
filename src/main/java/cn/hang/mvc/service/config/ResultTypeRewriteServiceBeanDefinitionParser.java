package cn.hang.mvc.service.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import cn.hang.mvc.common.util.InvocationUtils;
import cn.hang.mvc.config.AbstractSimpleSingletonBeanDefinitionParser;
import cn.hang.mvc.exception.InitializeException;
import cn.hang.mvc.service.ResultTypeRewriteService;
import cn.hang.mvc.service.ServiceManager;

/**
 * 返回结果类型重服务标签写处理程序
 * 
 * @author GaoHang
 *
 */
public class ResultTypeRewriteServiceBeanDefinitionParser extends AbstractSimpleSingletonBeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		GenericBeanDefinition def = getDefaultBeanDefinition(element);
		Class<?> c = def.getBeanClass();
		if (!InvocationUtils.isImplementationOf(c, ResultTypeRewriteService.class)) {
			throw new InitializeException("The Service in <mvc-service:result-type-rewrite-service> is not a ResultTypeRewriteService");
		}
		parserContext.registerBeanComponent(new BeanComponentDefinition(def, ServiceManager.RESULT_TYPE_REWRITE_SERVICE));
		return null;
	}

}
