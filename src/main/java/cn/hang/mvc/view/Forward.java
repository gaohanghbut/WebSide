package cn.hang.mvc.view;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ֱ����ת
 * 
 * @author hang.gao Initial Created at 2014��5��28��
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Forward {

    String type() default "html";

    String sufix() default "";

    /**
     * request�е�������������setAttributeʱʹ�õ�key
     * 
     * @return
     */
    String attribute();

    String destination() default "";
}
