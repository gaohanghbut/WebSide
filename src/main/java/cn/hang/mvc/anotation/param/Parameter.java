package cn.hang.mvc.anotation.param;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ���ڱ�Ǵ������������ĸ�������Ϊ�������õĲ�������ע��ֻ�����ڱ�Ƿ����Ĳ���
 * 
 * @author GaoHang
 * 
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Parameter {

	/**
	 * ������
	 * 
	 * @return
	 */
	String value();
}
