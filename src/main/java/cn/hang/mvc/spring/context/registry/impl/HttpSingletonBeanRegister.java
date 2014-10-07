package cn.hang.mvc.spring.context.registry.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.config.SingletonBeanRegistry;

import cn.hang.mvc.http.HttpRequestManager;
import cn.hang.mvc.spring.context.registry.SingletonBeanRegister;

/**
 * HTTP请求注入器，用于向ApplicationContext或者BeanFactory中注册与HTTP请求相关的HttpServletRequest，
 * HttpServletResponse和HttpSession
 * 
 * @author GaoHang
 * 
 */
public class HttpSingletonBeanRegister implements SingletonBeanRegister {

	@Override
	public void register(SingletonBeanRegistry registry) {
		HttpRequestManager httpRequestManager = HttpRequestManager.DEFAULT_HTTP_REQUEST_MANAGER;
		HttpServletRequest request = httpRequestManager.getMvcHttpServletRequest();
		HttpServletResponse response = httpRequestManager.getMvcHttpServletResponse();
		HttpSession session = httpRequestManager.getMvcHttpSession();
		
		registry.registerSingleton("httpServletRequest", request);
		registry.registerSingleton("httpServletResponse", response);
		registry.registerSingleton("httpSession", session);
//		registry.registerSingleton("servletContext", ServletUtils.getServletContext());
	}

}
