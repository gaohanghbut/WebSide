package cn.hang.mvc.pipeline.valve.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.hang.mvc.pipeline.PipelineContext;
import cn.hang.mvc.pipeline.valve.AbstractValve;
import cn.hang.mvc.pipeline.valve.ExceptionHandlerValve;
import cn.hang.mvc.pipeline.valve.Valve;

/**
 * 默认ExceptionHandlerValve的实现，如果出现异常，则跳转到请求URL
 * 
 * @author GaoHang
 * 
 */
public class DefaultExceptionHandlerValve extends AbstractValve implements ExceptionHandlerValve {
	
	private Log log = LogFactory.getLog(Valve.class);

	@Override
	public boolean execute(PipelineContext ctx) {
		String url = ctx.getRequestContext().getURL();
		ctx.getRequestContext().redirect(url);
		return true;
	}

	@Override
	public boolean execute(PipelineContext ctx, Throwable e) {
		e.printStackTrace();
		log.error(e.getCause(), e);
		ctx.getRequestContext().putValue("error_message", e.getMessage());
		return execute(ctx);
	}

}
