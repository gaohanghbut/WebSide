package cn.hang.mvc.service;

import org.springframework.context.ApplicationContext;

/**
 * 模块装载服务接口
 * 
 * @author GaoHang
 * 
 */
public interface ModuleLoadService extends Service {
	
	/**
	 * 配置中的moduleNames属性值中默认的各模块名的分隔符
	 */
	static final String MODULE_SPLITE_REGEX = " ";
	
	/**
	 * 加载模块
	 * 
	 * @param moduleName
	 *            模块名
	 * @return 模块加载后返回模块的Spring依赖注入容器
	 */
	public abstract ApplicationContext loadModule(String moduleName);

	/**
	 * 获取指定模块的依赖注入容器
	 * 
	 * @param moduleName
	 *            模块名
	 * @return 模块对应的ApplicationContext
	 */
	public abstract ApplicationContext getModule(String moduleName);

	/**
	 * 设置模块名，每个模块名间用空格分开
	 * 
	 * @param moduleNames
	 */
	public abstract void setModuleNames(String moduleNames);

	/**
	 * 获取模块名，每个模块名间用空格分开
	 * 
	 * @return
	 */
	public String getModuleNames();

}