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
 * ִ�з��ؽ����Ⱦ����Valve��ÿһ��Action��Ӧһ��Screen�����ؽ����Ⱦ��������ִ����Action����ִ�ж�Ӧ��Screen��
 * 
 * @author Hang
 * 
 */
public class ScreenInvokeValve extends AbstractValve {
	
	/**
	 * Ĭ�ϵ��¼�event����ֵ
	 */
	private static final String	DEFAULT_EVENT_NAME	= "execute";
	
	@Override
	public boolean execute(PipelineContext ctx) {
		if (ctx.hasReturn()) {// ����Action��ִ����ת
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
	 * ͨ�����ؽ����д����õ���д��ķ�������
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
	 * ����Screen�࣬���û�ҵ��򷵻�null
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
	 * ����Screen����Ӧ�ķ���
	 * 
	 * @param runData
	 * @param screen
	 * @param resultType
	 */
	protected void invokeScreenMethod(PipelineContext ctx, Object screen, String resultType) {
		// ������ͼ��Ⱦ�������ȵ��÷�����Ϊ${event}${ResultType}�ķ������������ʧ�ܣ��ٵ��÷�����Ϊ${resultType}�ķ��������ʧ�ܣ������Screen�е�Ĭ�Ϸ�����
		String event = ctx.getRequestContext().getParameter(RequestContextConstants.EVENT_PARAMETER_NAME);
		if (StringUtils.isEmpty(event)) {
			event = DEFAULT_EVENT_NAME;
		}
		
		// ����$(event)($Type)�������磺testJson
		try {
			invokeEventTypeMethod(ctx, screen, resultType, event);
		} catch (DynamicInvocationException e2) {
			try {
				// ����$(Type)�������磺json
				invokeTypeMethod(ctx, screen, resultType);
			} catch (DynamicInvocationException e) {
				try {
					invokeEventNameMethod(ctx, screen, event);
				} catch (DynamicInvocationException e1) {
					// ����execute����
					try {
						invokeDefaultMethod(ctx, screen);
					} catch (Exception e3) {
						// û����Ҫ���õ���Ⱦ�������򲻽��е���
					}
					
				}
			}
		}
	}
	
	/**
	 * ����Ĭ�Ϸ�����excute�����ȵ����н�������ģ��������ʧ��������޽�������ķ���
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
	 * ������Action�еķ�������ͬ�ķ������ȵ����н�������ģ��������ʧ��������޽�������ķ���
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
	 * ������Ϊ$(type)�ķ���
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
	 * ����$(event)$(Type)���ķ���
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
