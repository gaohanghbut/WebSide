package cn.hang.mvc.common.util;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * Web应用工具类
 * 
 * @author GaoHang
 * 
 */
public abstract class ServletUtils {
	
	private ServletUtils() {
	}
	
	/**
	 * 当前Web应用的ServletContext实例
	 */
	private static ServletContext	servletContext;
	
	/**
	 * 当前Web应用的ServletConfig实例
	 */
	private static ServletConfig	servletConfig;
	
	private static String			base_uri;
	
	/**
	 * 用于标记ServletContext对象是否已经被设置
	 */
	private static boolean			servletContextSet;
	
	/**
	 * 设置ServletContext
	 * 
	 * @param context
	 */
	public static void setServletContext(ServletContext context) {
		if (!servletContextSet) {
			synchronized (ServletUtils.class) {
				if (!servletContextSet) {
					servletContext = context;
				}
			}
		}
	}
	
	/**
	 * 获取ServletContext对象
	 * 
	 * @return
	 */
	public static ServletContext getServletContext() {
		return servletContext;
	}
	
	public static ServletConfig getServletConfig() {
		return servletConfig;
	}
	
	public static void setServletConfig(ServletConfig servletConfig) {
		ServletUtils.servletConfig = servletConfig;
	}
	
	public static String getBaseUri() {
		return base_uri;
	}
	
	public static void setBaseUri(String baseUri) {
		if (base_uri == null) {
			synchronized (ServletUtils.class) {
				if (base_uri == null) {
					base_uri = baseUri;
				}
			}
		}
	}
	
}
