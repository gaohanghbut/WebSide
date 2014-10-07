package cn.hang.mvc.pipeline.valve.impl;

import cn.hang.mvc.common.util.ApplicationContextUtils;
import cn.hang.mvc.pipeline.PipelineContext;
import cn.hang.mvc.pipeline.valve.ControlInvokeResult;

/**
 * ����actionָ�����಻���ڵ�����������������valveҲ����true�����Կ��Լ���ִ�к����valve��
 * �磺�����actionΪ�գ���ֱ�ӽ���action��������ص���ת�����ʹ�ö��������������valve������
 * ����һϵ����action��صĶ�����ʹ֮ȫ��ִ�С�
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
		if (action == null) {//ָ��action������
			return null;
		}
		return invokeAction(ctx, methodName, action);
	}
}
