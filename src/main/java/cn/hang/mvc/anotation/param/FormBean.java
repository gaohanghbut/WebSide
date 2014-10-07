package cn.hang.mvc.anotation.param;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于接收表单参数，将表单中的各参数创建一个方法调用参数中的实体类型的对象
 * 
 * @author GaoHang
 * 
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FormBean {
}
