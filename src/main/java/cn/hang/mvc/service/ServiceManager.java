package cn.hang.mvc.service;

import cn.hang.mvc.service.impl.DefaultServiceManager;

/**
 * 服务管理器
 * 
 * @author GaoHang
 * 
 */
public interface ServiceManager extends Service {

	/**
	 * 模块装载服务名称
	 */
	public static final String MODULE_LOAD_SERVICE = "_module_load_service_";

	/**
	 * 管道服务名称
	 */
	public static final String PIPELINE_SERVICE = "_pipeline_service_";
	
	/**
	 * 运行时数据服务名称
	 */
	public static final String RUN_DATA_SERVICE = "_run_data_service_";
	
	/**
	 * 返回结果重写服务名称
	 */
	public static final String RESULT_TYPE_REWRITE_SERVICE = "_result_type_rewrite_service_";
	
	/**
	 * 类型转换服务的名称
	 */
	public static final String TYPE_CONVERT_SERVICE = "_type_convert_service_";
	
	/**
	 * 文件上传服务的名称
	 */
	public static final String FILE_UPLOAD_SERVICE = "_file_upload_service_";
	
	/**
	 * 默认服务管理器
	 */
	public static final ServiceManager DEFAULT_SERVICE_MANAGER = new DefaultServiceManager();

	/**
	 * 获取指定名称的服务
	 * 
	 * @param serviceName
	 *            服务名
	 * @return
	 */
	Service getService(String serviceName);
	
	<T extends Service>T getService(Class<T> c);
	
}
