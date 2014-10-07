package cn.hang.mvc.common.util;

import cn.hang.mvc.http.HttpRequestManager;

/**
 * ��ȡHttpRequestManagerʵ���Ĺ���
 * 
 * @author Hang
 *
 */
public abstract class HttpRequestManagers {

	/**
	 * ��ȡHttpRequestManagerʵ��
	 * 
	 * @return
	 */
	public static HttpRequestManager getHttpRequestManager() {
		return HttpRequestManager.DEFAULT_HTTP_REQUEST_MANAGER;
	}
}
