package cn.hang.mvc.spring.context;

import org.springframework.web.context.support.XmlWebApplicationContext;

import cn.hang.mvc.common.util.ServletUtils;

/**
 * �޸�XmlWebApplicationContext�е�Ĭ�������ļ����ƣ������������ط�������
 * 
 * @author GaoHang
 * 
 */
public class ServiceApplicationContext extends XmlWebApplicationContext {

	/**
	 * �µ�Ĭ�Ϸ��������ļ�·��
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
