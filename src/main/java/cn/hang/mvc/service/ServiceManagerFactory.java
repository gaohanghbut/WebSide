package cn.hang.mvc.service;

/**
 * 抽象工厂模式的工厂接口，用于生产ServiceManager对象
 * 
 * @author Hang
 * 
 */
public interface ServiceManagerFactory {

	ServiceManager getServiceManager();
}
