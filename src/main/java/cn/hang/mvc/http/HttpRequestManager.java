package cn.hang.mvc.http;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求管理器
 * 
 * @author GaoHang
 * 
 */
public interface HttpRequestManager {
	
	/**
	 * 默认的HttpRequestManager
	 */
	static final HttpRequestManager DEFAULT_HTTP_REQUEST_MANAGER = new DefaultHttpRequestManager();
	
	/**
	 * 对HttpRequestManager进行初始化
	 */
	void init();
	
	/**
	 * 对请求的HttpServletRequest，HttpServletResponse和HttpSession进行包装
	 * 
	 * @param request
	 * @param response
	 */
	void wrapRequest(ServletRequest request, ServletResponse response);

	/**
	 * 请求结束后对包装的数据进行清理
	 */
	void cleanRequest();

	/**
	 * 获取请求
	 * 
	 * @return
	 */
	public MvcHttpServletRequest getMvcHttpServletRequest();

	/**
	 * 获取响应
	 * 
	 * @return
	 */
	public MvcHttpServletResponse getMvcHttpServletResponse();

	/**
	 * 获取会话
	 * 
	 * @return
	 */
	public MvcHttpSession getMvcHttpSession();

	/**
	 * HttpRequestManager的默认实现
	 * 
	 * @author GaoHang
	 * 
	 */
	static class DefaultHttpRequestManager implements HttpRequestManager {

		/**
		 * 包装器类的实例，用于包装请求
		 */
		private MvcHttpServletRequest mvcHttpServletRequest;

		/**
		 * 包装器类的实例，用于包装响应
		 */
		private MvcHttpServletResponse mvcHttpServletResponse;

		/**
		 * 包装器类，用于包装会话
		 */
		private MvcHttpSession mvcHttpSession;

		protected DefaultHttpRequestManager() {
		}

		@Override
		public void wrapRequest(ServletRequest request, ServletResponse response) {
			mvcHttpServletRequest.setRequest((HttpServletRequest) request);
			mvcHttpServletResponse.setResponse((HttpServletResponse) response);
			mvcHttpSession.setSession(((HttpServletRequest) request).getSession());
		}

		@Override
		public void cleanRequest() {
			mvcHttpServletRequest.removeRequest();
			mvcHttpServletResponse.removeResponse();
			mvcHttpSession.removeSession();
		}

		public MvcHttpServletRequest getMvcHttpServletRequest() {
			return mvcHttpServletRequest;
		}

		public MvcHttpServletResponse getMvcHttpServletResponse() {
			return mvcHttpServletResponse;
		}

		public MvcHttpSession getMvcHttpSession() {
			return mvcHttpSession;
		}

		@Override
		public void init() {
			mvcHttpServletRequest = new MvcHttpServletRequest();
			mvcHttpServletResponse = new MvcHttpServletResponse();
			mvcHttpSession = new MvcHttpSession();
		}

	}
}
