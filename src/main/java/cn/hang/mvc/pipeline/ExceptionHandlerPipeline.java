package cn.hang.mvc.pipeline;

import cn.hang.mvc.RequestContext;
import cn.hang.mvc.RunData;

/**
 * �쳣����ܵ�
 * 
 * @author GaoHang
 * 
 */
public interface ExceptionHandlerPipeline extends Pipeline {

	/**
	 * �����쳣�ķ���
	 * 
	 * @param runData
	 * @param t
	 *            �������쳣
	 * @return ��ȷ������true
	 * @deprecated ��Ϊʹ�� {@link #handler(RequestContext,Throwable)} 
	 */
	boolean handler(RunData runData, Throwable t);

	/**
	 * �����쳣�ķ���
	 * 
	 * @param ctx
	 * @param t
	 *            �������쳣
	 * @return ��ȷ������true
	 */
	boolean handler(PipelineContext ctx, Throwable t);
}
