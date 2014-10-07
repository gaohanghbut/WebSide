package cn.hang.mvc.pipeline.valve.impl;

import java.util.ArrayList;
import java.util.List;

import cn.hang.mvc.pipeline.ConditionPipeline;
import cn.hang.mvc.pipeline.PipelineContext;
import cn.hang.mvc.pipeline.valve.AbstractValve;
import cn.hang.mvc.pipeline.valve.IfElseIfValve;

/**
 * 默认后缀条件过虑valve的实现
 * 
 * @author GaoHang
 * 
 */
public class DefaultIfElseIfValve extends AbstractValve implements IfElseIfValve {
	
	/**
	 * if条件满足时执行的pipeline
	 */
	private ConditionPipeline ifConditionPipeline;
	
	/**
	 * else-if条件满足时执行的管道集合，因为else-if可能有多个
	 */
	private List<ConditionPipeline> elseIfConditionPipelines = new ArrayList<ConditionPipeline>();
	
	/**
	 * 其它条件下执行的管道
	 */
	private ConditionPipeline elseConditionPipeline;

	@Override
	public void init() {
	}

	@Override
	public boolean execute(PipelineContext ctx) {
		String uri = ctx.getRequestContext().getURI();
		int sufix_start = uri.lastIndexOf('.');
		String sufix;
		if (sufix_start < 0) {
			sufix = "*";
		} else {
			sufix = uri.substring(uri.lastIndexOf('.') + 1);
		}
		ConditionPipeline conditionPipeline;
		if (ifConditionPipeline != null && ifConditionPipeline.accept(sufix)) {
			return ifConditionPipeline.handler(ctx);
		} else if ((conditionPipeline = elseIfMeet(sufix)) != null) {
			return conditionPipeline.handler(ctx);
		} else if (elseConditionPipeline != null ) {
			return elseConditionPipeline.handler(ctx);
		}
		return true;
	}

	@Override
	public void setIfPipeline(ConditionPipeline pipeline) {
		this.ifConditionPipeline = pipeline;
	}

	@Override
	public void setElseIfPipeline(ConditionPipeline pipeline) {
		this.elseIfConditionPipelines.add(pipeline);
	}

	@Override
	public void setElsePipeline(ConditionPipeline pipeline) {
		this.elseConditionPipeline = pipeline;
	}

	/**
	 * else-if管道能否接指定后缀
	 * @param sufix 后缀名
	 * @return 如果能接收则返回true，否则返回false
	 */
	private ConditionPipeline elseIfMeet(String sufix) {
		for (ConditionPipeline pipeline : elseIfConditionPipelines) {
			if (pipeline.accept(sufix)) {
				return pipeline;
			}
		}
		return null;
	}

}
