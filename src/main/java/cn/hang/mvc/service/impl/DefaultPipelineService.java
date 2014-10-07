package cn.hang.mvc.service.impl;

import org.springframework.util.Assert;

import cn.hang.mvc.RequestContext;
import cn.hang.mvc.common.util.ApplicationContextUtils;
import cn.hang.mvc.pipeline.Pipeline;
import cn.hang.mvc.service.PipelineService;
import cn.hang.mvc.spring.context.PipelineApplicationContext;
import cn.hang.mvc.spring.holder.ApplicationContextHolder;

/**
 * Ĭ�Ϲܵ�����ʵ�֣���Ҫ���ڼ���ÿ��ģ���pipeline.xml�ļ���Ȼ��ͨ��pipeline.xml�ļ��е�����
 * ��װ�ܵ�
 * 
 * @author GaoHang
 *
 */
public class DefaultPipelineService implements PipelineService {

	/**
	 * ͨ��Spring�����Ĺܵ�
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
	 * �ڹܵ�������ʹ�õĹܵ�������
	 * @author Hang
	 *
	 */
	private class PipelineContext implements cn.hang.mvc.pipeline.PipelineContext {
		
		private RequestContext ctx;
		
		/**
		 * ���ڱ�ǵ�ǰ�����Ƿ��Ѿ�����
		 */
		private boolean returned = false;
		
		/**
		 * action���õĽ��
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
