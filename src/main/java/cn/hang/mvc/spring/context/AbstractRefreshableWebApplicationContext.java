package cn.hang.mvc.spring.context;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractRefreshableConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.ui.context.Theme;
import org.springframework.ui.context.ThemeSource;
import org.springframework.ui.context.support.UiApplicationContextUtils;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ConfigurableWebEnvironment;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.ServletContextAwareProcessor;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.context.support.ServletContextResourcePatternResolver;
import org.springframework.web.context.support.StandardServletEnvironment;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 
 * @author GaoHang
 *
 */
public abstract class AbstractRefreshableWebApplicationContext extends AbstractRefreshableConfigApplicationContext implements ConfigurableWebApplicationContext, ThemeSource {

	/** Servlet context that this context runs in */
	private ServletContext servletContext;

	/** Servlet config that this context runs in, if any */
	private ServletConfig servletConfig;

	/** Namespace of this context, or {@code null} if root */
	private String namespace;

	/** the ThemeSource for this ApplicationContext */
	private ThemeSource themeSource;

	public AbstractRefreshableWebApplicationContext() {
		setDisplayName("Root WebApplicationContext");
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public AbstractRefreshableWebApplicationContext(ApplicationContext parent) {
		super(parent);
	}

	public ServletContext getServletContext() {
		return this.servletContext;
	}

	public void setServletConfig(ServletConfig servletConfig) {
		this.servletConfig = servletConfig;
		if (servletConfig != null && this.servletContext == null) {
			setServletContext(servletConfig.getServletContext());
		}
	}

	public ServletConfig getServletConfig() {
		return this.servletConfig;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
		if (namespace != null) {
			setDisplayName("WebApplicationContext for namespace '" + namespace
					+ "'");
		}
	}

	public String getNamespace() {
		return this.namespace;
	}

	@Override
	public String[] getConfigLocations() {
		return super.getConfigLocations();
	}

	@Override
	public String getApplicationName() {
		if (this.servletContext == null) {
			return "";
		}
		if (this.servletContext.getMajorVersion() == 2
				&& this.servletContext.getMinorVersion() < 5) {
			String name = this.servletContext.getServletContextName();
			return (name != null ? name : "");
		} else {
			// Servlet 2.5 available
			return this.servletContext.getContextPath();
		}
	}

	/**
	 * Create and return a new {@link StandardServletEnvironment}. Subclasses
	 * may override in order to configure the environment or specialize the
	 * environment type returned.
	 */
	@Override
	protected ConfigurableEnvironment createEnvironment() {
		return new StandardServletEnvironment();
	}

	/**
	 * Register request/session scopes, a {@link ServletContextAwareProcessor},
	 * etc.
	 */
	@Override
	protected void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) {
		beanFactory.addBeanPostProcessor(new ServletContextAwareProcessor(
				this.servletContext, this.servletConfig));
		beanFactory.ignoreDependencyInterface(ServletContextAware.class);
		beanFactory.ignoreDependencyInterface(ServletConfigAware.class);

		WebApplicationContextUtils.registerWebApplicationScopes(beanFactory,
				this.servletContext);
		WebApplicationContextUtils.registerEnvironmentBeans(beanFactory,
				this.servletContext, this.servletConfig);
	}

	/**
	 * This implementation supports file paths beneath the root of the
	 * ServletContext.
	 * 
	 * @see ServletContextResource
	 */
	@Override
	protected Resource getResourceByPath(String path) {
		return new ServletContextResource(this.servletContext, path);
	}

	/**
	 * This implementation supports pattern matching in unexpanded WARs too.
	 * 
	 * @see ServletContextResourcePatternResolver
	 */
	@Override
	protected ResourcePatternResolver getResourcePatternResolver() {
		return new ServletContextResourcePatternResolver(this);
	}

	/**
	 * Initialize the theme capability.
	 */
	@Override
	protected void onRefresh() {
		this.themeSource = UiApplicationContextUtils.initThemeSource(this);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Replace {@code Servlet}-related property sources.
	 */
	@Override
	protected void initPropertySources() {
		super.initPropertySources();
		ConfigurableEnvironment env = this.getEnvironment();
		if (env instanceof ConfigurableWebEnvironment) {
			((ConfigurableWebEnvironment) env).initPropertySources(
					this.servletContext, this.servletConfig);
		}
	}

	public Theme getTheme(String themeName) {
		return this.themeSource.getTheme(themeName);
	}

}
