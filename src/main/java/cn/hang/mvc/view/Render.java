package cn.hang.mvc.view;

import java.lang.annotation.Annotation;

import cn.hang.mvc.RequestContext;

/**
 * ��ͼ��Ⱦ�ӿڣ���������̬��ͼ��Ⱦ����xml,json����ԴҲ����ʹ����ת�ķ�ʽ��
 * 
 * @author hang.gao Initial Created at 2014��5��28��
 */
public interface Render {

    /**
     * ��Ҫ���ظ������ߵĶ���
     * 
     * @param ctx
     * @param annotation �����ϱ�ǵ���ͼ��Ⱦע��
     * @param view ��Ҫ���ظ������ߵĶ���
     */
    void rend(RequestContext ctx, Annotation annotation, Object view);
}
