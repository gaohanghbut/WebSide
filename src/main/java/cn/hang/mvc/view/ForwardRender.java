package cn.hang.mvc.view;

import java.lang.annotation.Annotation;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import cn.hang.mvc.RequestContext;

/**
 * 跳转到目的路径
 * 
 * @author hang.gao Initial Created at 2014年5月28日
 */
public class ForwardRender implements Render {

    @Override
    public void rend(RequestContext ctx, Annotation annotation, Object view) {
        Preconditions.checkNotNull(ctx);
        Preconditions.checkNotNull(annotation);
        Forward forward = (Forward) annotation;
        String dst = forward.destination();
        ctx.put(forward.attribute(), view);
        if (Strings.isNullOrEmpty(dst)) {
            String sufix = forward.sufix();
            if (Strings.isNullOrEmpty(sufix)) {
                sufix = forward.type();
            }
            dst = new StringBuffer().append('/').append(ctx.getRequestModuleName()).append(forward.type())
                    .append(ctx.getResource()).append('.').append(sufix).toString();
        }
        ctx.forward(dst);
    }

}
