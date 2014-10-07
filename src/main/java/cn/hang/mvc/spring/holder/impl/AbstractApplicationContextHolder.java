package cn.hang.mvc.spring.holder.impl;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import cn.hang.mvc.spring.holder.ApplicationContextHolder;

/**
 * 
 * @author GaoHang
 * 
 */
public abstract class AbstractApplicationContextHolder implements ApplicationContextHolder {

    private Log log = LogFactory.getLog(AbstractApplicationContextHolder.class);
    
	/**
	 * 用于容纳各个模块对应的ApplicationContext对象
	 */
	private volatile Map<String, ApplicationContext> applicationContexts;

	public AbstractApplicationContextHolder() {
		super();
	}

	@Override
	public ApplicationContext get(String key) {
		insureContextMap();
		return applicationContexts.get(key);
	}

	/**
	 * 确保applicationContexts初始化一次
	 */
	private void insureContextMap() {
		if (applicationContexts == null) {
			synchronized (this) {
				if (applicationContexts == null) {
					applicationContexts = getApplicationContextMap();
				}
			}
		}
	}

	@Override
	public void set(String key, ApplicationContext ctx) {
		insureContextMap();
		applicationContexts.put(key, ctx);
	}

	@Override
	public ApplicationContext getRootApplicationContext() {
		insureContextMap();
		return applicationContexts.get(ROOT_APPLICATION_CONTEXT);
	}

	@Override
	public void setRootApplicationContext(ApplicationContext parent) {
		insureContextMap();
		applicationContexts.put(ROOT_APPLICATION_CONTEXT, parent);
	}

	/**
	 * 用于取得存放ApplicationContext的Map
	 * 
	 * @return
	 */
	protected abstract Map<String, ApplicationContext> getApplicationContextMap();

	@Override
	public void constHolder() {
		applicationContexts = Collections.unmodifiableMap(applicationContexts);
	}

    @Override
    public void destroy() {
        for (Map.Entry<String, ApplicationContext> en : applicationContexts.entrySet()) {
            ApplicationContext applicationContext = en.getValue();
            if (applicationContext instanceof ConfigurableWebApplicationContext) {
                try {
                    ((ConfigurableWebApplicationContext)applicationContext).close();
                } catch (Exception e) {
                    if (log.isErrorEnabled()) {
                        log.error("Close the application context " + en.getKey() + " failed", e);
                    }
                }
            }
        }
    }
	
}