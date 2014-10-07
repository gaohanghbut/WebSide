package cn.hang.mvc.pipeline.valve.impl;

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
		return !StringUtils.isEmpty(ctx.getRequestContext().getResource());
	}

}
