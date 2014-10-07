package cn.hang.mvc.pipeline.valve.impl;

import cn.hang.common.util.DynamicInvocationException;
import cn.hang.mvc.common.util.RequestContextConstants;
import cn.hang.mvc.common.util.ServiceManagers;
import cn.hang.mvc.common.util.StringUtils;
import cn.hang.mvc.pipeline.PipelineContext;
import cn.hang.mvc.pipeline.valve.AbstractValve;
import cn.hang.mvc.pipeline.valve.ControlInvokeResult;
import cn.hang.mvc.service.ViewRendService;

/**
 * ����Actionִ��valve
 * 
 * @author Hang
 * 
 */
public abstract class AbstractActionInvokeValve extends AbstractValve {

    /**
     * Ĭ�ϵ��õ�Action�ķ�������event����������ʱ����execute����
     */
    protected static final String DEFAULT_METHOD_NAME = "execute";

    private ViewRendService viewRendService;

    public AbstractActionInvokeValve() {
        super();
    }

    @Override
    public void init() {
    }

    @Override
    public boolean execute(PipelineContext ctx) {
        if (viewRendService == null) {
            synchronized (this) {
                if (viewRendService == null) {
                    try {
                        viewRendService = ServiceManagers.getServiceManager().getService(ViewRendService.class);
                    } catch (Exception e) {
                        //do nothing
                    }
                }
            }
        }
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
            ControlInvokeResult result = invokeAction(ctx, moduleName, actionName, methodName);
            invokeRender(ctx, result);
            if (result != null) {
                ctx.storeActionInvokeResult(result.getResult());//�洢Action���ý��
            }
        } catch (DynamicInvocationException e) {
            return false;
        }
        return true;
    }

    protected void invokeRender(PipelineContext ctx, ControlInvokeResult result) {
        if (result != null && viewRendService != null) {
            viewRendService.render(ctx.getRequestContext(), result);
        }
    }

    /**
     * ִ��Action�еķ���
     * 
     * @param runData ����������
     * @param moduleName ģ����
     * @param actionName Action��
     * @param methodName Action�еķ�����
     * @return ������óɹ����򷵻ص��ý���������׳��쳣
     */
    protected abstract ControlInvokeResult invokeAction(PipelineContext ctx, String moduleName, String actionName,
            String methodName) throws DynamicInvocationException;

}
