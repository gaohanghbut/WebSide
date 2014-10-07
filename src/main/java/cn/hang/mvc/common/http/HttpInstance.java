package cn.hang.mvc.common.http;

import cn.hang.common.util.StringUtils;
import cn.hang.mvc.RequestContext;

/**
 * 处理HTTP协议的工具类
 * 
 * @author Hang
 * 
 */
public class HttpInstance {

	/**
	 * 单例
	 */
	private static final HttpInstance hi = new HttpInstance();

	/**
	 * 默认字符集名，浏览器提交数据默认使用
	 */
	private static final String DEFAULT_CHARSET_NAME = "ISO-8859-1";

	/**
	 * content type中的charset
	 */
	private static final String CHARSET_IN_CONTENT_TYPE = "charset";

	/**
	 * http请求的字符集
	 * 
	 * @param ctx
	 * @return
	 */
	public static String extractCharset(RequestContext ctx) {
		return hi.getCharSetName(ctx);
	}

	/**
	 * 默认使用的字符集
	 * 
	 * @return
	 */
	public static String defaultCharset() {
		return hi.getDefaultCharset();
	}
	
	/**
	 * 默认使用的字符集
	 * 
	 * @return
	 */
	public String getDefaultCharset() {
		return "UTF-8";
	}

	/**
	 * 通过请求上下文抽取请求的contentType中的字符集名
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
