package cn.hang.mvc.common.util;

/**
 * RunData运行时常量
 * 
 * @author GaoHang
 * 
 */
public abstract class RequestContextConstants {

	private RequestContextConstants() {
	}

	/**
	 * 请求的资源的参数名
	 */
	public static final String RESOURCE_PARAMETER_NAME = "resource";
	
	/**
	 * 请求的Action上执行的方法
	 */
	public static final String EVENT_PARAMETER_NAME = "event";
	
	/**
	 * 返回给浏览器的数据类型
	 */
	public static final String TYPE_PARAMETER_NAME = "type";
	
	/**
	 * 默认返回数据类型
	 */
	public static final String DEFAULT_RESULT_TYPE = "html";
	
	/**
	 * 是否已经跳转
	 */
	public static final String RETURN = "forwarded";
	
	/**
	 * 请求的Action名
	 */
	public static final String ACTION_PARAMETER_NAME = "action";
	
	/**
	 * action参数中包含的后缀字符串
	 */
	public static final String ACTION_NAME_SUFIX = "Action";
}
