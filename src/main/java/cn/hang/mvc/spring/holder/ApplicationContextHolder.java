package cn.hang.mvc.spring.holder;

import org.springframework.context.ApplicationContext;

/**
 * ���ڴ洢�ͻ�ȡApplicationContext����Ľӿ�
 * 
 * @author GaoHang
 * 
 */
public interface ApplicationContextHolder {

    /**
     * ��ʾ��������key
     */
    static final String ROOT_APPLICATION_CONTEXT = "_ROOT_APPLICATION_CONTEXT_";

    /**
     * �������������key
     */
    static final String SERVICE_CONTEXT = "_SERVICE_CONTEXT_";

    /**
     * �ܵ��ܵ�������key
     */
    static final String PIPELINE_CONTEXT = "_pipeline_context_";

    /**
     * ��ʾ���ؽ����д����������key
     */
    static final String RESULT_TYPE_REWRITE_CONTEXT = "_result_type_rewrite_context_";

    /**
     * ��ȡkey��Ӧ��application context
     * 
     * @param key
     * @return
     */
    ApplicationContext get(String key);

    /**
     * ����key��application context�Ķ�Ӧ��ϵ
     * 
     * @param key
     * @param ctx
     */
    void set(String key, ApplicationContext ctx);

    /**
     * ���ø�����
     * 
     * @param parent
     */
    void setRootApplicationContext(ApplicationContext parent);

    /**
     * ��ȡ������
     * 
     * @return
     */
    ApplicationContext getRootApplicationContext();

    /**
     * �����ڳ�ʼ����ɺ���ã�����Ϊ�ó���ApplicationContext�����������ٱ��޸�
     */
    void constHolder();

    /**
     * ��������
     */
    void destroy();

}
