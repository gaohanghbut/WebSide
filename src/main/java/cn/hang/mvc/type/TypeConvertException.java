package cn.hang.mvc.type;

/**
 * 类型转换异常
 * 
 * @author GaoHang
 * 
 */
@SuppressWarnings("serial")
public class TypeConvertException extends RuntimeException {

	public TypeConvertException() {
		super();
	}

	public TypeConvertException(String message, Throwable cause) {
		super(message, cause);
	}

	public TypeConvertException(String message) {
		super(message);
	}

	public TypeConvertException(Throwable cause) {
		super(cause);
	}

	
}
