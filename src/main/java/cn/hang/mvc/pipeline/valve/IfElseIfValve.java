package cn.hang.mvc.pipeline.valve;

import cn.hang.mvc.pipeline.ConditionPipeline;

/**
 * 条件判断Valve
 * 
 * @author GaoHang
 * 
 */
public interface IfElseIfValve extends Valve {

	/**
	 * 设置if pipeline
	 * 
	 * @param pipeline
	 */
	public void setIfPipeline(ConditionPipeline pipeline);

	/**
	 * 设置else-if pipeline
	 * 
	 * @param pipeline
	 */
	public void setElseIfPipeline(ConditionPipeline pipeline);

	/**
	 * 设置else pipeline
	 * 
	 * @param pipeline
	 */
	public void setElsePipeline(ConditionPipeline pipeline);

}
