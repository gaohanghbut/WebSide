package cn.hang.mvc.pipeline.valve.impl;

import cn.hang.mvc.RequestContext;
import cn.hang.mvc.pipeline.ExceptionHandlerPipeline;
import cn.hang.mvc.pipeline.Pipeline;
import cn.hang.mvc.pipeline.PipelineContext;
import cn.hang.mvc.pipeline.valve.AbstractValve;
import cn.hang.mvc.pipeline.valve.TryCatchFinallyValve;

/**
 * ��Valve����ִ����Pipeline������������Pipeline���ֱ���Try��Catch��Finally��ִ��
 * 
 * @author GaoHang
 * 
 */
public class DefaultTryCatchFinallyValve extends AbstractValve implements TryCatchFinallyValve {
	
	/**
	 * ��try��ִ�е��ӹܵ�
	 */
	private Pipeline tryPipeline;
	
	/**
	 * �����쳣��ִ�еĹܵ�
	 */
	private ExceptionHandlerPipeline catchPipeline;
	
	/**
	 * ��������ʱִ�еĹܵ�
	 */
	private Pipeline finallyPipeline;
	
	@Override
	public void init() {
	}

	@Override
	public boolean execute(PipelineContext ctx) {
		try {
			if (tryPipeline != null) {
				return tryPipeline.handler(ctx);
			}
			return true;
		} catch (Exception e) {
			if (catchPipeline != null) {
				if (ctx.getRequestContext() instanceof RequestContext) {
					catchPipeline.handler(ctx, e);
				}
			}
			return true;
		} finally {
			if (finallyPipeline != null) {
				finallyPipeline.handler(ctx);
			}
		}
	}

	@Override
	public void setTryPipeline(Pipeline tryPipeline) {
		this.tryPipeline = tryPipeline;
	}

	@Override
	public void setCatchPipeline(ExceptionHandlerPipeline catchPipeline) {
		this.catchPipeline = catchPipeline;
	}

	@Override
	public void setFinallyPipeline(Pipeline finallyPipeline) {
		this.finallyPipeline = finallyPipeline;
	}
}
