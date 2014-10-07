package cn.hang.mvc.pipeline;

import java.util.List;

import cn.hang.mvc.pipeline.valve.Valve;

/**
 * 管道
 * 
 * @author GaoHang
 * 
 */
public interface Pipeline {

	/**
	 * 管道初始化后调用
	 */
	void init();

	/**
	 * 对请求进行拦截
	 * 
	 * @return 执行成功则返回true，否则返回false
	 */
	boolean handler(PipelineContext ctx);

	/**
	 * 设置管道中的valve
	 * 
	 * @param valves
	 */
	void setValves(List<Valve> valves);
}
