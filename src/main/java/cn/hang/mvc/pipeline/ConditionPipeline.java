package cn.hang.mvc.pipeline;

import java.util.List;

/**
 * 条件判断管道
 * 
 * @author GaoHang
 * 
 */
public interface ConditionPipeline extends Pipeline {
	
	/**
	 * 条件中，后缀的分隔符
	 */
	public static final String SUFIX_DILIMETER = " ";

	/**
	 * 设置管道执行的条件，如果请求的URI的后缀包含于参数中
	 * 
	 * @param urisufix
	 *            满足条件的后缀集合
	 */
	void setUrisufixs(List<String> urisufix);

	/**
	 * 判断指定的uri后缀能否让管道执行
	 * 
	 * @param urisufix
	 * @return
	 */
	boolean accept(String urisufix);
}
