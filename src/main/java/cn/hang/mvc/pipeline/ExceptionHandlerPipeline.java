package cn.hang.mvc.pipeline;

import cn.hang.mvc.RequestContext;
import cn.hang.mvc.RunData;

/**
 * 异常处理管道
 * 
 * @author GaoHang
 * 
 */
public interface ExceptionHandlerPipeline extends Pipeline {

	/**
	 * 处理异常的方法
	 * 
	 * @param runData
	 * @param t
	 *            发生的异常
	 * @return 正确处理返回true
	 * @deprecated 改为使用 {@link #handler(RequestContext,Throwable)} 
	 */
	boolean handler(RunData runData, Throwable t);

	/**
	 * 处理异常的方法
	 * 
	 * @param ctx
	 * @param t
	 *            发生的异常
	 * @return 正确处理返回true
	 */
	boolean handler(PipelineContext ctx, Throwable t);
}
