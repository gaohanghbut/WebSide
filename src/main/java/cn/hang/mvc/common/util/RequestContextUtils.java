package cn.hang.mvc.common.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cn.hang.mvc.RequestContextFactory;

/**
 * 请求上下文工具类
 * 
 * @author Hang
 *
 */
public abstract class RequestContextUtils {

	/**
	 * 存放请求上下文工厂的列表，其中只有一个元素
	 */
	private static List<RequestContextFactory> REQUEST_CONTEXT_FACTORIES = new LinkedList<RequestContextFactory>();
	
	/**
	 * 注册请求上下文工厂，注册后工厂将不能修改
	 * 
	 * @param requestContextFactory
	 */
	public static void registerRequestContextFactory(RequestContextFactory requestContextFactory) {
		if (REQUEST_CONTEXT_FACTORIES.size() == 0) {
			synchronized (REQUEST_CONTEXT_FACTORIES) {
				if (REQUEST_CONTEXT_FACTORIES.isEmpty()) {
					REQUEST_CONTEXT_FACTORIES.add(requestContextFactory);
					REQUEST_CONTEXT_FACTORIES = Collections.unmodifiableList(REQUEST_CONTEXT_FACTORIES);
				}
			}
		}
	}
	
	/**
	 * 获取请求上下文工厂
	 * 
	 * @return
	 */
	public static RequestContextFactory getRequestContextFactory() {
		if (REQUEST_CONTEXT_FACTORIES.isEmpty()) {
			return null;
		}
		return REQUEST_CONTEXT_FACTORIES.get(0);
	}
}
