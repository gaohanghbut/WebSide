package cn.hang.mvc.pipeline.impl;

import java.util.Collections;
import java.util.List;

import cn.hang.mvc.pipeline.ConditionPipeline;
import cn.hang.mvc.pipeline.Pipeline;
import cn.hang.mvc.pipeline.PipelineContext;
import cn.hang.mvc.pipeline.valve.Valve;

/**
 * 默认ConditionPipeline的实现，使用装饰器模式，此类只是个包装，真正的Pipeline则是源管道
 * 
 * @author GaoHang
 * 
 */
public class DefaultConditionPipeline implements ConditionPipeline {
	
	/**
	 * 满足条件的集合，一旦设置了此属性，此集合将不能再加入元素
	 */
	private List<String> urisufixs;
	
	/**
	 * 源管道
	 */
	private Pipeline sourcePipeline;

	public DefaultConditionPipeline(Pipeline sourcePipeline) {
		super();
		this.sourcePipeline = sourcePipeline;
	}

	@Override
	public void init() {
	}

	@Override
	public boolean handler(PipelineContext ctx) {
		if (sourcePipeline == null) {
			return false;
		}
		return sourcePipeline.handler(ctx);
	}

	@Override
	public void setValves(List<Valve> valves) {
		if (sourcePipeline != null) {
			sourcePipeline.setValves(valves);
		}
	}

	@Override
	public void setUrisufixs(List<String> urisufixs) {
		this.urisufixs = Collections.unmodifiableList(urisufixs);
	}

	@Override
	public boolean accept(String urisufix) {
		if (urisufixs.contains("*")) {
			return true;
		}
		return this.urisufixs.contains(urisufix);
	}

}
