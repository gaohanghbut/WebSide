package cn.hang.mvc.result;

import cn.hang.mvc.RequestContext;

/**
 * 返回结果处理，如：如何返回json，如果json使用模板语言，则在此接口的实现中进行渲染，如果是html则直接跳转等。
 * 
 * @author Hang
 *
 */
public interface ResultTypeHandler {

	/**
	 * 执行跳转之前的操作以及跳转
	 * 
	 */
	boolean handleResult(RequestContext requestContext, String path);
}
