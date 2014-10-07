package cn.hang.mvc.view;

import java.util.Map;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;

/**
 * 工具类，用于获取默认的视图渲染器
 * 
 * @author hang.gao Initial Created at 2014年5月28日
 */
public final class RenderResolvers {
    private RenderResolvers() {
    }
    private static final DefaultViewResolver resolver = new DefaultViewResolver();
    static {
        Map<String, Render> renders = Maps.newHashMap();
        JsonRender jsonRender = new JsonRender();
        try {
            jsonRender.afterPropertiesSet();
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
        renders.put("cn.hang.mvc.view.Json", jsonRender);
        XmlRender xmlRender = new XmlRender();
        try {
            xmlRender.afterPropertiesSet();
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
        renders.put("cn.hang.mvc.view.Xml", xmlRender);
        resolver.setRenders(renders);
    }
    public static ViewResolver defaultRenderResolver() {
        return resolver;
    }
}
