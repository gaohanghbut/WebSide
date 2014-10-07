package cn.hang.mvc.view;

import java.lang.annotation.Annotation;

import org.springframework.beans.factory.InitializingBean;

import cn.hang.mvc.RequestContext;

import com.google.common.base.Preconditions;
import com.thoughtworks.xstream.XStream;

/**
 * 序列化成XML数据
 * 
 * @author hang.gao Initial Created at 2014年5月28日
 */
public class XmlRender implements Render, InitializingBean {

    /**
     * 序列化成json的工具
     */
    private XStream xmlWriter;

    @Override
    public void rend(RequestContext ctx, Annotation annotation, Object view) {
        Preconditions.checkNotNull(ctx);
        if (view == null) {
            return;
        }
        try {
            xmlWriter.toXML(view, ctx.getHttpServletResponse().getWriter());
        } catch (Throwable e) {
            throw new SerializeException(e);
        }
    }

    public void setXmlWriter(XStream xmlWriter) {
        this.xmlWriter = xmlWriter;
    }

    public void afterPropertiesSet() throws Exception {
        if (xmlWriter == null) {
            xmlWriter = new XStream();
        }
    }

}
