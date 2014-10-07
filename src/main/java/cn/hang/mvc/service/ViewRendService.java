package cn.hang.mvc.service;

import cn.hang.mvc.RequestContext;
import cn.hang.mvc.pipeline.valve.ControlInvokeResult;

/**
 * 视图渲染服务
 * 
 * @author hang.gao Initial Created at 2014年6月1日
 */
public interface ViewRendService extends Service {

    boolean render(RequestContext ctx, ControlInvokeResult view);
}
