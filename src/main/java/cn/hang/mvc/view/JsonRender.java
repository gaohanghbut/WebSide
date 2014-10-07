package cn.hang.mvc.view;

import java.lang.annotation.Annotation;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.base.Preconditions;

import cn.hang.mvc.RequestContext;

/**
 * JSON数据渲染
 * 
 * @author hang.gao Initial Created at 2014年5月28日
 */
public class JsonRender implements Render, InitializingBean {
    
    /**
     * 负责序列化成json
     */
    private ObjectMapper objectMapper;

    @Override
    public void rend(RequestContext ctx, Annotation annotation, Object view) {
        Preconditions.checkNotNull(ctx);
        if (view == null) {
            return;
        }
        try {
            ctx.getHttpServletResponse().getWriter().println(objectMapper.writeValueAsString(view));
        } catch (Throwable e) {
            throw new SerializeException(e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
    }
    
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

}
