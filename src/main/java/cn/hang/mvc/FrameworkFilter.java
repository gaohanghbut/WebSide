package cn.hang.mvc;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hang.mvc.common.util.ApplicationContextUtils;
import cn.hang.mvc.common.util.HttpRequestManagers;
import cn.hang.mvc.common.util.RequestContextUtils;
import cn.hang.mvc.common.util.ServiceManagers;
import cn.hang.mvc.common.util.ServletUtils;
import cn.hang.mvc.http.HttpRequestManager;
import cn.hang.mvc.run.DefaultRequestContextFactory;
import cn.hang.mvc.service.PipelineService;
import cn.hang.mvc.service.RunDataService;
import cn.hang.mvc.service.ServiceManager;
import cn.hang.mvc.service.ServiceManagerFactory;
import cn.hang.mvc.service.impl.DefaultServiceManagerFactory;
import cn.hang.mvc.spring.context.ControllerParentWebApplicationContext;
import cn.hang.mvc.spring.context.registry.SingletonBeanRegister;
import cn.hang.mvc.spring.context.registry.impl.HttpSingletonBeanRegister;
import cn.hang.mvc.spring.holder.ApplicationContextHolder;
import cn.hang.mvc.spring.holder.ApplicationContextHolders;

/**
 * ���Ŀ�����
 * 
 * @author GaoHang
 * 
 */
public class FrameworkFilter implements javax.servlet.Filter {

	private FilterConfig config;

	/**
	 * HTTP�����������������ǿRequest, Response��Session
	 */
	private HttpRequestManager httpRequestManager = HttpRequestManagers.getHttpRequestManager();

	/**
	 * �������������������ÿһ������
	 */
	private ServiceManager serviceManager;

	private static final String BASE_URI = "base_uri";
	
	/**
	 * ��ʼ��Spring������
	 */
	private void initialRootApplicationContext() {
		httpRequestManager.init();
		ControllerParentWebApplicationContext parent = new ControllerParentWebApplicationContext(config.getServletContext());
		ApplicationContextHolder applicationContextHolder = ApplicationContextUtils.getApplicationContextHolder();
		applicationContextHolder.setRootApplicationContext(parent);

		registryHttpBeanToContext(parent);
		
	}

	/**
	 * ����HTTP������ص�Bean��ӵ�����ע������
	 * 
	 * @param parent
	 */
	private void registryHttpBeanToContext(ControllerParentWebApplicationContext parent) {
		SingletonBeanRegister register = new HttpSingletonBeanRegister();
		register.register(parent);
	}

	/**
	 * ����ÿһ������
	 */
	private void invokeServices() {
		serviceManager.service();
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		try {
			httpRequestManager.wrapRequest(req, resp);// ��ǿrequest,response��session
			PipelineService pipelineService = (PipelineService) serviceManager.getService(ServiceManager.PIPELINE_SERVICE);
			RequestContext ctx = ((RunDataService) serviceManager.getService(ServiceManager.RUN_DATA_SERVICE)).getRequestRunData((HttpServletRequest) req, (HttpServletResponse) resp);
			boolean success = pipelineService.invokePipeline(ctx);
			if (!success && !ctx.hasReturned()) {
				chain.doFilter(req, resp);
			}
		} finally {
			httpRequestManager.cleanRequest();
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		//��ע�Ṥ��
		registerFactories();
		//��ʼ��web.xml�еĳ�ʼ����
		initWebApplicationBaseData(config);
		// ��ʼ��������
		initialRootApplicationContext();
		// ����ģ�������������ܵ��Լ�������������
		invokeServices();
		//����ApplicationContextHolder
		ApplicationContextHolders.constHolder();
	}

	/**
	 * ע��������Ҫ�Ĺ���
	 */
	private void registerFactories() {
		ServiceManagers.registerServiceManagerFactory(getServiceManagerFactory());
		RequestContextUtils.registerRequestContextFactory(getRequestContextFactory());
	}

	/**
	 * ��ʼ����������
	 * 
	 * @param config
	 */
	private void initWebApplicationBaseData(FilterConfig config) {
		this.config = config;
		ServletUtils.setServletContext(config.getServletContext());
		//ע��������������
		serviceManager = ServiceManagers.getServiceManager();
		ServletUtils.setBaseUri(config.getInitParameter(BASE_URI));
	}
	
	/**
	 * ���ط��������������������Ը������Է��ز�ͬ������ʵ��
	 * 
	 * @return
	 */
	protected ServiceManagerFactory getServiceManagerFactory() {
		return new DefaultServiceManagerFactory();
	}
	
	/**
	 * �������������Ĺ���
	 * 
	 * @return
	 */
	protected RequestContextFactory getRequestContextFactory() {
		return new DefaultRequestContextFactory();
	}

	public FilterConfig getConfig() {
		return config;
	}

}
