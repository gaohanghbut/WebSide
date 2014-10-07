package cn.hang.mvc.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import cn.hang.mvc.common.util.FrameworkConstants;
import cn.hang.mvc.common.util.InvocationUtils;
import cn.hang.mvc.common.util.StringUtils;
import cn.hang.mvc.exception.InitializeException;

/**
 * <action>标签解析器，在此解析器中注册Action类和Screen类
 * 
 * @author GaoHang
 * 
 */
public class ActionBeanDefinitionParser extends AbstractSimpleSingletonBeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		//注册Action
		GenericBeanDefinition actionBeanDefinition = getDefaultBeanDefinition(element);
		String actionBeanName = StringUtils.firstCharToLowercase(actionBeanDefinition.getBeanClassName().substring(actionBeanDefinition.getBeanClassName().lastIndexOf('.') + 1));
		parserContext.registerBeanComponent(new BeanComponentDefinition(actionBeanDefinition, actionBeanName));
		
		//注册Screen
		GenericBeanDefinition screenBeanDefinition = getScreenBeanDefinition(actionBeanDefinition);
		if (screenBeanDefinition == null) {
			return null;
		}
		String screenBeanName = StringUtils.replaceLast(actionBeanName, FrameworkConstants.ACTION_SUFIX, FrameworkConstants.SCREEN_SUFIX);
		parserContext.registerBeanComponent(new BeanComponentDefinition(screenBeanDefinition, screenBeanName));
		return null;
	}

	/**
	 * 取得Action对应的Screen的Bean定义
	 * 
	 * @param actionBeanDefinition Action的Bean定义
	 * 
	 * @return
	 */
	protected GenericBeanDefinition getScreenBeanDefinition(GenericBeanDefinition actionBeanDefinition) {
		String screenClassName = StringUtils.replaceLast(actionBeanDefinition.getBeanClass().getName(), FrameworkConstants.MODULE_ACTION_PACKAGE_NAME_SUFIX, FrameworkConstants.MODULE_SCREEN_PACKAGE_NAME_SUFIX);
		screenClassName = StringUtils.replaceLast(screenClassName, FrameworkConstants.ACTION_SUFIX, FrameworkConstants.SCREEN_SUFIX);
		if (! InvocationUtils.classExists(screenClassName)) {
			return null;
		}
		GenericBeanDefinition screenBeanDefinition = new GenericBeanDefinition();
		screenBeanDefinition.setAbstract(false);
		screenBeanDefinition.setAutowireCandidate(true);
		screenBeanDefinition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_NAME);
		screenBeanDefinition.setScope(GenericBeanDefinition.SCOPE_SINGLETON);
		screenBeanDefinition.setBeanClassName(screenClassName);
		if (StringUtils.isNotEmpty(screenBeanDefinition.getBeanClassName())) {
			try {
				screenBeanDefinition.setBeanClass(Class.forName(screenBeanDefinition.getBeanClassName()));
			} catch (ClassNotFoundException e) {
				throw new InitializeException(e);
			}
		}
		return screenBeanDefinition;
	}

}
