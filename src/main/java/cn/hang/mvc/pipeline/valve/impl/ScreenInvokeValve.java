package cn.hang.mvc.pipeline.valve.impl;

import cn.hang.mvc.RequestContext;
import cn.hang.mvc.common.util.ApplicationContextUtils;
import cn.hang.mvc.common.util.FrameworkConstants;
import cn.hang.mvc.common.util.InvocationUtils;
import cn.hang.mvc.common.util.RequestContextConstants;
import cn.hang.mvc.common.util.ServiceManagers;
import cn.hang.mvc.common.util.StringUtils;
import cn.hang.mvc.exception.DynamicInvocationException;
import cn.hang.mvc.pipeline.PipelineContext;
import cn.hang.mvc.pipeline.valve.AbstractValve;
import cn.hang.mvc.service.ResultTypeRewriteService;
import cn.hang.mvc.service.ServiceManager;

/**
 * 执行返回结果渲染器的Valve。每一个Action对应一个Screen（返回结果渲染器），在执行完Action后再执行对应的Screen。
 * 
 * @author Hang
 * 
 */
public class ScreenInvokeValve extends AbstractValve {
	
	/**
	 * 默认的事件event参数值
	 */
	private static final String	DEFAULT_EVENT_NAME	= "execute";
	
	@Override
	public boolean execute(PipelineContext ctx) {
		if (ctx.hasReturn()) {// 已在Action中执行跳转
			return Boolean.TRUE;
		}
		Object screen = getScreen(ctx.getRequestContext());
		if (screen == null) {
			return Boolean.TRUE;
		}
		
		String finalResultType = getFinalResultType(ctx.getRequestContext());
		if (StringUtils.isEmpty(finalResultType)) {
			return Boolean.FALSE;
		}
		
		invokeScreenMethod(ctx, screen, finalResultType);
		return Boolean.TRUE;
	}
	
	/**
	 * 通过返回结果重写服务得到重写后的返回类型
	 * 
	 * @param runData
	 * @return
	 */
	protected String getFinalResultType(RequestContext runData) {
		String resultType = runData.getParameter(RequestContextConstants.TYPE_PARAMETER_NAME);
		if (StringUtils.isEmpty(resultType)) {
			resultType = RequestContextConstants.DEFAULT_RESULT_TYPE;
		}
		
		ResultTypeRewriteService resultTypeRewriteService = (ResultTypeRewriteService) ServiceManagers.getServiceManager().getService(ServiceManager.RESULT_TYPE_REWRITE_SERVICE);
		resultType = resultTypeRewriteService.getFinalResultType(resultType);
		return resultType;
	}
	
	/**
	 * 查找Screen类，如果没找到则返回null
	 * 
	 * @param runData
	 * @return
	 */
	protected Object getScreen(RequestContext runData) {
		String screenName = runData.getScreenName();
		if (StringUtils.isEmpty(screenName)) {
			return null;
		}
		Object screen = ApplicationContextUtils.getBean(runData.getRequestModuleName(), screenName);
		if (screen == null) {
			return null;
		}
		return screen;
	}
	
	/**
	 * 调用Screen中相应的方法
	 * 
	 * @param runData
	 * @param screen
	 * @param resultType
	 */
	protected void invokeScreenMethod(PipelineContext ctx, Object screen, String resultType) {
		// 调用视图渲染方法，先调用方法名为${event}${ResultType}的方法，如果调用失败，再调用方法名为${resultType}的方法，如果失败，则调用Screen中的默认方法。
		String event = ctx.getRequestContext().getParameter(RequestContextConstants.EVENT_PARAMETER_NAME);
		if (StringUtils.isEmpty(event)) {
			event = DEFAULT_EVENT_NAME;
		}
		
		// 调用$(event)($Type)方法，如：testJson
		try {
			invokeEventTypeMethod(ctx, screen, resultType, event);
		} catch (DynamicInvocationException e2) {
			try {
				// 调用$(Type)方法，如：json
				invokeTypeMethod(ctx, screen, resultType);
			} catch (DynamicInvocationException e) {
				try {
					invokeEventNameMethod(ctx, screen, event);
				} catch (DynamicInvocationException e1) {
					// 调用execute方法
					try {
						invokeDefaultMethod(ctx, screen);
					} catch (Exception e3) {
						// 没有需要调用的渲染方法，则不进行调用
					}
					
				}
			}
		}
	}
	
	/**
	 * 调用默认方法（excute），先调用有结果参数的，如果调用失败则调用无结果参数的方法
	 * 
	 * @param ctx
	 * @param screen
	 */
	private void invokeDefaultMethod(PipelineContext ctx, Object screen) {
		try {
			InvocationUtils.invokeRunDataParameterMethodWithObject(screen, FrameworkConstants.SCREEN_METHOD_NAME, ctx.getRequestContext(), ctx.getActionInvokeResult());
		} catch (DynamicInvocationException e) {
			InvocationUtils.invokeRunDataParameterMethod(screen, FrameworkConstants.SCREEN_METHOD_NAME, ctx.getRequestContext());
		}
	}
	
	
	/**
	 * 调用与Action中的方法名相同的方法，先调用有结果参数的，如果调用失败则调用无结果参数的方法
	 * 
	 * @param ctx
	 * @param screen
	 */
	private void invokeEventNameMethod(PipelineContext ctx, Object screen, String event) {
		try {
			InvocationUtils.invokeRunDataParameterMethodWithObject(screen, event, ctx.getRequestContext(), ctx.getActionInvokeResult());
		} catch (DynamicInvocationException e) {
			InvocationUtils.invokeRunDataParameterMethod(screen, event, ctx.getRequestContext());
		}
	}
	
	/**
	 * 调用名为$(type)的方法
	 * @param ctx
	 * @param screen
	 * @param resultType
	 */
	private void invokeTypeMethod(PipelineContext ctx, Object screen, String resultType) {
		try {
			InvocationUtils.invokeRunDataParameterMethodWithObject(screen, resultType, ctx.getRequestContext(), ctx.getActionInvokeResult());
		} catch (DynamicInvocationException e) {
			InvocationUtils.invokeRunDataParameterMethod(screen, resultType, ctx.getRequestContext());
		}
	}
	
	/**
	 * 调用$(event)$(Type)名的方法
	 * @param ctx
	 * @param screen
	 * @param resultType
	 * @param event
	 */
	private void invokeEventTypeMethod(PipelineContext ctx, Object screen, String resultType, String event) {
		try {
			InvocationUtils.invokeRunDataParameterMethodWithObject(screen, event + StringUtils.firstCharToUppercase(resultType), ctx.getRequestContext(), ctx.getActionInvokeResult());
		} catch (DynamicInvocationException e) {
			InvocationUtils.invokeRunDataParameterMethod(screen, event + StringUtils.firstCharToUppercase(resultType), ctx.getRequestContext());
		}
	}
	
}
