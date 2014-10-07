package cn.hang.mvc.pipeline.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * http://www.hbut.edu.cn/schema/pineline/mvc-pipeline�����ռ䴦����
 * 
 * @author GaoHang
 * 
 */
public class MvcPipelineNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("pipeline", new PipelineBeanDefinitionParser());
	}

}
