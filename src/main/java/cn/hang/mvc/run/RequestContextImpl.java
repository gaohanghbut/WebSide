package cn.hang.mvc.run;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import java.util.concurrent.ConcurrentHashMap;
import cn.hang.mvc.RequestContext;

/**
 * 如果没有httpservletrequest和httpservletresponse对象，则使用此上下文实现
 * 
 * @author GaoHang
 * 
 */
public class RequestContextImpl extends AbstractRequestContext {
	
	/**
	 * 父上下文
	 */
	private RequestContext parent;
	
	/**
	 * 存储上下文件信息的Map
	 */
	private Map<String, Object> context = new ConcurrentHashMap<String, Object>();
	
	public RequestContextImpl(RequestContext parent) {
		this.parent = parent;
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
		return context.containsKey(key) || parent.contains(key);
	}

	@Override
	public String[] keys() {
		Set<String> set = context.keySet();
		String[] parentKeys = parent.keys();
		set.addAll(Arrays.asList(parentKeys));
		String[] keys = new String[set.size()];
		keys = set.toArray(keys);
		return keys;
	}

}
