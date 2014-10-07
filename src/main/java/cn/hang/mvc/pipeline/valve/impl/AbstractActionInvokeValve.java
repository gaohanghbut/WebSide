package cn.hang.mvc.pipeline.valve.impl;

import cn.hang.common.util.DynamicInvocationException;
import cn.hang.mvc.common.util.RequestContextConstants;
import cn.hang.mvc.common.util.StringUtils;
import cn.hang.mvc.pipeline.PipelineContext;
import cn.hang.mvc.pipeline.valve.AbstractValve;

/**
 * 抽象Action执行valve
 * 
 * @author Hang
 *
 */
public abstract class AbstractActionInvokeValve extends AbstractValve {

	/**
	 * 默认调用的Action的方法，当event参数不存在时调用execute方法
	 */
	protected static final String DEFAULT_METHOD_NAME = "execute";

	public AbstractActionInvokeValve() {
		super();
	}

	@Override
	public void init() {
	}

	@Override
	public boolean execute(PipelineContext ctx) {
		if (ctx.hasReturn()) {
			return Boolean.TRUE;
		}
		String moduleName = ctx.getRequestContext().getRequestModuleName();
		String actionName = ctx.getRequestContext().getActionName();
		if (StringUtils.isEmpty(actionName)) {
			return false;
		}
		String methodName = ctx.getRequestContext().getParameter(RequestContextConstants.EVENT_PARAMETER_NAME);
		if (StringUtils.isEmpty(methodName)) {
			methodName = DEFAULT_METHOD_NAME;
		}
		try {
			Object result = invokeAction(ctx, moduleName, actionName, methodName);
			ctx.storeActionInvokeResult(result);//存储Action调用结果
		} catch (DynamicInvocationException e) {
			return false;
		}
		return true;
	}

	/**
	 * 执行Action中的方法
	 * 
	 * @param runData
	 *            请求上下文
	 * @param moduleName
	 *            模块名
	 * @param actionName
	 *            Action名
	 * @param methodName
	 *            Action中的方法名
	 * @return 如果调用成功，则返回调用结果，否则抛出异常
	 */
	protected abstract Object invokeAction(PipelineContext ctx, String moduleName, String actionName, String methodName) throws DynamicInvocationException;

}