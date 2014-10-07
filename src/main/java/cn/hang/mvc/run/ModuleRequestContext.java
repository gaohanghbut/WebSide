package cn.hang.mvc.run;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.ServletContextResource;

import cn.hang.mvc.RequestContext;
import cn.hang.mvc.common.util.ServletUtils;

/**
 * ģ�����������ģ�������Action�н��в����Ļ�ȡ������<key,vale>�Ե�
 * 
 * @author GaoHang
 * 
 */
public class ModuleRequestContext extends AbstractRequestContext {
	
	/**
	 * ��־��¼��
	 */
	private final Log log = LogFactory.getLog(ModuleRequestContext.class);
	
	/**
	 * ��������
	 */
	private RequestContext parent;
	
	/**
	 * �洢�����ļ���Ϣ��Map
	 */
	private Map<String, Object> context = new ConcurrentHashMap<String, Object>();
	
	public ModuleRequestContext(RequestContext parent, String moduleName) {
		this.parent = parent;
		
		StringBuilder sb = new StringBuilder("/WEB-INF/");
		sb.append(moduleName);
		sb.append('/');
		sb.append(moduleName);
		sb.append(".properties");
		
		Resource resource = new ServletContextResource(ServletUtils.getServletContext(), sb.toString());
		if (resource.exists() && resource.isReadable()) {
			InputStream in = null;
			java.util.Properties properties = new java.util.Properties();
			try {
				properties.load(in = resource.getInputStream());
				for (Map.Entry<Object, Object> e : properties.entrySet()) {
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
		if (object == null) {
			//�ڸ��������в���
			if (object == null && parent != null) {
				object = parent.getValue(key);
			}
		}
		return object;
	}

	@Override
	public void putValue(String key, Object value) {
		context.put(key, value);
	}

	@Override
	public RequestContext getParent() {
		return parent;
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
