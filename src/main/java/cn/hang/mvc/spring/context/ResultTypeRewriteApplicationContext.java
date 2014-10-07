package cn.hang.mvc.spring.context;

import org.springframework.web.context.support.XmlWebApplicationContext;

import cn.hang.mvc.common.util.ServletUtils;

/**
 * �޸�XmlWebApplicationContext�е�Ĭ�������ļ����ƣ������������ط���������д���á�
 * ����������д�磺htm====>html��
 * 
 * @author GaoHang
 * 
 */
public class ResultTypeRewriteApplicationContext extends XmlWebApplicationContext {

	/**
	 * �µ�Ĭ�Ϸ��������ļ�·��
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
