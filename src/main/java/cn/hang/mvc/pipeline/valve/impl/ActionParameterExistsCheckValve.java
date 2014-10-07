package cn.hang.mvc.pipeline.valve.impl;

import cn.hang.mvc.common.util.RequestContextConstants;
import cn.hang.mvc.common.util.StringUtils;
import cn.hang.mvc.pipeline.PipelineContext;
import cn.hang.mvc.pipeline.valve.AbstractValve;

/**
 *����Ƿ���action����������������򽫿���Ȩ������Web����
 * 
 * @author Hang
 *
 */
public class ActionParameterExistsCheckValve extends AbstractValve {

	@Override
	public boolean execute(PipelineContext ctx) {
		String action = ctx.getRequestContext().getParameter(RequestContextConstants.RESOURCE_PARAMETER_NAME);
		return !StringUtils.isEmpty(action);
	}

}
