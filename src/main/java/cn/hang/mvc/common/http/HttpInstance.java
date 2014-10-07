package cn.hang.mvc.common.http;

import cn.hang.common.util.StringUtils;
import cn.hang.mvc.RequestContext;

/**
 * ����HTTPЭ��Ĺ�����
 * 
 * @author Hang
 * 
 */
public class HttpInstance {

	/**
	 * ����
	 */
	private static final HttpInstance hi = new HttpInstance();

	/**
	 * Ĭ���ַ�������������ύ����Ĭ��ʹ��
	 */
	private static final String DEFAULT_CHARSET_NAME = "ISO-8859-1";

	/**
	 * content type�е�charset
	 */
	private static final String CHARSET_IN_CONTENT_TYPE = "charset";

	/**
	 * http������ַ���
	 * 
	 * @param ctx
	 * @return
	 */
	public static String extractCharset(RequestContext ctx) {
		return hi.getCharSetName(ctx);
	}

	/**
	 * Ĭ��ʹ�õ��ַ���
	 * 
	 * @return
	 */
	public static String defaultCharset() {
		return hi.getDefaultCharset();
	}
	
	/**
	 * Ĭ��ʹ�õ��ַ���
	 * 
	 * @return
	 */
	public String getDefaultCharset() {
		return "UTF-8";
	}

	/**
	 * ͨ�����������ĳ�ȡ�����contentType�е��ַ�����
	 * 
	 * @param ctx
	 * @return
	 */
	private String getCharSetName(RequestContext ctx) {
		String contentType = ctx.getHttpServletRequest().getContentType();
		if (StringUtils.isEmpty(contentType)) {
			return DEFAULT_CHARSET_NAME;
		}
		int index = contentType.indexOf(CHARSET_IN_CONTENT_TYPE);
		if (index < 0) {
			return DEFAULT_CHARSET_NAME;
		}
		try {
			return contentType.substring(index
					+ CHARSET_IN_CONTENT_TYPE.length() + 1);// charset=utf-8
		} catch (Exception e) {
			return DEFAULT_CHARSET_NAME;
		}
	}
}
