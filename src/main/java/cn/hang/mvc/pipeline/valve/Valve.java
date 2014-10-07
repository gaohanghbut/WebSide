package cn.hang.mvc.pipeline.valve;

import cn.hang.mvc.pipeline.Pipeline;
import cn.hang.mvc.pipeline.PipelineContext;

/**
 * ����Actionʱ�Ĺ�����
 * 
 * @author GaoHang
 * 
 */
public interface Valve {

	/**
	 * ��ʼ��Valve
	 */
	void init();

	/**
	 * ִ��Valve
	 * 
	 * @param ctx
	 */
	boolean execute(PipelineContext ctx);

	/**
	 * Valve���ڵ�pipeline
	 * 
	 * @param pipeline
	 */
	void currentPipeline(Pipeline pipeline);

}
