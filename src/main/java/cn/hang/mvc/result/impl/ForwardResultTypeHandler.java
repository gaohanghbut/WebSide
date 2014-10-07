package cn.hang.mvc.result.impl;

import cn.hang.mvc.RequestContext;
import cn.hang.mvc.result.ResultTypeHandler;

/**
 * ���ؽ���������Ļ���ʵ�֣��������ֱ����ת��Ŀ������
 * 
 * @author Hang
 *
 */
public class ForwardResultTypeHandler implements ResultTypeHandler {
	
	public ForwardResultTypeHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean handleResult(RequestContext requestContext, String path) {
		try {
			requestContext.forward(path);
			return true;
		} catch (Exception e) {
			//��תʧ��
			return false;
		}
	}

}
