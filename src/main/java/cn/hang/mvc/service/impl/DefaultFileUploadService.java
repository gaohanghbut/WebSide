package cn.hang.mvc.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.hang.mvc.RequestContext;
import cn.hang.mvc.service.FileUploadService;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

/**
 * �ļ����ط����Ĭ��ʵ��
 * 
 * @author Hang
 * 
 */
public class DefaultFileUploadService implements FileUploadService {
	
	/**
	 * �����ļ��ϴ��Ķ���
	 */
	private ServletFileUpload	servletFileUpload;
	
	@Override
	public void service() {
		DiskFileItemFactory factory = new DiskFileItemFactory();
//		factory.
		servletFileUpload = new ServletFileUpload(factory);
		servletFileUpload.setFileSizeMax(1024*1024);//1MB
	}
	
	@Override
	public Map<String, FileItem> upload(RequestContext ctx) throws cn.hang.mvc.file.FileUploadException {
		if (isMultipart(ctx)) {
			List<FileItem> fileItems;
			try {
				fileItems = servletFileUpload.parseRequest(ctx.getHttpServletRequest());
			} catch (FileUploadException e) {
				throw new cn.hang.mvc.file.FileUploadException(e);
			}
			Map<String, FileItem> map = Maps.newHashMap();
			for (FileItem fileItem : fileItems) {
				if (!fileItem.isFormField()) {
					map.put(fileItem.getFieldName(), fileItem);
				}
			}
			return ImmutableMap.copyOf(map);
		}
		return Collections.emptyMap();
	}

	@Override
	public boolean isMultipart(RequestContext ctx) {
		return ServletFileUpload.isMultipartContent(ctx.getHttpServletRequest());
	}
	
}
