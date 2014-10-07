package cn.hang.mvc.pipeline.valve.impl;

import java.lang.reflect.Method;

import cn.hang.mvc.common.util.ApplicationContextUtils;
import cn.hang.mvc.common.util.InvocationUtils;
import cn.hang.mvc.exception.DynamicInvocationException;
import cn.hang.mvc.pipeline.PipelineContext;
import cn.hang.mvc.pipeline.valve.ControlInvokeResult;

/**
 * 启动请求的Action
 * 
 * @author GaoHang
 * 
 */
public class ActionInvokeValve extends AbstractActionInvokeValve {
	
	@Override
	protected ControlInvokeResult invokeAction(PipelineContext ctx, String moduleName, String actionName, String methodName) {
		Object action = ApplicationContextUtils.getBean(moduleName, actionName);
		if (action == null) {//没有Action调用
			throw new DynamicInvocationException("Action不存在");
		}
		return invokeAction(ctx, methodName, action);
	}

	/**
	 * 执行请求的Action
	 * @param ctx
	 * @param methodName
	 * @param action
	 * @return
	 */
	protected ControlInvokeResult invokeAction(PipelineContext ctx, String methodName, Object action) {
		Method method = cn.hang.common.util.InvocationUtils.lookupMethod(action.getClass(), methodName);
	    Object result = InvocationUtils.invokeRunDataParameterMethod(action, method, ctx.getRequestContext());
		return new ControlInvokeResult(result, method);
	}

}
