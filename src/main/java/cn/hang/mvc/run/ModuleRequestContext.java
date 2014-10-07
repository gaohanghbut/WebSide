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
 * 模块请求上下文，用于在Action中进行参数的获取，设置<key,vale>对等
 * 
 * @author GaoHang
 * 
 */
public class ModuleRequestContext extends AbstractRequestContext {
	
	/**
	 * 日志记录器
	 */
	private final Log log = LogFactory.getLog(ModuleRequestContext.class);
	
	/**
	 * 父上下文
	 */
	private RequestContext parent;
	
	/**
	 * 存储上下文件信息的Map
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
			//在父上下文中查找
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
