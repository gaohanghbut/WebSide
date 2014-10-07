package cn.hang.mvc.service;

import org.springframework.context.ApplicationContext;

/**
 * ģ��װ�ط���ӿ�
 * 
 * @author GaoHang
 * 
 */
public interface ModuleLoadService extends Service {
	
	/**
	 * �����е�moduleNames����ֵ��Ĭ�ϵĸ�ģ�����ķָ���
	 */
	static final String MODULE_SPLITE_REGEX = " ";
	
	/**
	 * ����ģ��
	 * 
	 * @param moduleName
	 *            ģ����
	 * @return ģ����غ󷵻�ģ���Spring����ע������
	 */
	public abstract ApplicationContext loadModule(String moduleName);

	/**
	 * ��ȡָ��ģ�������ע������
	 * 
	 * @param moduleName
	 *            ģ����
	 * @return ģ���Ӧ��ApplicationContext
	 */
	public abstract ApplicationContext getModule(String moduleName);

	/**
	 * ����ģ������ÿ��ģ�������ÿո�ֿ�
	 * 
	 * @param moduleNames
	 */
	public abstract void setModuleNames(String moduleNames);

	/**
	 * ��ȡģ������ÿ��ģ�������ÿո�ֿ�
	 * 
	 * @return
	 */
	public String getModuleNames();

}