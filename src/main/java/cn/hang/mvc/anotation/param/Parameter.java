package cn.hang.mvc.anotation.param;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记从浏览器传入的哪个参数作为方法调用的参数，此注解只能用于标记方法的参数
 * 
 * @author GaoHang
 * 
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Parameter {

	/**
	 * 参数名
	 * 
	 * @return
	 */
	String value();
}
