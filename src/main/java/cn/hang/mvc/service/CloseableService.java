package cn.hang.mvc.service;

/**
 * �ɹرյ�Service
 * 
 * @author hang.gao Initial Created at 2014��5��10��
 */
public interface CloseableService extends Service {

    void close();
    
    boolean isClosed();
}
