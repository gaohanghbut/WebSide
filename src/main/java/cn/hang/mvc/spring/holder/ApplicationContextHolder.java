package cn.hang.mvc.spring.holder;

import org.springframework.context.ApplicationContext;

/**
 * 用于存储和获取ApplicationContext对象的接口
 * 
 * @author GaoHang
 * 
 */
public interface ApplicationContextHolder {

    /**
     * 表示根容器的key
     */
    static final String ROOT_APPLICATION_CONTEXT = "_ROOT_APPLICATION_CONTEXT_";

    /**
     * 服务服务容器的key
     */
    static final String SERVICE_CONTEXT = "_SERVICE_CONTEXT_";

    /**
     * 管道管道容器的key
     */
    static final String PIPELINE_CONTEXT = "_pipeline_context_";

    /**
     * 表示返回结果重写的字容器的key
     */
    static final String RESULT_TYPE_REWRITE_CONTEXT = "_result_type_rewrite_context_";

    /**
     * 获取key对应的application context
     * 
     * @param key
     * @return
     */
    ApplicationContext get(String key);

    /**
     * 设置key与application context的对应关系
     * 
     * @param key
     * @param ctx
     */
    void set(String key, ApplicationContext ctx);

    /**
     * 设置父容器
     * 
     * @param parent
     */
    void setRootApplicationContext(ApplicationContext parent);

    /**
     * 获取父容器
     * 
     * @return
     */
    ApplicationContext getRootApplicationContext();

    /**
     * 可以在初始化完成后调用，功能为让持有ApplicationContext的容器不能再被修改
     */
    void constHolder();

    /**
     * 销毁容器
     */
    void destroy();

}
