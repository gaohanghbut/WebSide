package cn.hang.mvc.exception;

/**
 * 定向异常，跳转时候发生
 * 
 * @author GaoHang
 * 
 */
public class DirectException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6749094702127401753L;

	public DirectException() {
		super();
	}

	public DirectException(String message, Throwable cause) {
		super(message, cause);
	}

	public DirectException(String message) {
		super(message);
	}

	public DirectException(Throwable cause) {
		super(cause);
	}

}
