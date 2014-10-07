package cn.hang.mvc.service;

import cn.hang.mvc.RequestContext;
import cn.hang.mvc.pipeline.Pipeline;

/**
 * 管道组装服务，每一个模块的管理都在一个单独的Spring容器实例中
 * 
 * @author GaoHang
 * 
 */
public interface PipelineService extends Service {
	
	/**
	 * pipeline文件对应的ApplicationContext名称的后缀，ApplicationContext的名称用于
	 * 
	 */
	public static final String PIPELINE_CONTEXT_KEY_SUFIX = "_pipline_";
	
	/**
	 * 通过Valve名称组装pipeline
	 * 
	 * @param valves
	 *            valve名称数组
	 * @param pipelineId
	 *            管道标识
	 * @return 包含指定valve的管理
	 */
	Pipeline loadPipeline(String pipelineId, String[] valves);

	/**
	 * 获取指定Id的管道
	 * 
	 * @param pipelineId
	 *            管道标识
	 * @return
	 */
	Pipeline getPipeline(String pipelineId);
	
	/**
	 * 执行管道
	 */
	boolean invokePipeline(RequestContext ctx);

}