package cn.hang.mvc.pipeline.valve;

import cn.hang.mvc.pipeline.ConditionPipeline;

/**
 * �����ж�Valve
 * 
 * @author GaoHang
 * 
 */
public interface IfElseIfValve extends Valve {

	/**
	 * ����if pipeline
	 * 
	 * @param pipeline
	 */
	public void setIfPipeline(ConditionPipeline pipeline);

	/**
	 * ����else-if pipeline
	 * 
	 * @param pipeline
	 */
	public void setElseIfPipeline(ConditionPipeline pipeline);

	/**
	 * ����else pipeline
	 * 
	 * @param pipeline
	 */
	public void setElsePipeline(ConditionPipeline pipeline);

}
