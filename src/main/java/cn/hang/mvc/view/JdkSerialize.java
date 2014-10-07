package cn.hang.mvc.view;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记返回对象为JDK的对象序列化方式
 * 
 * @author hang.gao Initial Created at 2014年5月28日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JdkSerialize {
}
