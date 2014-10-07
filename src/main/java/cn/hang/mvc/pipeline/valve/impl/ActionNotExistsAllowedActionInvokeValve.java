package cn.hang.mvc.pipeline.valve.impl;

import cn.hang.mvc.common.util.ApplicationContextUtils;
import cn.hang.mvc.pipeline.PipelineContext;
import cn.hang.mvc.pipeline.valve.ControlInvokeResult;

/**
 * 允许action指定的类不存在的情况，在这种情况下valve也返回true，所以可以继续执行后面的valve。
 * 如：请求的action为空，则直接进行action参数名相关的跳转。如果使用多个类似于这样的valve，可以
 * 定义一系列与action相关的动作，使之全部执行。
 * 
 * @author Hang
 *
 */
public class ActionNotExistsAllowedActionInvokeValve extends ActionInvokeValve {

	/**
	 * @param runData
	 * @param moduleName
	 * @param actionName
	 * @param methodName
	 * @return
	 */
	protected ControlInvokeResult invokeAction(PipelineContext ctx, String moduleName, String actionName, String methodName) {
		Object action = ApplicationContextUtils.getBean(moduleName, actionName);
		if (action == null) {//指定action不存在
			return null;
		}
		return invokeAction(ctx, methodName, action);
	}
}
