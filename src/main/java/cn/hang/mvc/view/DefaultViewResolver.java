package cn.hang.mvc.view;

import java.util.Map;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;

/**
 * ViewResolver��Ĭ��ʵ��
 * 
 * @author hang.gao Initial Created at 2014��5��28��
 */
public class DefaultViewResolver implements ViewResolver {

    private Map<Class<?>, Render> renders;

    @Override
    public Render lookupRender(Class<?> viewAnnotation) {
        return renders.get(viewAnnotation);
    }

    public void setRenders(Map<String, Render> renders) {
        if (this.renders != null) {
            return;
        }
        this.renders = Maps.newConcurrentMap();
        if (renders == null) {
            return;
        }
        try {
            for (Map.Entry<String, Render> en : renders.entrySet()) {
                this.renders.put(Class.forName(en.getKey()), en.getValue());
            }
        } catch (ClassNotFoundException e) {
            throw Throwables.propagate(e);
        }
    }

}
