package cn.hang.mvc.pipeline.impl;

import java.util.Collections;
import java.util.List;

import cn.hang.mvc.pipeline.ConditionPipeline;
import cn.hang.mvc.pipeline.Pipeline;
import cn.hang.mvc.pipeline.PipelineContext;
import cn.hang.mvc.pipeline.valve.Valve;

/**
 * Ĭ��ConditionPipeline��ʵ�֣�ʹ��װ����ģʽ������ֻ�Ǹ���װ��������Pipeline����Դ�ܵ�
 * 
 * @author GaoHang
 * 
 */
public class DefaultConditionPipeline implements ConditionPipeline {
	
	/**
	 * ���������ļ��ϣ�һ�������˴����ԣ��˼��Ͻ������ټ���Ԫ��
	 */
	private List<String> urisufixs;
	
	/**
	 * Դ�ܵ�
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
