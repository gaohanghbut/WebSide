package cn.hang.mvc.run;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.Assert;

import cn.hang.mvc.RequestContext;
import cn.hang.mvc.common.http.HttpInstance;
import cn.hang.mvc.common.util.RequestContextConstants;
import cn.hang.mvc.common.util.ServletUtils;
import cn.hang.mvc.common.util.StringUtils;
import cn.hang.mvc.exception.DirectException;
import cn.hang.mvc.http.HttpRequestManager;

/**
 * ���������ģ�������Action�н��в����Ļ�ȡ������<key,vale>�Եȣ�������ʹ��request������԰��������
 * 
 * @author GaoHang
 * 
 */
public class DefaultRequestContext extends AbstractRequestContext {
	
	/**
	 * http����
	 */
	private HttpServletRequest servletRequest;
	
	/**
	 * http��Ӧ
	 */
	private HttpServletResponse servletResponse;
	
	/**
	 * ��������
	 */
	private RequestContext parent;
	
	/**
	 * �洢�����ļ���Ϣ��Map
	 */
//	private Map<String, Object> context = new HashMap<String, Object>();
	
	/**
	 * �Ƿ��Ѿ���ת��
	 */
	private boolean returned;
	
	/**
	 * ��ǰ�����ģ����
	 */
	private String moduleName;
	
	/**
	 * ��ǰ�����actionǰ׺
	 */
	private String resource;
	
	/**
	 * ��ʼ��moduleNameʱ�ڴ˶���������
	 */
	private Object moduleLock = new Object();
	
	/**
	 * ��ʼ��actionPrefixʱ�ڴ˶���������
	 */
	private Object actionPrefixLock = new Object();
	
	private static final String SCREEN = "Screen";

	public DefaultRequestContext() {
		this(HttpRequestManager.DEFAULT_HTTP_REQUEST_MANAGER.getMvcHttpServletRequest(), HttpRequestManager.DEFAULT_HTTP_REQUEST_MANAGER.getMvcHttpServletResponse());
		this.servletRequest.setAttribute("requestContext", this);
	}
	
	public DefaultRequestContext(RequestContext parent) {
		this();
		this.parent = parent;
		this.servletRequest.setAttribute("requestContext", this);
	}

	public DefaultRequestContext(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		super();
		Assert.notNull(servletRequest, "HttpServletRequest cannot be null !");
		Assert.notNull(servletResponse, "HttpServletResponse cannot be null !");
		this.servletRequest = servletRequest;
		this.servletResponse = servletResponse;
		this.servletRequest.setAttribute("requestContext", this);
	}

	public DefaultRequestContext(HttpServletRequest servletRequest, HttpServletResponse servletResponse, RequestContext parent) {
		super();
		this.servletRequest = servletRequest;
		this.servletResponse = servletResponse;
		this.parent = parent;
		this.servletRequest.setAttribute("requestContext", this);
	}
	
	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}

	@Override
	public String getParameter(String paramName) {
		String value = servletRequest.getParameter(paramName);
//		if (StringUtils.isEmpty(value)) {
			return value;
//		}
		/*
		try {
			return new String(value.getBytes(HttpInstance.extractCharset(this)), HttpInstance.defaultCharset());
		} catch (UnsupportedEncodingException e) {
			return value;
		}*/
	}

	@Override
	public String getParameter(String paramName, String charset) {
		String value = servletRequest.getParameter(paramName);
		try {
			return new String(value.getBytes(HttpInstance.extractCharset(this)), charset);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("The charset is unsupported : " + charset);
		}
	}

	@Override
	public String getParameter(String paramName, Charset charset) {
		String value = servletRequest.getParameter(paramName);
		try {
			return new String(value.getBytes(HttpInstance.extractCharset(this)), charset);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("The charset is unsupported : " + charset);
		}
	}

	@Override
	public String[] getParameterValues(String paramName) {
		return servletRequest.getParameterValues(paramName);
	}

	@Override
	public Object getValue(String key) {
		Object object;
		//���request,session��application��Χ�ڵ�����
		object = servletRequest.getAttribute(key);
		if (object == null) {
			HttpSession session = servletRequest.getSession();
			object = servletRequest.getSession().getAttribute(key);
			if (object == null) {
				object = session.getServletContext().getAttribute(key);
				//�ڸ��������в���
				if (object == null && parent != null) {
					object = parent.getValue(key);
				}
			}
		}
		if (object == null) {
			object = getParameter(key);
		}
		return object;
	}

	@Override
	public void putValue(String key, Object value) {
		servletRequest.setAttribute(key, value); 
	}

	@Override
	public String getURI() {
		return servletRequest.getRequestURI();
	}

	@Override
	public void forward(String path) {
		try {
			servletRequest.getRequestDispatcher(path).forward(servletRequest, servletResponse);
			returned = true;
		} catch (ServletException e) {
			throw new DirectException(e);
		} catch (IOException e) {
			throw new DirectException(e);
		}
	}

	@Override
	public void redirect(String path) {
		try {
			servletResponse.sendRedirect(path);
			returned = true;
		} catch (IOException e) {
			throw new DirectException(e);
		}
	}

	@Override
	public int getIntParameter(String param) {
		String value = servletRequest.getParameter(param);
		if (StringUtils.isEmpty(value)) {
			return 0;
		}
		return Integer.parseInt(value);
	}

	@Override
	public boolean getBooleanParameter(String param) {
		String value = servletRequest.getParameter(param);
		if (StringUtils.isEmpty(value)) {
			return false;
		}
		return Boolean.parseBoolean(value);
	}

	@Override
	public long getLongParameter(String param) {
		String value = servletRequest.getParameter(param);
		if (StringUtils.isEmpty(value)) {
			return 0L;
		}
		return Long.parseLong(value);
	}

	@Override
	public short getShortParameter(String param) {
		String value = servletRequest.getParameter(param);
		if (StringUtils.isEmpty(value)) {
			return 0;
		}
		return Short.parseShort(value);
	}

