package cn.hang.mvc.view;

import java.lang.annotation.Annotation;

import cn.hang.mvc.RequestContext;

import com.google.common.base.Preconditions;

/**
 * 跳转到目的路径
 * 
 * @author hang.gao Initial Created at 2014年5月28日
 */
public class RedirectRender implements Render {

    @Override
    public void rend(RequestContext ctx, Annotation annotation, Object view) {
        Preconditions.checkNotNull(ctx);
        Preconditions.checkNotNull(view);
        ctx.redirect((String) view);;
    }

}
