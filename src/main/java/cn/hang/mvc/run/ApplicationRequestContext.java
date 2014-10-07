package cn.hang.mvc.run;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.ServletContextResource;

import cn.hang.mvc.RequestContext;
import cn.hang.mvc.common.util.ServletUtils;

/**
 * Ӧ�����������ģ�������Action�н��в����Ļ�ȡ������<key,vale>�Ե�
 * 
 * @author GaoHang
 * 
 */
public class ApplicationRequestContext extends AbstractRequestContext {
	
	/**
	 * �洢�����ļ���Ϣ��Map
	 */
	private Map<String, Object> context = new ConcurrentHashMap<String, Object>();
	
	/**
	 * ��¼��־
	 */
	private final Log log = LogFactory.getLog(ApplicationRequestContext.class);
	
	/**
	 * application���������ĳ�ʼ�����ݵ������ļ�
	 */
	private static final String APPLICATION_RESOURCES = "/WEB-INF/application.properties";
	
	public ApplicationRequestContext() {
		Resource resource = new ServletContextResource(ServletUtils.getServletContext(), APPLICATION_RESOURCES);
		if (resource.exists() && resource.isReadable()) {//�ļ��������ȡ�����򲻶�ȡ
			InputStream in = null;
			try {
				Properties properties = new Properties();
				properties.load(in = resource.getInputStream());
				for (Entry<Object, Object> e : properties.entrySet()) {
					context.put(e.getKey().toString(), e.getValue().toString());
				}
			} catch (IOException e) {
				log.info(e.getMessage(), e);
				context.clear();
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						log.error(e.getMessage(), e);
					}
				}
			}
		}
	}

	@Override
	public Object getValue(String key) {
		Object object = context.get(key);
		return object;
	}

	@Override
	public void putValue(String key, Object value) {
		context.put(key, value);
	}

	@Override
	public RequestContext getParent() {
		return null;
	}

	@Override
	public Object removeValue(String key) {
		return context.remove(key);
	}

	@Override
	public boolean contains(String key) {
		return context.containsKey(key);
	}

	@Override
	public String[] keys() {
		String[] keys = new String[context.size()];
		keys = context.keySet().toArray(keys());
		return keys;
	}

}
