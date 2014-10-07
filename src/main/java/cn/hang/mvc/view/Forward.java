package cn.hang.mvc.view;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 直接跳转
 * 
 * @author hang.gao Initial Created at 2014年5月28日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Forward {

    String type() default "html";

    String sufix() default "";

    /**
     * request中的属性名，调用setAttribute时使用的key
     * 
     * @return
     */
    String attribute();

    String destination() default "";
}
