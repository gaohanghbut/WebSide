package cn.hang.mvc.result.impl;

import cn.hang.mvc.RequestContext;
import cn.hang.mvc.result.ResultTypeHandler;

/**
 * 返回结果处理器的基础实现，此类的中直接重定向
 * 
 * @author Hang
 *
 */
public class RedirectResultTypeHandler implements ResultTypeHandler {

	@Override
	public boolean handleResult(RequestContext requestContext, String path) {
		try {
			requestContext.redirect(path);
			return true;
		} catch (Exception e) {
			//重定向失败
			return false;
		}
	}

}
