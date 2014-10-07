package cn.hang.mvc.service;

import cn.hang.mvc.RequestContext;
import cn.hang.mvc.pipeline.Pipeline;

/**
 * �ܵ���װ����ÿһ��ģ��Ĺ�����һ��������Spring����ʵ����
 * 
 * @author GaoHang
 * 
 */
public interface PipelineService extends Service {
	
	/**
	 * pipeline�ļ���Ӧ��ApplicationContext���Ƶĺ�׺��ApplicationContext����������
	 * 
	 */
	public static final String PIPELINE_CONTEXT_KEY_SUFIX = "_pipline_";
	
	/**
	 * ͨ��Valve������װpipeline
	 * 
	 * @param valves
	 *            valve��������
	 * @param pipelineId
	 *            �ܵ���ʶ
	 * @return ����ָ��valve�Ĺ���
	 */
	Pipeline loadPipeline(String pipelineId, String[] valves);

	/**
	 * ��ȡָ��Id�Ĺܵ�
	 * 
	 * @param pipelineId
	 *            �ܵ���ʶ
	 * @return
	 */
	Pipeline getPipeline(String pipelineId);
	
	/**
	 * ִ�йܵ�
	 */
	boolean invokePipeline(RequestContext ctx);

}