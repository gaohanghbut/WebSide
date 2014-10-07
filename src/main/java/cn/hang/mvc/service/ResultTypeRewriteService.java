package cn.hang.mvc.service;

import cn.hang.mvc.result.ResultTypeHandler;
import cn.hang.mvc.result.ResultTypeMapHolder;

/**
 * 返回结果重写服务
 * 
 * @author Hang
 * 
 */
public interface ResultTypeRewriteService extends Service {

	/**
	 * 在Spring中的bean配置的name
	 */
	public static final String RESULT_TYPE_REWRITE_BEAN_NAME = "_result_type_rewrite_bean_";

	/**
	 * 返回存储重写映射的对象
	 * 
	 * @return
	 */
	ResultTypeMapHolder getResultTypeMapHolder();

	/**
	 * 获取指定返回类型对应的返回结果处理器
	 * 
	 * @param resultType
	 *            重写后的返回类型
	 * @return
	 */
	ResultTypeHandler getResultTypeHandler(String resultType);

	/**
	 * 获取指定原始返回类型对应的返回结果处理器
	 * 
	 * @param originalResultType
	 *            重写前的返回类型
	 * @return
	 */
	ResultTypeHandler getOriginalResultTypeHandler(String originalResultType);
	
	/**
	 * 通过原始返回类型获取重写后的返回类型
	 * 
	 * @param originalResultType 原始返回类型
	 * 
	 * @return
	 */
	String getFinalResultType(String originalResultType);
	
	/**
	 * 通过原始返回类型获取重写后的返回类型视图文件的后缀名
	 * 
	 * @param originalResultType 原始返回类型
	 * 
	 * @return
	 */
	String getFinalResultSufix(String originalResultType);

}
