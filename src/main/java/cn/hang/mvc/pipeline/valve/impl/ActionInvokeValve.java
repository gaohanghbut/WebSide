package cn.hang.mvc.pipeline.valve.impl;

import java.lang.reflect.Method;

import cn.hang.mvc.common.util.ApplicationContextUtils;
import cn.hang.mvc.common.util.InvocationUtils;
import cn.hang.mvc.exception.DynamicInvocationException;
import cn.hang.mvc.pipeline.PipelineContext;
import cn.hang.mvc.pipeline.valve.ControlInvokeResult;

/**
 * ���������Action
 * 
 * @author GaoHang
 * 
 */
public class ActionInvokeValve extends AbstractActionInvokeValve {
	
	@Override
	protected ControlInvokeResult invokeAction(PipelineContext ctx, String moduleName, String actionName, String methodName) {
		Object action = ApplicationContextUtils.getBean(moduleName, actionName);
		if (action == null) {//û��Action����
			throw new DynamicInvocationException("Action������");
		}
		return invokeAction(ctx, methodName, action);
	}

	/**
	 * ִ�������Action
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
