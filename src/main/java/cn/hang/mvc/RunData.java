package cn.hang.mvc;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * 请求运行信息，包括请求参数，Request，Response等
 * 
 * @author GaoHang
 * 
 */
public interface RunData {

	/**
	 * 获取参数值
	 * 
	 * @param paramName
	 *            参数名
	 * @return
	 */
	String getParameter(String paramName);
	
	/**
	 * 根据指定字符集获取参数值
	 * 
	 * @param paramName
	 *            参数名
	 * @return
	 */
	String getParameter(String paramName, String charset);
	
	/**
	 * 根据指定字符集获取参数值
	 * 
	 * @param paramName
	 *            参数名
	 * @return
	 */
	String getParameter(String paramName, Charset charset);

	/**
	 * 获取参数值，如复选框
	 * 
	 * @param paramName
	 *            参数名
	 * @return
	 */
	String[] getParameterValues(String paramName);

	/**
	 * 获取请求的URI
	 * 
	 * @return
	 */
	String getURI();

	/**
	 * 服务器重定向
	 * 
	 * @param path
	 *            跳转的路径
	 */
	void forward(String path);

	/**
	 * 客户端重定向
	 * 
	 * @param path
	 *            重定向的路径
	 */
	void redirect(String path);

	/**
	 * 获取整型参数
	 * 
	 * @param param
	 *            参数名
	 * @return
	 */
	int getIntParameter(String param);

	/**
	 * 获取布尔型参数
	 * 
	 * @param param
	 *            参数名
	 * @return
	 */
	boolean getBooleanParameter(String param);

	/**
	 * 获取Long型参数
	 * 
	 * @param param
	 *            参数名
	 * @return
	 */
	long getLongParameter(String param);

	/**
	 * 获取short型参数
	 * 
	 * @param param
	 *            参数名
	 * @return 参数值
	 */
	short getShortParameter(String param);

	/**
	 * 根据请求中传来的参数返回实体类的对象
	 * 
	 * @param c
	 * @return
	 */
	@Deprecated
	<T> T getEntiry(Class<T> c);

	/**
	 * 获取请求的URL
	 * 
	 * @return
	 */
	String getURL();

	/**
	 * 是否已经返回
	 * 
	 * @return 是则返回true，否则返回false
	 */
	boolean hasReturned();

	/**
	 * 返回当前请求的模块名
	 * 
	 * @return
	 */
	String getRequestModuleName();

	/**
	 * 返回action参数中的前缀，此处的前缀定义为"action"前的字符串，如：请求的action为“helloAction”，则
	 * 返回"hello"，如果action参数中没有"Action"则返回参数值
	 * 
	 * @return
	 */
	String getResource();

	/**
	 * 取得请求的Action对应的Screen的名称
	 * 
	 * @return
	 */
	String getScreenName();

	/**
	 * 获取请求的Action
	 * 
	 * @return 请求的Action
	 */
	String getActionName();
	
	/**
	 * 获取参数名与参数值的映射
	 * 
	 * @return 参数名与参数值的映射
	 */
	Map<String, String> getParameterMap();
}
