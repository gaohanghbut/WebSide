package cn.hang.mvc.spring.context;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;

import cn.hang.mvc.common.util.ServletUtils;

/**
 * 子容器
 * 
 * @author GaoHang
 * 
 */
public class ControllerChildWebApplicationContext extends AbstractRefreshableWebApplicationContext implements BeanDefinitionRegistry {
	
	/**
	 * 新的默认根配置文件路径模板,$s为可替换的字条串
	 */
	public static final String DEFAULT_CONFIG_LOCATION_TEMPLATE = "/WEB-INF/%s/mvc.xml";
	
	/**
	 * 当前对象所管理的模块名
	 */
	private String moduleName;
	
	/**
	 * 配置文件的路径
	 */
	private String configFilePath;
	
	/**
	 * ApplicationContext所使用的BeanFactory实例
	 */
	private DefaultListableBeanFactory beanFactory;

	/**
	 * 子模块容器的构造器
	 * 
	 * @param moduleName 
	 * 					模块名
	 * 
	 * @param parent 
	 * 					父容器对象的引用
	 */
	public ControllerChildWebApplicationContext(String moduleName, ApplicationContext parent) {
		super(parent);
		this.moduleName = moduleName;
		configFilePath = String.format(DEFAULT_CONFIG_LOCATION_TEMPLATE, moduleName);
		setServletConfig(ServletUtils.getServletConfig());
		setServletContext(ServletUtils.getServletContext());
		refresh();
		beanFactory = (DefaultListableBeanFactory) super.getBeanFactory();
	}

	/**
	 * 加载Bean的定义
	 */
	@Override
	protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException {
		XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

		beanDefinitionReader.setEnvironment(this.getEnvironment());
		beanDefinitionReader.setResourceLoader(this);
		beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(this));

		initBeanDefinitionReader(beanDefinitionReader);
		loadBeanDefinitions(beanDefinitionReader);
	}

	/**
	 * 初始化beanDefinitionReader中相关的数据，可在子类中覆盖
	 */
	protected void initBeanDefinitionReader(XmlBeanDefinitionReader beanDefinitionReader) {
	}

	/**
	 * 加载Bean
	 */
	protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) throws IOException {
		String[] configLocations = getConfigLocations();
		if (configLocations != null) {
			for (String configLocation : configLocations) {
				reader.loadBeanDefinitions(configLocation);
			}
		}
	}

	@Override
	protected String[] getDefaultConfigLocations() {
		return new String[] {configFilePath};
	}

	public String getModuleName() {
		return moduleName;
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

}
