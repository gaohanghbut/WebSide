package cn.hang.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求上下文件工厂
 * 
 * @author Hang
 * 
 */
public interface RequestContextFactory {

	/**
	 * 生产对象，返回代表一次HTTP请求的请求上下文对象
	 * 
	 * @return RequestContext的某个实现类的对象
	 */
	RequestContext getGenericRequestContext(HttpServletRequest req, HttpServletResponse resp, RequestContext parentRequestContext);
	
	/**
	 * 生产对象，返回代表一次HTTP请求的请求上下文对象
	 * 
	 * @return RequestContext的某个实现类的对象
	 */
	RequestContext getGenericRequestContext(RequestContext parentRequestContext);

	/**
	 * 返回代表一个模块的请求上下文对象
	 * 
	 * @param applicationRequestContext
	 *            整个应用的请求上下文件对象
	 * @return
	 */
	RequestContext getModuleRequestContext(RequestContext applicationRequestContext, String moduleName);
	
	/**
	 * 返回代表一个模块的请求上下文对象
	 * 
	 * @param applicationRequestContext
	 *            整个应用的请求上下文件对象
	 * @return
	 */
	RequestContext getGenericRequestContextWithNoneHttp(RequestContext applicationRequestContext);
	
	/**
	 * 
	 * @return
	 */
	RequestContext getApplicationRequestContext();
}
