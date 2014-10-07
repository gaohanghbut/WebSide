package cn.hang.mvc.service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hang.mvc.RequestContext;

/**
 * 用于管理不同scope的RunData，RunData有三种，一种是Application范围内的RunData，此RunData对象是单例，
 * 第二种是Request范围的RunData，此对象在同一次请求中是单例，第三种是Module RunData，此对象在同一个模块中是单例
 * 
 * @author GaoHang
 * 
 */
public interface RunDataService extends Service {

	/**
	 * 获取Application范围内的RunData
	 * 
	 * @return
	 * @deprecated 改为使用 {@link #getApplicationRunData()} 
	 */
//	RunData getApplicationRunData();

	/**
	 * 获取Application范围内的RunData
	 * 
	 * @return
	 */
	RequestContext getApplicationRunData();

	/**
	 * 获取Request范围内的RunData
	 * 
	 * @return
	 * @deprecated 改为使用 {@link #getRequestRunData(ServletRequest)} 
	 */
//	RunData getRequestRunData();

	/**
	 * 获取Request范围内的RunData
	 * @param req
	 * 
	 * @return
	 */
	RequestContext getRequestRunData(ServletRequest req);

	/**
	 * 获取Module RunData
	 * 
	 * @return
	 * @deprecated 改为使用 {@link #getModuleRunData(String)} 
	 */
//	RunData getModuleRunData();

	/**
	 * 获取Module RunData
	 * @param module
	 * 
	 * @return
	 */
	RequestContext getModuleRunData(String module);

	RequestContext getRequestRunData(HttpServletRequest req, HttpServletResponse resp);
}
