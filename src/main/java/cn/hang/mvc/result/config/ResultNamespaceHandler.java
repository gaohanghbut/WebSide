package cn.hang.mvc.result.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * 返回类型映射命名空间解析器
 * 
 * @author Hang
 *
 */
public class ResultNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("result-type-rewrite", new ResultTypeRewriteBeanDefinitionParser());
		registerBeanDefinitionParser("result-handler", new ResultHandlerBeanDefinitionParser());
	}

}
