package cn.hang.mvc.service.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import com.google.common.base.Preconditions;

import cn.hang.mvc.RequestContext;
import cn.hang.mvc.common.util.ApplicationContextUtils;
import cn.hang.mvc.pipeline.valve.ControlInvokeResult;
import cn.hang.mvc.service.ViewRendService;
import cn.hang.mvc.spring.holder.ApplicationContextHolder;
import cn.hang.mvc.view.Render;
import cn.hang.mvc.view.RenderResolvers;
import cn.hang.mvc.view.ViewResolver;

public class DefaultViewRendService implements ViewRendService {

    private volatile ViewResolver viewResolver;

    @Override
    public boolean render(RequestContext ctx, ControlInvokeResult object) {
        Preconditions.checkNotNull(ctx);
        if (viewResolver == null) {
            synchronized (this) {
                if (viewResolver == null) {
                    ApplicationContext applicationContext = ApplicationContextUtils.getApplicationContextHolder().get(
                            ApplicationContextHolder.SERVICE_CONTEXT);
                    try {
                        viewResolver = applicationContext.getBean(ViewResolver.class);
                    } catch (NoSuchBeanDefinitionException e) {
                        viewResolver = RenderResolvers.defaultRenderResolver();
                    }
                }
            }
        }
        Method method = object.getMethod();
        if (method != null) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Render render = viewResolver.lookupRender(annotation.annotationType());
                if (render != null) {
                    render.rend(ctx, annotation, object.getResult());
                    ctx.expireForward();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void service() {
        ApplicationContext applicationContext = ApplicationContextUtils.getApplicationContextHolder().get(
                ApplicationContextHolder.PIPELINE_CONTEXT);
        if (applicationContext == null) {
            return;
        }
        try {
            viewResolver = applicationContext.getBean(ViewResolver.class);
        } catch (NoSuchBeanDefinitionException e) {
            viewResolver = RenderResolvers.defaultRenderResolver();
        }
    }

}
