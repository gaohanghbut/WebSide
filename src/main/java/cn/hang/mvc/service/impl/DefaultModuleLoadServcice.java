package cn.hang.mvc.service.impl;

import org.springframework.context.ApplicationContext;

import cn.hang.mvc.common.util.StringUtils;
import cn.hang.mvc.exception.InitializeException;
import cn.hang.mvc.service.ModuleLoadService;
import cn.hang.mvc.spring.context.ControllerChildWebApplicationContext;
import cn.hang.mvc.spring.holder.ApplicationContextHolders;

/**
 * 模块装载服务，用于装载Spring子容器，此类不是线程安全的
 * 
 * @author GaoHang
 * 
 */
public class DefaultModuleLoadServcice implements ModuleLoadService {
	
	/**
	 * 模块名序列，每一个模块名之间用空格分开
	 */
	private String moduleNames;

	private ApplicationContext rootContext;
	
	@Override
	public void service() {

		//装载子模块
		String[] modules = StringUtils.split(moduleNames, MODULE_SPLITE_REGEX);
		
		ApplicationContext context;
		for (String module : modules) {
			context = loadModule(module);
			ApplicationContextHolders.set(module, context);
		}
	}
	
	@Override
	public ApplicationContext loadModule(String moduleName) {
		if (rootContext == null) {
			rootContext = ApplicationContextHolders.getRootApplicationContext();
			if (rootContext == null) {
				throw new InitializeException("Root ApplicationContext is not initialized!");
			}
		}
		ApplicationContext context = new ControllerChildWebApplicationContext(moduleName, rootContext);
		return context;
	}

	@Override
	public void setModuleNames(String moduleNames) {
		this.moduleNames = moduleNames;
	}

	@Override
	public String getModuleNames() {
		return moduleNames;
	}

	@Override
	public ApplicationContext getModule(String moduleName) {
		if (ApplicationContextHolders.get(moduleName) == null) {
			ApplicationContextHolders.set(moduleName, loadModule(moduleName));
		}
		return ApplicationContextHolders.get(moduleName);
	}

}
