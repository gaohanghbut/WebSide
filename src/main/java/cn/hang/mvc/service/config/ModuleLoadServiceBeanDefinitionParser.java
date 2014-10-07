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
 * module-load-service��ǩ��������������beanʹ��byType��������ע�룬������ע�����ǵ���
 * 
 * @author GaoHang
 * 
 */
public class ModuleLoadServiceBeanDefinitionParser extends AbstractSimpleSingletonBeanDefinitionParser {

	/**
	 * <modules>�ӱ�ǩ
	 */
	private static final String MODULES = "modules";

	/**
	 * Ҫװ�ص�ģ������ÿ��ģ����֮���ÿո�ֿ�
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
					//����<modules>��ǩ
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
	 * ����<modules>�ӱ�ǩ
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
				//ֻ��Ҫ��һ��ģ������
				break;
			}
		}
	}

}
