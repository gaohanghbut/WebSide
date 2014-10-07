package cn.hang.mvc.pipeline.valve.impl;

import cn.hang.common.util.DynamicInvocationException;
import cn.hang.mvc.common.util.RequestContextConstants;
import cn.hang.mvc.common.util.StringUtils;
import cn.hang.mvc.pipeline.PipelineContext;
import cn.hang.mvc.pipeline.valve.AbstractValve;

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
			ctx.storeActionInvokeResult(result);//�洢Action���ý��
		} catch (DynamicInvocationException e) {
			return false;
		}
		return true;
	}

	/**
	 * ִ��Action�еķ���
	 * 
	 * @param runData
	 *            ����������
	 * @param moduleName
	 *            ģ����
	 * @param actionName
	 *            Action��
	 * @param methodName
	 *            Action�еķ�����
	 * @return ������óɹ����򷵻ص��ý���������׳��쳣
	 */
	protected abstract Object invokeAction(PipelineContext ctx, String moduleName, String actionName, String methodName) throws DynamicInvocationException;

}