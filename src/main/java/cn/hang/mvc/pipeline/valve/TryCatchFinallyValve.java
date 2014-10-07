package cn.hang.mvc.pipeline.valve;

import cn.hang.mvc.pipeline.ExceptionHandlerPipeline;
import cn.hang.mvc.pipeline.Pipeline;

/**
 * 异常处理管道
 * 
 * @author GaoHang
 * 
 */
public interface TryCatchFinallyValve extends Valve {

	/**
	 * 设置try语句块中执行的管道
	 * 
	 * @param tryPipeline
	 */
	public abstract void setTryPipeline(Pipeline tryPipeline);

	/**
	 * 设置catch语句中执行的管道
	 * 
	 * @param catchPipeline
	 */
	public abstract void setCatchPipeline(ExceptionHandlerPipeline catchPipeline);

	/**
	 * 设置finally语句块中执行的管道
	 * 
	 * @param finallyPipeline
	 */
	public abstract void setFinallyPipeline(Pipeline finallyPipeline);

}