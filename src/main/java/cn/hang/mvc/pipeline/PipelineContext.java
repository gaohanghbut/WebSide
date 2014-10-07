package cn.hang.mvc.pipeline;

import cn.hang.mvc.RequestContext;

/**
 * 管理执行时的上下文
 * 
 * @author Hang
 * 
 */
public interface PipelineContext {

	/**
	 * 获取一次请求的请求上下文
	 * 
	 * @return
	 */
	public RequestContext getRequestContext();

	/**
	 * 是否已经返回数据给客户端
	 * 
	 * @return
	 */
	public boolean hasReturn();

	/**
	 * 存储Action调用的返回结果
	 * 
	 * @param result
	 *            action调用的返回结果
	 */
	public void storeActionInvokeResult(Object result);
	
	/**
	 * 获取Action调用的结果
	 * 
	 * @return action的调用结果
	 */
	public Object getActionInvokeResult();
}
