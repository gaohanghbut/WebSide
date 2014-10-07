package cn.hang.mvc.service;

import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import cn.hang.mvc.file.FileUploadException;

import cn.hang.mvc.RequestContext;

/**
 * 
 * @author Hang
 * 
 */
public interface FileUploadService extends Service {
	
	/**
	 * 上传文件
	 * 
	 * @param ctx
	 *            请求上下文件
	 * @return 参数名与File的Map
	 * @throws FileUploadException
	 *             文件上传失败
	 */
	Map<String, FileItem> upload(RequestContext ctx) throws FileUploadException;
	
	/**
	 * 判断当前请求是否是上传文件
	 * 
	 * @param ctx
	 *            当前请求
	 * @return 如果当前请求是上传文件，则返回true，否则返回false
	 */
	boolean isMultipart(RequestContext ctx);
}
