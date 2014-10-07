package cn.hang.mvc.pipeline.valve;

import cn.hang.mvc.pipeline.ExceptionHandlerPipeline;
import cn.hang.mvc.pipeline.Pipeline;

/**
 * �쳣����ܵ�
 * 
 * @author GaoHang
 * 
 */
public interface TryCatchFinallyValve extends Valve {

	/**
	 * ����try������ִ�еĹܵ�
	 * 
	 * @param tryPipeline
	 */
	public abstract void setTryPipeline(Pipeline tryPipeline);

	/**
	 * ����catch�����ִ�еĹܵ�
	 * 
	 * @param catchPipeline
	 */
	public abstract void setCatchPipeline(ExceptionHandlerPipeline catchPipeline);

	/**
	 * ����finally������ִ�еĹܵ�
	 * 
	 * @param finallyPipeline
	 */
	public abstract void setFinallyPipeline(Pipeline finallyPipeline);

}