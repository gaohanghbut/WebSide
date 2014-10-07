package cn.hang.mvc.service.config;

import java.util.List;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import cn.hang.mvc.common.util.InvocationUtils;
import cn.hang.mvc.config.AbstractSimpleSingletonBeanDefinitionParser;
import cn.hang.mvc.exception.InitializeException;
import cn.hang.mvc.service.ModuleLoadService;
import cn.hang.mvc.service.ServiceManager;

/**
 * module-load-service标签处理器，处理后的bean使用byType进行依赖注入，在依赖注入中是单例
 * 
 * @author GaoHang
 * 
 */
public class ModuleLoadServiceBeanDefinitionParser extends AbstractSimpleSingletonBeanDefinitionParser {

	/**
	 * <modules>子标签
	 */
	private static final String MODULES = "modules";

	/**
	 * 要装载的模块名，每个模块名之间用空格分开
	 */
	private static final String MODULE_NAMES = "moduleNames";

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		if (!parserContext.getRegistry().containsBeanDefinition(ServiceManager.MODULE_LOAD_SERVICE)) {
			String className = element.getAttribute(CLASS);
			try {
				Class<?> c = Class.forName(className);
				if (InvocationUtils.isImplementationOf(c, ModuleLoadService.class)) {
					GenericBeanDefinition def = getDefaultBeanDefinition(element);
					//处理<modules>标签
					handlerModulesTag(element, def);
					parserContext.registerBeanComponent(new BeanComponentDefinition(def, ServiceManager.MODULE_LOAD_SERVICE));
				} else {
					throw new InitializeException("class is not a module load service:" + c.getName());
				}
			} catch (ClassNotFoundException e) {
				throw new InitializeException(e);
			}
		}
		return null;
	}

	/**
	 * 处理<modules>子标签
	 * 
	 * @param element
	 * @param def
	 */
	private void handlerModulesTag(Element element, GenericBeanDefinition def) {
		List<Element> elets = DomUtils.getChildElements(element);
		for (Element elem : elets) {
			if (elem.getTagName().endsWith(MODULES)) {
				MutablePropertyValues propertyValues = new MutablePropertyValues();
				propertyValues.add(MODULE_NAMES, elem.getAttribute(MODULE_NAMES));
				def.setPropertyValues(propertyValues);
				//只需要有一个模块配置
				break;
			}
		}
	}

}
