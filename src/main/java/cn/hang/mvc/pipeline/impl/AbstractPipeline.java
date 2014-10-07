package cn.hang.mvc.pipeline.impl;

import java.util.List;

import cn.hang.mvc.pipeline.Pipeline;
import cn.hang.mvc.pipeline.valve.Valve;

/**
 * 
 * @author GaoHang
 *
 */
public abstract class AbstractPipeline implements Pipeline {

	/**
	 * 管道中的Valve
	 */
	private List<Valve> valves;

	public AbstractPipeline() {
		super();
	}

	@Override
	public void init() {
		if (valves == null) {
			return;
		}
		for (Valve valve : valves) {
			valve.currentPipeline(this);
		}
	}

	@Override
	public void setValves(List<Valve> valves) {
		this.valves = valves;
	}

	public List<Valve> getValves() {
		return valves;
	}

}