package cn.hang.mvc.pipeline.valve;

import cn.hang.mvc.pipeline.Pipeline;
import cn.hang.mvc.pipeline.PipelineContext;

/**
 * 请求Action时的过虑器
 * 
 * @author GaoHang
 * 
 */
public interface Valve {

	/**
	 * 初始化Valve
	 */
	void init();

	/**
	 * 执行Valve
	 * 
	 * @param ctx
	 */
	boolean execute(PipelineContext ctx);

	/**
	 * Valve属于的pipeline
	 * 
	 * @param pipeline
	 */
	void currentPipeline(Pipeline pipeline);

}
