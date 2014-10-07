package cn.hang.mvc.exception;

/**
 * 管道执行异常
 * 
 * @author GaoHang
 * 
 */
public class PipelineException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 353850370957675108L;

	public PipelineException(String message, Throwable cause) {
		super(message, cause);
	}

	public PipelineException(String message) {
		super(message);
	}

	public PipelineException(Throwable cause) {
		super(cause);
	}

}
