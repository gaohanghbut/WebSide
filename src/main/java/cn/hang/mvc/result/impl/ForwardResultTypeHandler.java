package cn.hang.mvc.result.impl;

import cn.hang.mvc.RequestContext;
import cn.hang.mvc.result.ResultTypeHandler;

/**
 * 返回结果处理器的基础实现，此类的中直接跳转到目的连接
 * 
 * @author Hang
 *
 */
public class ForwardResultTypeHandler implements ResultTypeHandler {
	
	public ForwardResultTypeHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean handleResult(RequestContext requestContext, String path) {
		try {
			requestContext.forward(path);
			return true;
		} catch (Exception e) {
			//中转失败
			return false;
		}
	}

}