//	@Override
//	public <T> T getEntiry(Class<T> c) {
//		try {
//			T t = c.newInstance();
//			Enumeration<?> en = servletRequest.getParameterNames();
//			while (en.hasMoreElements()) {
//				String param = (String) en.nextElement();
//				InvocationUtils.invokeSetter(t, param, getParameter(param));
//			}
//			return t;
//		} catch (InstantiationException e) {
//			throw new DynamicInvocationException(e);
//		} catch (IllegalAccessException e) {
//			throw new DynamicInvocationException(e);
//		}
//	}

	@Override
	public String getURL() {
		return servletRequest.getRequestURL().toString();
	}

	@Override
	public RequestContext getParent() {
		return parent;
	}

	@Override
	public Object removeValue(String key) {
		Object value = servletRequest.getAttribute(key);
		servletRequest.removeAttribute(key);
		return value;
	}

	@Override
	public boolean hasReturned() {
		return returned;
	}

	@Override
    public void expireForward() {
	    returned = true;
    }

    @Override
	public String getRequestModuleName() {
		if (moduleName == null) {
			synchronized (moduleLock) {
				if (moduleName == null) {
					String uri = this.getURI();
					if (uri.equals(ServletUtils.getBaseUri())) {
						return "";
					}
					int sufix_start = uri.lastIndexOf('.');
					if (sufix_start < 0) {
						sufix_start = uri.length();
					}
					moduleName = uri.substring(ServletUtils.getBaseUri().length(), sufix_start);
				}
			}
		}
		return moduleName;
	}

	@Override
	public String getResource() {
		if (resource == null) {
			synchronized (actionPrefixLock) {
				if (resource == null) {
					String action = this.getParameter(RequestContextConstants.RESOURCE_PARAMETER_NAME);
					if (StringUtils.isEmpty(action)) {
						return null;
					}
					if (action.endsWith(RequestContextConstants.ACTION_NAME_SUFIX)) {
						int index = action.lastIndexOf(RequestContextConstants.ACTION_NAME_SUFIX);
						if (index < 0) {
							resource = action;
						} else {
							resource = action.substring(0, index);
						}
					} else {
						resource = action;
					}
				}
			}
		}
		return resource;
	}

	@Override
	public String getScreenName() {
		String resource = getResource();
		return resource + SCREEN;
	}

	@Override
	public boolean contains(String key) {
		if (parent == null) {
			return servletRequest.getAttribute(key) != null;
		}
		return servletRequest.getAttribute(key) != null || parent.contains(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String[] keys() {
		Set<String> set = new HashSet<String>();
		set.addAll(Collections.list(servletRequest.getAttributeNames()));
		if (parent != null) {
			String[] parentKeys = parent.keys();
			set.addAll(Arrays.asList(parentKeys));
		}
		String[] keys = new String[set.size()];
		keys = set.toArray(keys);
		return keys;
	}

	@Override
	public HttpServletRequest getHttpServletRequest() {
		return servletRequest;
	}

	@Override
	public HttpServletResponse getHttpServletResponse() {
		return servletResponse;
	}

	@Override
	public String getActionName() {
		String action = getParameter(RequestContextConstants.ACTION_PARAMETER_NAME);
		//���action����Ϊ�գ���ʹ��resource����
		if (StringUtils.isEmpty(action)) {
			action = getParameter(RequestContextConstants.RESOURCE_PARAMETER_NAME);
		}
		if (StringUtils.isEmpty(action)) {
			return null;
		}
		if (action.endsWith(RequestContextConstants.ACTION_NAME_SUFIX)) {
			return action;
		}
		return action + RequestContextConstants.ACTION_NAME_SUFIX;
	}

	/**
	 * @param returned the returned to set
	 */
	public void setReturned(boolean returned) {
		this.returned = returned;
	}

	@Override
	public Map<String, String> getParameterMap() {
		Map<String, String> paramMap = new HashMap<String, String>();
		@SuppressWarnings("unchecked")
		Enumeration<String> enumeration = servletRequest.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String name = (String) enumeration.nextElement();
			paramMap.put(name, this.getParameter(name));
		}
		return paramMap;
	}

}
