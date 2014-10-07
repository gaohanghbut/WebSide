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
 * 执行返回结果渲染器的Valve。每一个Action对应一个Screen（返回结果渲染器），在执行完Action后再执行对应的Screen。
 * 
 * @author Hang
 * 
 */
public class ScreenInvokeValve extends AbstractValve {

    /**
     * 默认的事件event参数值
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
        if (ctx.hasReturn()) {// 已在Action中执行跳转
            return Boolean.TRUE;
        }
        Object screen = getScreen(ctx.getRequestContext());
        if (screen == null) {
            return Boolean.TRUE;
        }

        String finalResultType = getFinalResultType(ctx.getRequestContext());
        if (StringUtils.isEmpty(finalResultType)) {
            //默认使用事件
            finalResultType = ctx.getRequestContext().getParameter(RequestContextConstants.TYPE_PARAMETER_NAME);
        }

        ControlInvokeResult object = invokeScreenMethod(ctx, screen, finalResultType);
        if (object != null) {
            viewRendService.render(ctx.getRequestContext(), object);
        }
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

        ResultTypeRewriteService resultTypeRewriteService = (ResultTypeRewriteService) ServiceManagers
                .getServiceManager().getService(ServiceManager.RESULT_TYPE_REWRITE_SERVICE);
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
    protected ControlInvokeResult invokeScreenMethod(PipelineContext ctx, Object screen, String resultType) {
        // 调用视图渲染方法，先调用方法名为${event}${ResultType}的方法，如果调用失败，再调用方法名为${resultType}的方法，如果失败，则调用Screen中的默认方法。
        String event = ctx.getRequestContext().getParameter(RequestContextConstants.EVENT_PARAMETER_NAME);
        if (StringUtils.isEmpty(event)) {
            event = DEFAULT_EVENT_NAME;
        }

        // 调用$(event)($Type)方法，如：testJson
        try {
            return invokeEventTypeMethod(ctx, screen, resultType, event);
        } catch (DynamicInvocationException e2) {
            try {
                // 调用$(Type)方法，如：json
                return invokeTypeMethod(ctx, screen, resultType);
            } catch (DynamicInvocationException e) {
                try {
                    return invokeEventNameMethod(ctx, screen, event);
                } catch (DynamicInvocationException e1) {
                    // 调用execute方法
                    try {
                        return invokeDefaultMethod(ctx, screen);
                    } catch (Exception e3) {
                        // 没有需要调用的渲染方法，则不进行调用
                    }

                }
            }
        }
        return null;
    }

    /**
     * 调用默认方法（excute），先调用有结果参数的，如果调用失败则调用无结果参数的方法
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
     * 调用与Action中的方法名相同的方法，先调用有结果参数的，如果调用失败则调用无结果参数的方法
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
     * 调用名为$(type)的方法
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
     * 调用$(event)$(Type)名的方法
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
