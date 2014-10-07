package cn.hang.mvc.spring.context;

import org.springframework.web.context.support.XmlWebApplicationContext;

import cn.hang.mvc.common.util.ServletUtils;

/**
 * 修改XmlWebApplicationContext中的默认配置文件名称，此类用作加载返回类型重写配置。
 * 返回类型重写如：htm====>html等
 * 
 * @author GaoHang
 * 
 */
public class ResultTypeRewriteApplicationContext extends XmlWebApplicationContext {

	/**
	 * 新的默认服务配置文件路径
	 */
	public static final String DEFAULT_CONFIG_LOCATION = "/WEB-INF/result-type-rewrite.xml";

	public ResultTypeRewriteApplicationContext() {
		setServletContext(ServletUtils.getServletContext());
		refresh();
	}
	
	@Override
	protected String[] getDefaultConfigLocations() {
		return new String[] { DEFAULT_CONFIG_LOCATION };
	}

}
