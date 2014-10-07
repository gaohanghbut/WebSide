package cn.hang.mvc.file;

/**
 * 文件上载异常
 * 
 * @author Hang
 * 
 */
public class FileUploadException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5022268048063086682L;

	/**
	 * 
	 */
	public FileUploadException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public FileUploadException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public FileUploadException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public FileUploadException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
