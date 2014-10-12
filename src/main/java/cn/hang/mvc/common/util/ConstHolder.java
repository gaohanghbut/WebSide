package cn.hang.mvc.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

/**
 * 常量
 * 
 * @author Hang
 *
 */
public class ConstHolder extends Properties {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8501648541219362988L;

	private Log log = LogFactory.getLog(ConstHolder.class);
	
	/**
	 * 属性文件资源 
	 */
	private Resource resource;
	
	public void init() {
		if (resource == null) {
			return;
		}
		InputStream in = null;
		try {
			in = resource.getInputStream();
			this.load(in);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * @return the resource
	 */
	public Resource getResource() {
		return resource;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
}
