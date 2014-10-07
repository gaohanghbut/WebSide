package cn.hang.mvc.pipeline.valve;

import cn.hang.mvc.pipeline.PipelineContext;

/**
 * 异常处理Valve接口，异常来源于执行Action
 * 
 * @author GaoHang
 * 
 */
public interface ExceptionHandlerValve extends Valve {

	/**
	 * 执行Valve
	 * 
	 * @param runData
	 */
	boolean execute(PipelineContext ctx, Throwable e);
	
}
