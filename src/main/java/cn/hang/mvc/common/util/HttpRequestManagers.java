package cn.hang.mvc.common.util;

import cn.hang.mvc.http.HttpRequestManager;

/**
 * 获取HttpRequestManager实例的工厂
 * 
 * @author Hang
 *
 */
public abstract class HttpRequestManagers {

	/**
	 * 获取HttpRequestManager实例
	 * 
	 * @return
	 */
	public static HttpRequestManager getHttpRequestManager() {
		return HttpRequestManager.DEFAULT_HTTP_REQUEST_MANAGER;
	}
}
