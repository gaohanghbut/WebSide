package cn.hang.mvc.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * 对HttpServletResponse进行包装，使之能够成为单例
 * 
 * @author GaoHang
 * 
 */
public class MvcHttpServletResponse implements HttpServletResponse {
	
	/**
	 * 用于存储httpServletResponse的ThreadLocal
	 */
	private ThreadLocal<HttpServletResponse> httpServletResponseThreadLocal = new ThreadLocal<HttpServletResponse>();
	
	/**
	 * 设置当前线程的HttpServletResponse对象
	 * 
	 * @param response
	 */
	public void setResponse(HttpServletResponse response) {
		httpServletResponseThreadLocal.set(response);
	}
	
	/**
	 * 删除当前线程的HttpServletResponse
	 * 
	 * @return
	 */
	public HttpServletResponse removeResponse() {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		httpServletResponseThreadLocal.set(null);
		return response;
	}

	@Override
	public void flushBuffer() throws IOException {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.flushBuffer();
		}
	}

	@Override
	public int getBufferSize() {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			return response.getBufferSize();
		}
		return 0;
	}

	@Override
	public String getCharacterEncoding() {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.getCharacterEncoding();
		}
		return null;
	}

	@Override
	public String getContentType() {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.getContentType();
		}
		return null;
	}

	@Override
	public Locale getLocale() {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.getLocale();
		}
		return null;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.getOutputStream();
		}
		return null;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.getWriter();
		}
		return null;
	}

	@Override
	public boolean isCommitted() {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.isCommitted();
		}
		return false;
	}

	@Override
	public void reset() {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.reset();
		}
	}

	@Override
	public void resetBuffer() {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.resetBuffer();
		}
	}

	@Override
	public void setBufferSize(int size) {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.setBufferSize(size);
		}
	}

	@Override
	public void setCharacterEncoding(String characterEncoding) {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.setCharacterEncoding(characterEncoding);
		}
	}

	@Override
	public void setContentLength(int length) {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.setContentLength(length);
		}
	}

	@Override
	public void setContentType(String contentType) {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.setContentType(contentType);
		}
	}

	@Override
	public void setLocale(Locale locale) {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.setLocale(locale);
		}
	}

	@Override
	public void addCookie(Cookie cookie) {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.addCookie(cookie);
		}
	}

	@Override
	public void addDateHeader(String name, long time) {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.addDateHeader(name, time);
		}
	}

	@Override
	public void addHeader(String name, String value) {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.addHeader(name, value);
		}
	}

	@Override
	public void addIntHeader(String name, int value) {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.addIntHeader(name, value);
		}
	}

	@Override
	public boolean containsHeader(String name) {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			return response.containsHeader(name);
		}
		return false;
	}

	@Override
	public String encodeRedirectURL(String url) {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			return response.encodeRedirectURL(url);
		}
		return null;
	}

	@Override
	@Deprecated
	public String encodeRedirectUrl(String url) {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			return response.encodeRedirectUrl(url);
		}
		return null;
	}

	@Override
	public String encodeURL(String url) {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			return response.encodeURL(url);
		}
		return null;
	}

	@Override
	@Deprecated
	public String encodeUrl(String url) {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			return response.encodeUrl(url);
		}
		return null;
	}

	@Override
	public void sendError(int code) throws IOException {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.sendError(code);
		}
	}

	@Override
	public void sendError(int code, String msg) throws IOException {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.sendError(code, msg);
		}
	}

	@Override
	public void sendRedirect(String path) throws IOException {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.sendRedirect(path);
		}
	}

	@Override
	public void setDateHeader(String name, long value) {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.setDateHeader(name, value);
		}
	}

	@Override
	public void setHeader(String name, String value) {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.setHeader(name, value);
		}
	}

	@Override
	public void setIntHeader(String name, int value) {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.setIntHeader(name, value);
		}
	}

	@Override
	public void setStatus(int status) {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.setStatus(status);
		}
	}

	@Override
	@Deprecated
	public void setStatus(int status, String msg) {
		HttpServletResponse response = httpServletResponseThreadLocal.get();
		if (response != null) {
			response.setStatus(status, msg);
		}
	}

}
