package cn.hang.mvc.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemHeaders;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.client.ResourceAccessException;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;

/**
 * 表示一个正在上传的文件
 * 
 * @author Hang
 * 
 */
public final class MultipartFile {

	private Log log = LogFactory.getLog(MultipartFile.class);
	
	/**
	 * 上传的文件
	 */
	private FileItem fileItem;

	/**
	 * @param fileItem
	 */
	public MultipartFile(FileItem fileItem) {
		Preconditions.checkNotNull(fileItem);
		this.fileItem = fileItem;
	}

	/**
	 * 将文件内容复制到输出流
	 * 
	 * @param out 输出流，不能为空
	 */
	public void copyTo(OutputStream out) {
		Preconditions.checkNotNull(out);
		InputStream inputStream = null;
		try {
			inputStream = fileItem.getInputStream();
			ByteStreams.copy(inputStream, out);
		} catch (IOException e) {
			throw new ResourceAccessException("Restore the upload file error", e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					if (log.isErrorEnabled()) {
						log.error("close the upload InputStream error", e);
					}
				}
			}
		}
	}
	
	/**
	 * @return
	 * @see org.apache.commons.fileupload.FileItemHeadersSupport#getHeaders()
	 */
	public FileItemHeaders getHeaders() {
		return this.fileItem.getHeaders();
	}

	/**
	 * @return
	 * @throws IOException
	 * @see org.apache.commons.fileupload.FileItem#getInputStream()
	 */
	public InputStream getInputStream() throws IOException {
		return this.fileItem.getInputStream();
	}

	/**
	 * @return
	 * @see org.apache.commons.fileupload.FileItem#getContentType()
	 */
	public String getContentType() {
		return this.fileItem.getContentType();
	}

	/**
	 * @return
	 * @see org.apache.commons.fileupload.FileItem#getName()
	 */
	public String getName() {
		return this.fileItem.getName();
	}

	/**
	 * @return
	 * @see org.apache.commons.fileupload.FileItem#isInMemory()
	 */
	public boolean isInMemory() {
		return this.fileItem.isInMemory();
	}

	/**
	 * @return
	 * @see org.apache.commons.fileupload.FileItem#getSize()
	 */
	public long getSize() {
		return this.fileItem.getSize();
	}

	/**
	 * @param encoding
	 * @return
	 * @throws UnsupportedEncodingException
	 * @see org.apache.commons.fileupload.FileItem#getString(java.lang.String)
	 */
	public String getString(String encoding) throws UnsupportedEncodingException {
		return this.fileItem.getString(encoding);
	}

	/**
	 * @return
	 * @see org.apache.commons.fileupload.FileItem#getString()
	 */
	public String getString() {
		return this.fileItem.getString();
	}

	/**
	 * 
	 * @see org.apache.commons.fileupload.FileItem#delete()
	 */
	public void delete() {
		this.fileItem.delete();
	}
	
	
}
