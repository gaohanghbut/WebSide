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
 * ������
 * 
 * @author GaoHang
 * 
 */
public class ControllerChildWebApplicationContext extends AbstractRefreshableWebApplicationContext implements BeanDefinitionRegistry {
	
	/**
	 * �µ�Ĭ�ϸ������ļ�·��ģ��,$sΪ���滻��������
	 */
	public static final String DEFAULT_CONFIG_LOCATION_TEMPLATE = "/WEB-INF/%s/mvc.xml";
	
	/**
	 * ��ǰ�����������ģ����
	 */
	private String moduleName;
	
	/**
	 * �����ļ���·��
	 */
	private String configFilePath;
	
	/**
	 * ApplicationContext��ʹ�õ�BeanFactoryʵ��
	 */
	private DefaultListableBeanFactory beanFactory;

	/**
	 * ��ģ�������Ĺ�����
	 * 
	 * @param moduleName 
	 * 					ģ����
	 * 
	 * @param parent 
	 * 					���������������
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
	 * ����Bean�Ķ���
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
	 * ��ʼ��beanDefinitionReader����ص����ݣ����������и���
	 */
	protected void initBeanDefinitionReader(XmlBeanDefinitionReader beanDefinitionReader) {
	}

	/**
	 * ����Bean
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
