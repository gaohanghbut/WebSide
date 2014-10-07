package cn.hang.mvc.result.impl;

import cn.hang.mvc.RequestContext;
import cn.hang.mvc.result.ResultTypeHandler;

/**
 * ���ؽ���������Ļ���ʵ�֣��������ֱ���ض���
 * 
 * @author Hang
 *
 */
public class RedirectResultTypeHandler implements ResultTypeHandler {

	@Override
	public boolean handleResult(RequestContext requestContext, String path) {
		try {
			requestContext.redirect(path);
			return true;
		} catch (Exception e) {
			//�ض���ʧ��
			return false;
		}
	}

}
