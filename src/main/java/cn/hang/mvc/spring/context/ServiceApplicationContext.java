package cn.hang.mvc.spring.context;

import org.springframework.web.context.support.XmlWebApplicationContext;

import cn.hang.mvc.common.util.ServletUtils;

/**
 * 修改XmlWebApplicationContext中的默认配置文件名称，此类用作加载服务配置
 * 
 * @author GaoHang
 * 
 */
public class ServiceApplicationContext extends XmlWebApplicationContext {

	/**
	 * 新的默认服务配置文件路径
	 */
	public static final String DEFAULT_CONFIG_LOCATION = "/WEB-INF/service.xml";

	public ServiceApplicationContext() {
		setServletContext(ServletUtils.getServletContext());
		refresh();
	}
	
	@Override
	protected String[] getDefaultConfigLocations() {
		return new String[] { DEFAULT_CONFIG_LOCATION };
	}

}
