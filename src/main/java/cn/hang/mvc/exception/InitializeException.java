package cn.hang.mvc.exception;

/**
 * ≥ı ºªØ“Ï≥£
 * 
 * @author GaoHang
 * 
 */
public class InitializeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2074657852889845788L;

	public InitializeException(String message, Throwable cause) {
		super(message, cause);
	}

	public InitializeException(String message) {
		super(message);
	}

	public InitializeException(Throwable cause) {
		super(cause);
	}

}
