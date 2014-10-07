package cn.hang.mvc.exception;

/**
 * ��̬���������쳣
 * 
 * @author GaoHang
 * 
 */
public class DynamicInvocationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4653524753743132681L;

	public DynamicInvocationException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DynamicInvocationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public DynamicInvocationException(String message) {
		super(message);
	}

}
