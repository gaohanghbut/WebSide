package cn.hang.mvc.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hang.mvc.RequestContext;
import cn.hang.mvc.common.util.RequestContextUtils;
import cn.hang.mvc.common.util.ServletUtils;
import cn.hang.mvc.service.RunDataService;

/**
 * RunDataService的默认实现
 * 
 * @author GaoHang
 * 
 */
public class DefaultRunDataService implements RunDataService {
	
	/**
	 * Application范围的请求上下文
	 */
	private final RequestContext APPLICATION_REQUEST_CONTEXT;
	
	/**
	 * 模块的请求上下文以<模块名,requestContext>保存
	 */
	private final Map<String, RequestContext> moduleContextHolder = new HashMap<String, RequestContext>();

	/**
	 * 用于上锁的对象
	 */
	private Object lockObject = new Object();
	
	public DefaultRunDataService() {
		APPLICATION_REQUEST_CONTEXT = RequestContextUtils.getRequestContextFactory().getApplicationRequestContext();
	}
	
	@Override
	public void service() {
	}

	@Override
	public RequestContext getApplicationRunData() {
		return APPLICATION_REQUEST_CONTEXT;
	}

	@Override
	public RequestContext getRequestRunData(ServletRequest req) {
		String uri = ((HttpServletRequest) req).getRequestURL().toString();
		if (uri.equals(ServletUtils.getBaseUri())) {
			return RequestContextUtils.getRequestContextFactory().getGenericRequestContextWithNoneHttp(getApplicationRunData());
		}
		int sufix_start = uri.lastIndexOf('.');
		if (sufix_start < 0) {
			sufix_start = uri.length();
		}
		String moduleName = uri.substring(ServletUtils.getBaseUri().length(), sufix_start);
		return RequestContextUtils.getRequestContextFactory().getGenericRequestContextWithNoneHttp(getModuleRunData(moduleName));
	}

	@Override
	public RequestContext getModuleRunData(String module) {
		if (moduleContextHolder.get(module) == null) {
			synchronized (lockObject) {
				if (moduleContextHolder.get(module) == null) {
					moduleContextHolder.put(module, RequestContextUtils.getRequestContextFactory().getModuleRequestContext(getApplicationRunData(), module));
				}
			}
		}
		return moduleContextHolder.get(module);
	}

	@Override
	public RequestContext getRequestRunData(HttpServletRequest req, HttpServletResponse resp) {
		String uri = ((HttpServletRequest) req).getRequestURI();
		if (uri.equals(ServletUtils.getBaseUri())) {
			return RequestContextUtils.getRequestContextFactory().getGenericRequestContext(req, resp, getApplicationRunData());
		}
		int sufix_start = uri.lastIndexOf('.');
		if (sufix_start < 0) {
			sufix_start = uri.length();
		}
		String moduleName = uri.substring(ServletUtils.getBaseUri().length(), sufix_start);
		return RequestContextUtils.getRequestContextFactory().getGenericRequestContext(req, resp, getModuleRunData(moduleName));
	}

}
