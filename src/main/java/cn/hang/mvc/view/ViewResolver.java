package cn.hang.mvc.view;

/**
 * 视图渲染对象持有者，用于持有视图渲染的对象
 * 
 * @author hang.gao Initial Created at 2014年5月28日
 */
public interface ViewResolver {

    Render lookupRender(Class<?> viewAnnotation);
}
