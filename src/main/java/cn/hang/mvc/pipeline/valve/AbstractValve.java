package cn.hang.mvc.pipeline.valve;

import cn.hang.mvc.pipeline.Pipeline;

/**
 * ����pipeline����
 * 
 * @author GaoHang
 * 
 */
public abstract class AbstractValve implements Valve {

	private Pipeline pipeline;

	public AbstractValve() {
		super();
	}

	@Override
	public void init() {
	}

	@Override
	public void currentPipeline(Pipeline pipeline) {
		this.pipeline = pipeline;
	}

	protected Pipeline getPipeline() {
		return pipeline;
	}
}