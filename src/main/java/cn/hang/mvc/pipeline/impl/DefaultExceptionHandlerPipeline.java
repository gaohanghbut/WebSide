package cn.hang.mvc.pipeline.impl;

import java.util.List;

import cn.hang.mvc.RunData;
import cn.hang.mvc.pipeline.ExceptionHandlerPipeline;
import cn.hang.mvc.pipeline.PipelineContext;
import cn.hang.mvc.pipeline.valve.ExceptionHandlerValve;
import cn.hang.mvc.pipeline.valve.Valve;

/**
 * 默认异常处理管道
 * 
 * @author GaoHang
 * 
 */
public class DefaultExceptionHandlerPipeline extends AbstractPipeline implements ExceptionHandlerPipeline{

	@Override
	public boolean handler(PipelineContext ctx) {
		List<Valve> valves = getValves();
		if (valves != null && valves.size() != 0) {
			for (Valve valve : valves) {
				ExceptionHandlerValve exceptionHandlerValve = (ExceptionHandlerValve) valve;
				exceptionHandlerValve.execute(ctx);
			}
		}
		return true;
	}

	@Override
	@Deprecated
	public boolean handler(RunData runData, Throwable t) {
//		List<Valve> valves = getValves();
//		if (valves != null && valves.size() != 0) {
//			for (Valve valve : valves) {
//				ExceptionHandlerValve exceptionHandlerValve = (ExceptionHandlerValve) valve;
//				exceptionHandlerValve.execute(runData, t);
//			}
//		}
		return true;
	}

	@Override
	public boolean handler(PipelineContext ctx, Throwable t) {
		List<Valve> valves = getValves();
		if (valves != null && valves.size() != 0) {
			for (Valve valve : valves) {
				ExceptionHandlerValve exceptionHandlerValve = (ExceptionHandlerValve) valve;
				exceptionHandlerValve.execute(ctx, t);
			}
		}
		return true;
	}

}
