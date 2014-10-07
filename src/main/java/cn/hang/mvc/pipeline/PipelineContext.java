package cn.hang.mvc.pipeline;

import cn.hang.mvc.RequestContext;

/**
 * ����ִ��ʱ��������
 * 
 * @author Hang
 * 
 */
public interface PipelineContext {

	/**
	 * ��ȡһ�����������������
	 * 
	 * @return
	 */
	public RequestContext getRequestContext();

	/**
	 * �Ƿ��Ѿ��������ݸ��ͻ���
	 * 
	 * @return
	 */
	public boolean hasReturn();

	/**
	 * �洢Action���õķ��ؽ��
	 * 
	 * @param result
	 *            action���õķ��ؽ��
	 */
	public void storeActionInvokeResult(Object result);
	
	/**
	 * ��ȡAction���õĽ��
	 * 
	 * @return action�ĵ��ý��
	 */
	public Object getActionInvokeResult();
}
