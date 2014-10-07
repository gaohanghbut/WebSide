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
	 * �ϴ��ļ�
	 * 
	 * @param ctx
	 *            ���������ļ�
	 * @return ��������File��Map
	 * @throws FileUploadException
	 *             �ļ��ϴ�ʧ��
	 */
	Map<String, FileItem> upload(RequestContext ctx) throws FileUploadException;
	
	/**
	 * �жϵ�ǰ�����Ƿ����ϴ��ļ�
	 * 
	 * @param ctx
	 *            ��ǰ����
	 * @return �����ǰ�������ϴ��ļ����򷵻�true�����򷵻�false
	 */
	boolean isMultipart(RequestContext ctx);
}
