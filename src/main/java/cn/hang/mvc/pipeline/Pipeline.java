package cn.hang.mvc.pipeline;

import java.util.List;

import cn.hang.mvc.pipeline.valve.Valve;

/**
 * �ܵ�
 * 
 * @author GaoHang
 * 
 */
public interface Pipeline {

	/**
	 * �ܵ���ʼ�������
	 */
	void init();

	/**
	 * �������������
	 * 
	 * @return ִ�гɹ��򷵻�true�����򷵻�false
	 */
	boolean handler(PipelineContext ctx);

	/**
	 * ���ùܵ��е�valve
	 * 
	 * @param valves
	 */
	void setValves(List<Valve> valves);
}
