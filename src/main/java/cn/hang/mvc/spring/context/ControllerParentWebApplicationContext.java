package cn.hang.mvc.spring.context;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * 修改XmlWebApplicationContext中的默认配置文件名称，用于加载根容器
 * 
 * @author GaoHang
 *
 */
public class ControllerParentWebApplicationContext extends XmlWebApplicationContext implements BeanDefinitionRegistry, SingletonBeanRegistry {
	
	/**
	 * 新的默认根配置文件路径
	 */
	public static final String DEFAULT_CONFIG_LOCATION = "/WEB-INF/mvc-root.xml";

	/**
	 * ApplicationContext所使用的BeanFactory实例
	 */
	private DefaultListableBeanFactory beanFactory;
	
	public ControllerParentWebApplicationContext(ServletContext servletContext) {
		super();
		setServletContext(servletContext);
		refresh();
		beanFactory = (DefaultListableBeanFactory) super.getBeanFactory();
	}

	@Override
	protected String[] getDefaultConfigLocations() {
		return new String[] {DEFAULT_CONFIG_LOCATION};
	}

	@Override
	public void registerAlias(String name, String alias) {
		this.beanFactory.registerAlias(name, alias);
	}

	@Override
	public void removeAlias(String alias) {
		this.beanFactory.removeAlias(alias);
	}

	@Override
	public boolean isAlias(String beanName) {
		return this.beanFactory.isAlias(beanName);
	}

	@Override
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionStoreException {
		this.beanFactory.registerBeanDefinition(beanName, beanDefinition);
	}

	@Override
	public void removeBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
		this.beanFactory.removeBeanDefinition(beanName);
	}

	@Override
	public BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
		return this.beanFactory.getBeanDefinition(beanName);
	}

	@Override
	public boolean isBeanNameInUse(String beanName) {
		return this.beanFactory.isBeanNameInUse(beanName);
	}

	@Override
	public void registerSingleton(String beanName, Object singletonObject) throws IllegalStateException {
		beanFactory.registerSingleton(beanName, singletonObject);
	}

	@Override
	public Object getSingleton(String beanName) {
		return beanFactory.getSingleton(beanName);
	}

	@Override
	public boolean containsSingleton(String beanName) {
		return beanFactory.containsSingleton(beanName);
	}

	@Override
	public String[] getSingletonNames() {
		return beanFactory.getSingletonNames();
	}

	@Override
	public int getSingletonCount() {
		return beanFactory.getSingletonCount();
	}

}
