package cn.hang.mvc.pipeline.valve.impl;

import cn.hang.mvc.common.util.StringUtils;
import cn.hang.mvc.pipeline.PipelineContext;
import cn.hang.mvc.pipeline.valve.AbstractValve;

/**
 *检查是否有action参数，如果不存在则将控制权返还给Web容器
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
