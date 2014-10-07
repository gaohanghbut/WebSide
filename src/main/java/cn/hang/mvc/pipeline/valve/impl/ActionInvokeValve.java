package cn.hang.mvc.pipeline.valve.impl;

import cn.hang.mvc.common.util.ApplicationContextUtils;
import cn.hang.mvc.common.util.InvocationUtils;
import cn.hang.mvc.exception.DynamicInvocationException;
import cn.hang.mvc.pipeline.PipelineContext;

/**
 * ���������Action
 * 
 * @author GaoHang
 * 
 */
public class ActionInvokeValve extends AbstractActionInvokeValve {
	
	@Override
	protected Object invokeAction(PipelineContext ctx, String moduleName, String actionName, String methodName) {
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
	protected Object invokeAction(PipelineContext ctx, String methodName, Object action) {
		Object result = InvocationUtils.invokeRunDataParameterMethod(action, methodName, ctx.getRequestContext());
		return result;
	}

}
