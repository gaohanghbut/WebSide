package cn.hang.mvc.pipeline.valve.impl;

import java.lang.reflect.Method;

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
import cn.hang.mvc.pipeline.valve.ControlInvokeResult;
import cn.hang.mvc.service.ResultTypeRewriteService;
import cn.hang.mvc.service.ServiceManager;
import cn.hang.mvc.service.ViewRendService;

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
    private static final String DEFAULT_EVENT_NAME = "execute";
    
    private ViewRendService viewRendService;

    @Override
    public boolean execute(PipelineContext ctx) {
        if (viewRendService == null) {
            synchronized (this) {
                if (viewRendService == null) {
                    viewRendService = ServiceManagers.getServiceManager().getService(ViewRendService.class);
                }
            }
        }
        if (ctx.hasReturn()) {// ����Action��ִ����ת
            return Boolean.TRUE;
        }
        Object screen = getScreen(ctx.getRequestContext());
        if (screen == null) {
            return Boolean.TRUE;
        }

        String finalResultType = getFinalResultType(ctx.getRequestContext());
        if (StringUtils.isEmpty(finalResultType)) {
            //Ĭ��ʹ���¼�
            finalResultType = ctx.getRequestContext().getParameter(RequestContextConstants.TYPE_PARAMETER_NAME);
        }

        ControlInvokeResult object = invokeScreenMethod(ctx, screen, finalResultType);
        if (object != null) {
            viewRendService.render(ctx.getRequestContext(), object);
        }
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

        ResultTypeRewriteService resultTypeRewriteService = (ResultTypeRewriteService) ServiceManagers
                .getServiceManager().getService(ServiceManager.RESULT_TYPE_REWRITE_SERVICE);
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
    protected ControlInvokeResult invokeScreenMethod(PipelineContext ctx, Object screen, String resultType) {
        // ������ͼ��Ⱦ�������ȵ��÷�����Ϊ${event}${ResultType}�ķ������������ʧ�ܣ��ٵ��÷�����Ϊ${resultType}�ķ��������ʧ�ܣ������Screen�е�Ĭ�Ϸ�����
        String event = ctx.getRequestContext().getParameter(RequestContextConstants.EVENT_PARAMETER_NAME);
        if (StringUtils.isEmpty(event)) {
            event = DEFAULT_EVENT_NAME;
        }

        // ����$(event)($Type)�������磺testJson
        try {
            return invokeEventTypeMethod(ctx, screen, resultType, event);
        } catch (DynamicInvocationException e2) {
            try {
                // ����$(Type)�������磺json
                return invokeTypeMethod(ctx, screen, resultType);
            } catch (DynamicInvocationException e) {
                try {
                    return invokeEventNameMethod(ctx, screen, event);
                } catch (DynamicInvocationException e1) {
                    // ����execute����
                    try {
                        return invokeDefaultMethod(ctx, screen);
                    } catch (Exception e3) {
                        // û����Ҫ���õ���Ⱦ�������򲻽��е���
                    }

                }
            }
        }
        return null;
    }

    /**
     * ����Ĭ�Ϸ�����excute�����ȵ����н�������ģ��������ʧ��������޽�������ķ���
     * 
     * @param ctx
     * @param screen
     */
    private ControlInvokeResult invokeDefaultMethod(PipelineContext ctx, Object screen) {
        String methodName = FrameworkConstants.SCREEN_METHOD_NAME;
        Method method = cn.hang.common.util.InvocationUtils.lookupMethod(screen.getClass(), methodName);
        if (method == null) {
            throw new DynamicInvocationException("no method");
        }
        Object result;
        try {
            result = InvocationUtils.invokeRunDataParameterMethodWithObject(screen, method, ctx.getRequestContext(),
                    ctx.getActionInvokeResult());
        } catch (DynamicInvocationException e) {
            result = InvocationUtils.invokeRunDataParameterMethod(screen, method, ctx.getRequestContext());
        }
        return new ControlInvokeResult(result, method);
    }

    /**
     * ������Action�еķ�������ͬ�ķ������ȵ����н�������ģ��������ʧ��������޽�������ķ���
     * 
     * @param ctx
     * @param screen
     */
    private ControlInvokeResult invokeEventNameMethod(PipelineContext ctx, Object screen, String event) {
        String methodName = event;
        Method method = cn.hang.common.util.InvocationUtils.lookupMethod(screen.getClass(), methodName);
        if (method == null) {
            throw new DynamicInvocationException("no method");
        }
        Object result;
        try {
            result = InvocationUtils.invokeRunDataParameterMethodWithObject(screen, method, ctx.getRequestContext(),
                    ctx.getActionInvokeResult());
        } catch (DynamicInvocationException e) {
            result = InvocationUtils.invokeRunDataParameterMethod(screen, method, ctx.getRequestContext());
        }
        return new ControlInvokeResult(result, method);
    }

    /**
     * ������Ϊ$(type)�ķ���
     * 
     * @param ctx
     * @param screen
     * @param resultType
     */
    private ControlInvokeResult invokeTypeMethod(PipelineContext ctx, Object screen, String resultType) {
        String methodName = resultType;
        Method method = cn.hang.common.util.InvocationUtils.lookupMethod(screen.getClass(), methodName);
        if (method == null) {
            throw new DynamicInvocationException("no method");
        }
        Object result;
        try {
            result = InvocationUtils.invokeRunDataParameterMethodWithObject(screen, method, ctx.getRequestContext(),
                    ctx.getActionInvokeResult());
        } catch (DynamicInvocationException e) {
            result = InvocationUtils.invokeRunDataParameterMethod(screen, method, ctx.getRequestContext());
        }
        return new ControlInvokeResult(result, method);
    }

    /**
     * ����$(event)$(Type)���ķ���
     * 
     * @param ctx
     * @param screen
     * @param resultType
     * @param event
     */
    private ControlInvokeResult invokeEventTypeMethod(PipelineContext ctx, Object screen, String resultType,
            String event) {
        String methodName = event + StringUtils.firstCharToUppercase(resultType);
        Method method = cn.hang.common.util.InvocationUtils.lookupMethod(screen.getClass(), methodName);
        if (method == null) {
            throw new DynamicInvocationException("no method");
        }
        Object result;
        try {
            result = InvocationUtils.invokeRunDataParameterMethodWithObject(screen, method, ctx.getRequestContext(),
                    ctx.getActionInvokeResult());
        } catch (DynamicInvocationException e) {
            result = InvocationUtils.invokeRunDataParameterMethod(screen, method, ctx.getRequestContext());
        }
        return new ControlInvokeResult(result, method);
    }

}
