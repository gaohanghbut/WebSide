package cn.hang.mvc.service.impl;

import org.springframework.util.Assert;

import cn.hang.mvc.RequestContext;
import cn.hang.mvc.common.util.ApplicationContextUtils;
import cn.hang.mvc.pipeline.Pipeline;
import cn.hang.mvc.service.PipelineService;
import cn.hang.mvc.spring.context.PipelineApplicationContext;
import cn.hang.mvc.spring.holder.ApplicationContextHolder;

/**
 * 默认管道服务实现，主要用于加载每个模块的pipeline.xml文件，然后通过pipeline.xml文件中的配置
 * 组装管道
 * 
 * @author GaoHang
 *
 */
public class DefaultPipelineService implements PipelineService {

	/**
	 * 通过Spring建立的管道
	 */
	private Pipeline pipeline;

	@Override
	public void service() {
		PipelineApplicationContext pipelineApplicationContext = new PipelineApplicationContext();
		ApplicationContextUtils.getApplicationContextHolder().set(ApplicationContextHolder.PIPELINE_CONTEXT, pipelineApplicationContext);
		pipeline = pipelineApplicationContext.getBean(Pipeline.class);
	}

	@Override
	public Pipeline loadPipeline(String pipelineId, String[] valves) {
		return null;
	}

	@Override
	public Pipeline getPipeline(String pipelineId) {
		return null;
	}

	@Override
	public boolean invokePipeline(RequestContext ctx) {
		if (pipeline == null) {
			return false;
		}
		return pipeline.handler(new PipelineContext(ctx));
	}

	/**
	 * 在管道服务中使用的管道上下文
	 * @author Hang
	 *
	 */
	private class PipelineContext implements cn.hang.mvc.pipeline.PipelineContext {
		
		private RequestContext ctx;
		
		/**
		 * 用于标记当前请求是否已经返回
		 */
		private boolean returned = false;
		
		/**
		 * action调用的结果
		 */
		private Object actionInvokeResult;
		
		public PipelineContext(RequestContext ctx) {
			Assert.notNull(ctx, "Request Context cannot be null");
			this.ctx = ctx;
		}

		@Override
		public RequestContext getRequestContext() {
			return ctx;
		}

		@Override
		public boolean hasReturn() {
			if (ctx.hasReturned()) {
				return true;
			}
			return returned;
		}

		@Override
		public void storeActionInvokeResult(Object result) {
			this.actionInvokeResult = result;
		}

		@Override
		public Object getActionInvokeResult() {
			return actionInvokeResult;
		}
	}
}
