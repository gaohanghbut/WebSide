package cn.hang.mvc.view;

/**
 * ��ͼ��Ⱦ��������ߣ����ڳ�����ͼ��Ⱦ�Ķ���
 * 
 * @author hang.gao Initial Created at 2014��5��28��
 */
public interface ViewResolver {

    Render lookupRender(Class<?> viewAnnotation);
}
