package cn.hang.mvc.view;

import java.lang.annotation.Annotation;

import cn.hang.mvc.RequestContext;

/**
 * 视图渲染接口，用于做动态视图渲染，如xml,json（资源也可以使用跳转的方式）
 * 
 * @author hang.gao Initial Created at 2014年5月28日
 */
public interface Render {

    /**
     * 需要返回给调用者的对象
     * 
     * @param ctx
     * @param annotation 方法上标记的视图渲染注解
     * @param view 需要返回给调用者的对象
     */
    void rend(RequestContext ctx, Annotation annotation, Object view);
}
