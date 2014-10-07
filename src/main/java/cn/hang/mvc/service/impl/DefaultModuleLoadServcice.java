package cn.hang.mvc.service.impl;

import org.springframework.context.ApplicationContext;

import cn.hang.mvc.common.util.StringUtils;
import cn.hang.mvc.exception.InitializeException;
import cn.hang.mvc.service.ModuleLoadService;
import cn.hang.mvc.spring.context.ControllerChildWebApplicationContext;
import cn.hang.mvc.spring.holder.ApplicationContextHolders;

/**
 * ģ��װ�ط�������װ��Spring�����������಻���̰߳�ȫ��
 * 
 * @author GaoHang
 * 
 */
public class DefaultModuleLoadServcice implements ModuleLoadService {
	
	/**
	 * ģ�������У�ÿһ��ģ����֮���ÿո�ֿ�
	 */
	private String moduleNames;

	private ApplicationContext rootContext;
	
	@Override
	public void service() {

		//װ����ģ��
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
