package cn.hang.mvc.http;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ���������
 * 
 * @author GaoHang
 * 
 */
public interface HttpRequestManager {
	
	/**
	 * Ĭ�ϵ�HttpRequestManager
	 */
	static final HttpRequestManager DEFAULT_HTTP_REQUEST_MANAGER = new DefaultHttpRequestManager();
	
	/**
	 * ��HttpRequestManager���г�ʼ��
	 */
	void init();
	
	/**
	 * �������HttpServletRequest��HttpServletResponse��HttpSession���а�װ
	 * 
	 * @param request
	 * @param response
	 */
	void wrapRequest(ServletRequest request, ServletResponse response);

	/**
	 * ���������԰�װ�����ݽ�������
	 */
	void cleanRequest();

	/**
	 * ��ȡ����
	 * 
	 * @return
	 */
	public MvcHttpServletRequest getMvcHttpServletRequest();

	/**
	 * ��ȡ��Ӧ
	 * 
	 * @return
	 */
	public MvcHttpServletResponse getMvcHttpServletResponse();

	/**
	 * ��ȡ�Ự
	 * 
	 * @return
	 */
	public MvcHttpSession getMvcHttpSession();

	/**
	 * HttpRequestManager��Ĭ��ʵ��
	 * 
	 * @author GaoHang
	 * 
	 */
	static class DefaultHttpRequestManager implements HttpRequestManager {

		/**
		 * ��װ�����ʵ�������ڰ�װ����
		 */
		private MvcHttpServletRequest mvcHttpServletRequest;

		/**
		 * ��װ�����ʵ�������ڰ�װ��Ӧ
		 */
		private MvcHttpServletResponse mvcHttpServletResponse;

		/**
		 * ��װ���࣬���ڰ�װ�Ự
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
