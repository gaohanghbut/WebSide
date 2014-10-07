package cn.hang.mvc.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 对HttpServletRequest进行增强，此类可以为单例，支持注入到singleton范围的对象中
 * 
 * @author GaoHang
 * 
 */
public class MvcHttpServletRequest implements HttpServletRequest {
	
	/**
	 * 存储每个线程间的HttpServletRequest对象
	 */
	private ThreadLocal<HttpServletRequest> servletRequestThreadLocal = new ThreadLocal<HttpServletRequest>();
	
	private HttpRequestManager httpRequestManager = HttpRequestManager.DEFAULT_HTTP_REQUEST_MANAGER;

	/**
	 * 设置当前线程的真实HttpServletRequest
	 * 
	 * @param request
	 */
	public void setRequest(HttpServletRequest request) {
		servletRequestThreadLocal.set(request);
	}
	
	/**
	 * 删除当前线程的HttpServletRequest
	 * 
	 * @return
	 */
	public HttpServletRequest removeRequest() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		servletRequestThreadLocal.set(null);
		return request;
	}
	
	@Override
	public Object getAttribute(String name) {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request == null) {
			return null;
		}
		return request.getAttribute(name);
	}

	@Override
	public Enumeration<?> getAttributeNames() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getAttributeNames();
		}
		return null;
	}

	@Override
	public String getCharacterEncoding() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getCharacterEncoding();
		}
		return null;
	}

	@Override
	public int getContentLength() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getContentLength();
		}
		return 0;
	}

	@Override
	public String getContentType() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getContentType();
		}
		return null;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getInputStream();
		}
		return null;
	}

	@Override
	public String getLocalAddr() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getLocalAddr();
		}
		return null;
	}

	@Override
	public String getLocalName() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getLocalName();
		}
		return null;
	}

	@Override
	public int getLocalPort() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getLocalPort();
		}
		return 0;
	}

	@Override
	public Locale getLocale() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getLocale();
		}
		return null;
	}

	@Override
	public Enumeration<?> getLocales() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getLocales();
		}
		return null;
	}

	@Override
	public String getParameter(String name) {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getParameter(name);
		}
		return null;
	}

	@Override
	public Map<?, ?> getParameterMap() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getParameterMap();
		}
		return null;
	}

	@Override
	public Enumeration<?> getParameterNames() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getParameterNames();
		}
		return null;
	}

	@Override
	public String[] getParameterValues(String name) {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getParameterValues(name);
		}
		return new String[0];
	}

	@Override
	public String getProtocol() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getProtocol();
		}
		return null;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getReader();
		}
		return null;
	}

	@Override
	@Deprecated
	public String getRealPath(String path) {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getRealPath(path);
		}
		return null;
	}

	@Override
	public String getRemoteAddr() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getRemoteAddr();
		}
		return null;
	}

	@Override
	public String getRemoteHost() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getRemoteAddr();
		}
		return null;
	}

	@Override
	public int getRemotePort() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getRemotePort();
		}
		return 0;
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getRequestDispatcher(path);
		}
		return null;
	}

	@Override
	public String getScheme() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getScheme();
		}
		return null;
	}

	@Override
	public String getServerName() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getServerName();
		}
		return null;
	}

	@Override
	public int getServerPort() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getServerPort();
		}
		return 0;
	}

	@Override
	public boolean isSecure() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.isSecure();
		}
		return false;
	}

	@Override
	public void removeAttribute(String name) {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			request.removeAttribute(name);
		}
	}

	@Override
	public void setAttribute(String name, Object value) {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			request.setAttribute(name, value);
		}
	}

	@Override
	public void setCharacterEncoding(String encoding) throws UnsupportedEncodingException {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			request.setCharacterEncoding(encoding);
		}
	}

	@Override
	public String getAuthType() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getAuthType();
		}
		return null;
	}

	@Override
	public String getContextPath() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getContextPath();
		}
		return null;
	}

	@Override
	public Cookie[] getCookies() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getCookies();
		}
		return null;
	}

	@Override
	public long getDateHeader(String dateHeader) {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getDateHeader(dateHeader);
		}
		return 0;
	}

	@Override
	public String getHeader(String name) {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getHeader(name);
		}
		return null;
	}

	@Override
	public Enumeration<?> getHeaderNames() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getHeaderNames();
		}
		return null;
	}

	@Override
	public Enumeration<?> getHeaders(String name) {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getHeaders(name);
		}
		return null;
	}

	@Override
	public int getIntHeader(String name) {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getIntHeader(name);
		}
		return 0;
	}

	@Override
	public String getMethod() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getMethod();
		}
		return null;
	}

	@Override
	public String getPathInfo() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getPathInfo();
		}
		return null;
	}

	@Override
	public String getPathTranslated() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getPathTranslated();
		}
		return null;
	}

	@Override
	public String getQueryString() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getQueryString();
		}
		return null;
	}

	@Override
	public String getRemoteUser() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getRemoteUser();
		}
		return null;
	}

	@Override
	public String getRequestURI() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getRequestURI();
		}
		return null;
	}

	@Override
	public StringBuffer getRequestURL() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getRequestURL();
		}
		return null;
	}

	@Override
	public String getRequestedSessionId() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getRequestedSessionId();
		}
		return null;
	}

	@Override
	public String getServletPath() {
		HttpServletRequest request = servletRequestThreadLocal.get();
		if (request != null) {
			return request.getServletPath();
		}
		return null;
	}

	@Override
	public HttpSession getSession() {
		return httpRequestManager.getMvcHttpSession();
	}

	@Override
	public HttpSession getSession(boolean create) {
		HttpSession session = servletRequestThreadLocal.get().getSession(create);
		MvcHttpSession mvcHttpSession = httpRequestManager.getMvcHttpSession();
		mvcHttpSession.setSession(session);
		return mvcHttpSession;
	}

	@Override
	public Principal getUserPrincipal() {
		return servletRequestThreadLocal.get().getUserPrincipal();
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		return servletRequestThreadLocal.get().isRequestedSessionIdFromCookie();
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		return servletRequestThreadLocal.get().isRequestedSessionIdFromURL();
	}

	@Override
	@Deprecated
	public boolean isRequestedSessionIdFromUrl() {
		return servletRequestThreadLocal.get().isRequestedSessionIdFromUrl();
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		return servletRequestThreadLocal.get().isRequestedSessionIdValid();
	}

	@Override
	public boolean isUserInRole(String role) {
		return servletRequestThreadLocal.get().isUserInRole(role);
	}

}
