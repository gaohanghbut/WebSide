package cn.hang.mvc.service;

import cn.hang.mvc.service.impl.DefaultServiceManager;

/**
 * ���������
 * 
 * @author GaoHang
 * 
 */
public interface ServiceManager extends Service {

	/**
	 * ģ��װ�ط�������
	 */
	public static final String MODULE_LOAD_SERVICE = "_module_load_service_";

	/**
	 * �ܵ���������
	 */
	public static final String PIPELINE_SERVICE = "_pipeline_service_";
	
	/**
	 * ����ʱ���ݷ�������
	 */
	public static final String RUN_DATA_SERVICE = "_run_data_service_";
	
	/**
	 * ���ؽ����д��������
	 */
	public static final String RESULT_TYPE_REWRITE_SERVICE = "_result_type_rewrite_service_";
	
	/**
	 * ����ת�����������
	 */
	public static final String TYPE_CONVERT_SERVICE = "_type_convert_service_";
	
	/**
	 * �ļ��ϴ����������
	 */
	public static final String FILE_UPLOAD_SERVICE = "_file_upload_service_";
	
	/**
	 * Ĭ�Ϸ��������
	 */
	public static final ServiceManager DEFAULT_SERVICE_MANAGER = new DefaultServiceManager();

	/**
	 * ��ȡָ�����Ƶķ���
	 * 
	 * @param serviceName
	 *            ������
	 * @return
	 */
	Service getService(String serviceName);
	
	<T extends Service>T getService(Class<T> c);
	
}
