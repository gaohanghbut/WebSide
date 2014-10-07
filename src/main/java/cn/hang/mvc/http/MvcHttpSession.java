package cn.hang.mvc.http;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * 对Session进行包装
 * 
 * @author GaoHang
 * 
 */
@SuppressWarnings("deprecation")
public class MvcHttpSession implements HttpSession {
	
	/**
	 * 用于保存各线程间的Session数据
	 */
	private ThreadLocal<HttpSession> sessionThreadLocal = new ThreadLocal<HttpSession>();

	/**
	 * 设置当前线程的HttpSession
	 * 
	 * @param session
	 */
	public void setSession(HttpSession session) {
		sessionThreadLocal.set(session);
	}
	
	public HttpSession removeSession() {
		HttpSession session = sessionThreadLocal.get();
		sessionThreadLocal.set(null);
		return session;
	}
	
	@Override
	public Object getAttribute(String name) {
		HttpSession session = sessionThreadLocal.get();
		if (session != null) {
			return session.getAttribute(name);
		}
		return null;
	}

	@Override
	public Enumeration<?> getAttributeNames() {
		return sessionThreadLocal.get().getAttributeNames();
	}

	@Override
	public long getCreationTime() {
		return sessionThreadLocal.get().getCreationTime();
	}

	@Override
	public String getId() {
		return sessionThreadLocal.get().getId();
	}

	@Override
	public long getLastAccessedTime() {
		return sessionThreadLocal.get().getLastAccessedTime();
	}

	@Override
	public int getMaxInactiveInterval() {
		return sessionThreadLocal.get().getMaxInactiveInterval();
	}

	@Override
	public ServletContext getServletContext() {
		return sessionThreadLocal.get().getServletContext();
	}

	@Override
	@Deprecated
	public HttpSessionContext getSessionContext() {
		return sessionThreadLocal.get().getSessionContext();
	}

	@Override
	@Deprecated
	public Object getValue(String name) {
		return sessionThreadLocal.get().getValue(name);
	}

	@Override
	public String[] getValueNames() {
		return sessionThreadLocal.get().getValueNames();
	}

	@Override
	public void invalidate() {
		sessionThreadLocal.get().invalidate();
	}

	@Override
	public boolean isNew() {
		return sessionThreadLocal.get().isNew();
	}

	@Override
	@Deprecated
	public void putValue(String name, Object value) {
		sessionThreadLocal.get().putValue(name, value);
	}

	@Override
	public void removeAttribute(String name) {
		sessionThreadLocal.get().removeAttribute(name);
	}

	@Override
	@Deprecated
	public void removeValue(String name) {
		sessionThreadLocal.get().removeValue(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
		sessionThreadLocal.get().setAttribute(name, value);
	}

	@Override
	public void setMaxInactiveInterval(int time) {
		sessionThreadLocal.get().setMaxInactiveInterval(time);
	}

}
