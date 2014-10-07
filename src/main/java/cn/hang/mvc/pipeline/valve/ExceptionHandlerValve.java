package cn.hang.mvc.pipeline.valve;

import cn.hang.mvc.pipeline.PipelineContext;

/**
 * �쳣����Valve�ӿڣ��쳣��Դ��ִ��Action
 * 
 * @author GaoHang
 * 
 */
public interface ExceptionHandlerValve extends Valve {

	/**
	 * ִ��Valve
	 * 
	 * @param runData
	 */
	boolean execute(PipelineContext ctx, Throwable e);
	
}
