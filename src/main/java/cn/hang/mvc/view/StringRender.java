package cn.hang.mvc.view;

import java.io.IOException;
import java.lang.annotation.Annotation;

import com.google.common.base.Preconditions;

import cn.hang.mvc.RequestContext;

/**
 * ֱ�ӵ���toString()�������л�
 * 
 * @author hang.gao Initial Created at 2014��5��28��
 */
public class StringRender implements Render {

    @Override
    public void rend(RequestContext ctx, Annotation annotation, Object view) {
        Preconditions.checkNotNull(ctx);
        if (view == null) {
            return;
        }
        try {
            ctx.getHttpServletResponse().getWriter().println(view);
        } catch (IOException e) {
            throw new SerializeException(e);
        }
    }

}
