package cn.hang.mvc.result;

import cn.hang.mvc.RequestContext;

/**
 * ���ؽ�������磺��η���json�����jsonʹ��ģ�����ԣ����ڴ˽ӿڵ�ʵ���н�����Ⱦ�������html��ֱ����ת�ȡ�
 * 
 * @author Hang
 *
 */
public interface ResultTypeHandler {

	/**
	 * ִ����ת֮ǰ�Ĳ����Լ���ת
	 * 
	 */
	boolean handleResult(RequestContext requestContext, String path);
}
