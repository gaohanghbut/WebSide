package cn.hang.mvc.view;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ���ڱ�Ƿ��ض���ΪJSON
 * 
 * @author hang.gao Initial Created at 2014��5��28��
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Json {
}
