package cn.hang.mvc.service;

import cn.hang.mvc.RequestContext;
import cn.hang.mvc.pipeline.valve.ControlInvokeResult;

/**
 * ��ͼ��Ⱦ����
 * 
 * @author hang.gao Initial Created at 2014��6��1��
 */
public interface ViewRendService extends Service {

    boolean render(RequestContext ctx, ControlInvokeResult view);
}
