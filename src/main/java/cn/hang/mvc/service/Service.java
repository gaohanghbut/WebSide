package cn.hang.mvc.service;

/**
 * 框架中使用的服务接口，每一个服务应该由服务管理器启动，服务管理器在初始化时会加载service.xml文件
 * 
 * @author GaoHang
 * 
 */
public interface Service {

	/**
	 * 执行服务方法
	 */
	void service();
}
