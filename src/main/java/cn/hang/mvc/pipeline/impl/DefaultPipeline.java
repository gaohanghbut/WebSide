package cn.hang.mvc.pipeline.impl;

import java.util.List;

import cn.hang.mvc.exception.PipelineException;
import cn.hang.mvc.pipeline.Pipeline;
import cn.hang.mvc.pipeline.PipelineContext;
import cn.hang.mvc.pipeline.valve.Valve;

/**
 * Ä¬ÈÏ¹ÜµÀ
 * 
 * @author GaoHang
 * 
 */
public class DefaultPipeline extends AbstractPipeline implements Pipeline {

	@Override
	public boolean handler(PipelineContext ctx) {
		List<Valve> valves = getValves();
		if (valves == null || valves.size() == 0) {
			return false;
		}
		try {
			for (Valve valve : valves) {
				if (!valve.execute(ctx)) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			if (e instanceof PipelineException) {
				throw (PipelineException) e;
			}
			e.printStackTrace();
			throw new PipelineException(e);
		}
	}

}
