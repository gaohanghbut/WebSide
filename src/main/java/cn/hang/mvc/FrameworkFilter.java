package cn.hang.mvc;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hang.common.util.StringUtils;
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
 * 中心控制器
 * 
 * @author GaoHang
 * 
 */
public class FrameworkFilter implements javax.servlet.Filter {

    private static final String REQUEST_CONTEXT_FACTORY_NAME = "requestContextFactory";

    private static final String SERVICE_MANAGER_FACTORY_NAME = "serviceManagerFactory";

    private FilterConfig config;

    /**
     * HTTP请求管理器，用于增强Request, Response和Session
     */
    private HttpRequestManager httpRequestManager = HttpRequestManagers.getHttpRequestManager();

    /**
     * 服务管理器，用于启动每一个服务
     */
    private ServiceManager serviceManager;

    private static final String BASE_URI = "base_uri";

    /**
     * 初始化Spring根容器
     */
    private void initialRootApplicationContext() {
        httpRequestManager.init();
        ControllerParentWebApplicationContext parent = new ControllerParentWebApplicationContext(
                config.getServletContext());
        ApplicationContextHolder applicationContextHolder = ApplicationContextUtils.getApplicationContextHolder();
        applicationContextHolder.setRootApplicationContext(parent);

        registryHttpBeanToContext(parent);

    }

    /**
     * 将与HTTP请求相关的Bean添加到依赖注入容器
     * 
     * @param parent
     */
    private void registryHttpBeanToContext(ControllerParentWebApplicationContext parent) {
        SingletonBeanRegister register = new HttpSingletonBeanRegister();
        register.register(parent);
    }

    /**
     * 调用每一个服务
     */
    private void invokeServices() {
        serviceManager.service();
    }

    @Override
    public void destroy() {
        ApplicationContextHolders.getApplicationContextHolder().destroy();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
            ServletException {
        try {
            httpRequestManager.wrapRequest(req, resp);// 增强request,response和session
            PipelineService pipelineService = (PipelineService) serviceManager
                    .getService(ServiceManager.PIPELINE_SERVICE);
            RequestContext ctx = ((RunDataService) serviceManager.getService(ServiceManager.RUN_DATA_SERVICE))
                    .getRequestRunData((HttpServletRequest) req, (HttpServletResponse) resp);
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
        this.config = config;
        //先注册工厂
        registerFactories();
        //初始化web.xml中的初始数据
        initWebApplicationBaseData(config);
        // 初始化根容器
        initialRootApplicationContext();
        // 加载模块容器，构建管道以及启动其它服务
        invokeServices();
        //冻结ApplicationContextHolder
        ApplicationContextHolders.constHolder();
    }

    /**
     * 注册所有需要的工厂
     */
    private void registerFactories() {
        String serviceManagerFactoryName = config.getInitParameter(SERVICE_MANAGER_FACTORY_NAME);
        if (StringUtils.isEmpty(serviceManagerFactoryName)) {
            ServiceManagers.registerServiceManagerFactory(getServiceManagerFactory());
        } else {
            ServiceManagers.registerServiceManagerFactory(instance(ServiceManagerFactory.class, serviceManagerFactoryName));
        }
        String requestContextFactoryName = config.getInitParameter(REQUEST_CONTEXT_FACTORY_NAME);
        if (StringUtils.isEmpty(requestContextFactoryName)) {
            RequestContextUtils.registerRequestContextFactory(getRequestContextFactory());
        } else {
            RequestContextUtils.registerRequestContextFactory(instance(RequestContextFactory.class, requestContextFactoryName));
        }
    }

    private <T> T instance(Class<T> c, String name) {
        try {
            return c.cast(Class.forName(name).newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 初始化基本数据
     * 
     * @param config
     */
    private void initWebApplicationBaseData(FilterConfig config) {
        this.config = config;
        ServletUtils.setServletContext(config.getServletContext());
        //注册服务管理器工厂
        serviceManager = ServiceManagers.getServiceManager();
        String baseUrl = config.getInitParameter(BASE_URI);
        if (StringUtils.isEmpty(baseUrl)) {
            ServletUtils.setBaseUri(config.getServletContext().getContextPath() + "/");
        } else {
            ServletUtils.setBaseUri(baseUrl);
        }
    }

    /**
     * 返回服务管理器工厂，子类可以覆盖它以返回不同工厂的实例
     * 
     * @return
     */
    protected ServiceManagerFactory getServiceManagerFactory() {
        return new DefaultServiceManagerFactory();
    }

    /**
     * 返回请求上下文工厂
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
