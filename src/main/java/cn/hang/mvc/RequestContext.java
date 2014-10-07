package cn.hang.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求上下文，分为三类，Application RequestContext表示对应用的请求，整个应用中只有一个，
 * 可用于保存某些公有数据；Module RequestContext表示对模块的请求，一个模块只有一个；Request
 * 范围的RequestContext表示一次HTTP请求对应的上下文，一次请求对应一个。
 * 
 * @author Hang
 *
 */
public interface RequestContext extends RunData {

    @Deprecated
	Object getValue(String key);
	
	/**
	 * 根据key获取value，先在当前对象中获取value，如果没有则在其父上下文中查找，然后在request,session,application中查找，直到找到。
	 * 
	 * @param key
	 * @return
	 */
	Object get(String key);

	@Deprecated
	void putValue(String key, Object value);
	
	/**
	 * 设置对象的值，设置的对象不受session和request,application的属性范围管理
	 * 
	 * @param key
	 * @param value
	 */
	Object put(String key, Object value);
	
	/**
	 * 返回父RequestContext，Application RequestContext的父RequestContext为空
	 * Module RequestContext的父RequestContext是Application RequestContext,
	 * Request范围的RequestContext的父RequestContext是Module RequestContext
	 * 
	 * @return
	 */
	RequestContext getParent();
	
	/**
	 * 从上下文中删除对象，约定只能删除Request范围的上下文中的对象
	 * 
	 * @param key
	 * @return
	 */
	Object removeValue(String key);
	
	/**
	 * 是否包含key
	 * 
	 * @param key
	 * @return
	 */
	boolean contains(String key);
	
	/**
	 * 返回key的数组
	 * 
	 * @return
	 */
	String[] keys();

	/**
	 * 重定向
	 */
//	void redirect(String url);
	/**
	 * 返回当前请求的HttpServletRequest对象，如果当前RequestContext对象是Module或者Application请求上下文，则返回空
	 * 
	 * @return
	 */
	HttpServletRequest getHttpServletRequest();
	
	/**
	 * 返回当前请求的HttpServletResponse对象，如果当前RequestContext对象是Module或者Application请求上下文，则返回空
	 * 
	 * @return
	 */
	HttpServletResponse getHttpServletResponse();

	/**
	 * 将状态设置成已经返回
	 */
	void expireForward();
}
