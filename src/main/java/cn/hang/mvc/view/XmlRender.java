package cn.hang.mvc.view;

import java.lang.annotation.Annotation;

import org.springframework.beans.factory.InitializingBean;

import cn.hang.mvc.RequestContext;

import com.google.common.base.Preconditions;
import com.thoughtworks.xstream.XStream;

/**
 * ���л���XML����
 * 
 * @author hang.gao Initial Created at 2014��5��28��
 */
public class XmlRender implements Render, InitializingBean {

    /**
     * ���л���json�Ĺ���
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
