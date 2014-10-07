package cn.hang.mvc.run;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hang.mvc.RequestContext;

/**
 * 默认请求上下文工厂
 * 
 * @author Hang
 *
 */
public class RestUrlRequestContextFactory extends DefaultRequestContextFactory {

	@Override
	public RequestContext getGenericRequestContext(HttpServletRequest req, HttpServletResponse resp, RequestContext parentRequestContext) {
		return new RestUrlRequestContext(req, resp, parentRequestContext);
	}

	@Override
	public RequestContext getGenericRequestContext(RequestContext parentRequestContext) {
		return new RestUrlRequestContext(parentRequestContext);
	}

	@Override
	public RequestContext getGenericRequestContextWithNoneHttp(RequestContext applicationRequestContext) {
		return new RestUrlRequestContext(applicationRequestContext);
	}

}
