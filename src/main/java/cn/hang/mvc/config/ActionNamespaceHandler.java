package cn.hang.mvc.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Action���ý�����
 * 
 * @author GaoHang
 * 
 */
public class ActionNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("action", new ActionBeanDefinitionParser());
	}

}
