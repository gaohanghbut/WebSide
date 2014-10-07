package cn.hang.mvc.service;

/**
 * 可关闭的Service
 * 
 * @author hang.gao Initial Created at 2014年5月10日
 */
public interface CloseableService extends Service {

    void close();
    
    boolean isClosed();
}
