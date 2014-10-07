package cn.hang.mvc.run;

import java.nio.charset.Charset;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Hang
 *
 */
public abstract class AbstractRequestContext implements cn.hang.mvc.RequestContext {

	@Override
    public void expireForward() {
    }

    @Override
	public Map<String, String> getParameterMap() {
		return null;
	}

	@Override
	public String getParameter(String paramName, String charset) {
		return null;
	}

	@Override
	public String getParameter(String paramName, Charset charset) {
		return null;
	}

	@Override
	public String getParameter(String paramName) {
		return null;
	}

	@Override
	public String[] getParameterValues(String paramName) {
		return null;
	}

	@Override
	public String getURI() {
		return null;
	}

	@Override
	public void forward(String path) {
	}

	@Override
	public int getIntParameter(String param) {
		return 0;
	}

	@Override
	public boolean getBooleanParameter(String param) {
		return false;
	}

	@Override
	public long getLongParameter(String param) {
		return 0;
	}

	@Override
	public short getShortParameter(String param) {
		return 0;
	}

	@Override
	@Deprecated
	public <T> T getEntiry(Class<T> c) {
		return null;
	}

	@Override
	public String getURL() {
		return null;
	}

	@Override
	public boolean hasReturned() {
		return false;
	}

	@Override
	public String getRequestModuleName() {
		return null;
	}

	@Override
	public String getResource() {
		return null;
	}

	@Override
	public String getScreenName() {
		return null;
	}

	@Override
	public void redirect(String url) {
	}

	@Override
	public HttpServletRequest getHttpServletRequest() {
		return null;
	}

	@Override
	public HttpServletResponse getHttpServletResponse() {
		return null;
	}

	@Override
	public String getActionName() {
		return null;
	}

    @SuppressWarnings("deprecation")
    @Override
    public Object get(String key) {
        return getValue(key);
    }

    @SuppressWarnings("deprecation")
    @Override
    public Object put(String key, Object value) {
        putValue(key, value);
        return value;
    }
	
}