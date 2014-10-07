package cn.hang.mvc.common.util;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * WebӦ�ù�����
 * 
 * @author GaoHang
 * 
 */
public abstract class ServletUtils {
	
	private ServletUtils() {
	}
	
	/**
	 * ��ǰWebӦ�õ�ServletContextʵ��
	 */
	private static ServletContext	servletContext;
	
	/**
	 * ��ǰWebӦ�õ�ServletConfigʵ��
	 */
	private static ServletConfig	servletConfig;
	
	private static String			base_uri;
	
	/**
	 * ���ڱ��ServletContext�����Ƿ��Ѿ�������
	 */
	private static boolean			servletContextSet;
	
	/**
	 * ����ServletContext
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
	 * ��ȡServletContext����
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
