package cn.hang.mvc.service.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * http://www.hbut.edu.cn/schema/service/mvc-service命名空间处理器
 * 
 * @author GaoHang
 * 
 */
public class MvcServiceNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("module-load-service", new ModuleLoadServiceBeanDefinitionParser());
		registerBeanDefinitionParser("result-type-rewrite-service", new ResultTypeRewriteServiceBeanDefinitionParser());
		registerBeanDefinitionParser("run-data-service", new RunDataServiceBeanDefinitionParser());
		registerBeanDefinitionParser("pipeline-service", new PipelineServiceBeanDefinitionParser());
		registerBeanDefinitionParser("general-service", new GeneralServiceBeanDefinitionParser());
		registerBeanDefinitionParser("type-convert-service", new TypeConvertServiceBeanDefinitionParser());
	}

}
