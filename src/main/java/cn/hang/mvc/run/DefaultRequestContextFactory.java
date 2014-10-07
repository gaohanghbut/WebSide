package cn.hang.mvc.run;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hang.mvc.RequestContext;
import cn.hang.mvc.RequestContextFactory;

/**
 * 默认请求上下文工厂
 * 
 * @author Hang
 *
 */
public class DefaultRequestContextFactory implements RequestContextFactory {

	@Override
	public RequestContext getModuleRequestContext(RequestContext applicationRequestContext, String moduleName) {
		return new ModuleRequestContext(applicationRequestContext, moduleName);
	}

	@Override
	public RequestContext getApplicationRequestContext() {
		return new ApplicationRequestContext();
	}

	@Override
	public RequestContext getGenericRequestContext(HttpServletRequest req, HttpServletResponse resp, RequestContext parentRequestContext) {
		return new DefaultRequestContext(req, resp, parentRequestContext);
	}

	@Override
	public RequestContext getGenericRequestContext(RequestContext parentRequestContext) {
		return new DefaultRequestContext(parentRequestContext);
	}

	@Override
	public RequestContext getGenericRequestContextWithNoneHttp(RequestContext applicationRequestContext) {
		return new RequestContextImpl(applicationRequestContext);
	}

}
