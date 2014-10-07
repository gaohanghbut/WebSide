package cn.hang.mvc.pipeline.valve.impl;

import cn.hang.mvc.common.util.RequestContextConstants;
import cn.hang.mvc.common.util.ServiceManagers;
import cn.hang.mvc.common.util.StringUtils;
import cn.hang.mvc.pipeline.PipelineContext;
import cn.hang.mvc.pipeline.valve.AbstractValve;
import cn.hang.mvc.service.ResultTypeRewriteService;
import cn.hang.mvc.service.ServiceManager;

/**
 * 用于检查返回的视图是否存在的Valve，如果不存在则将控制权返还给Web容器
 * 
 * @author Hang
 *
 */
public class ViewExistsCheckValve extends AbstractValve {

	@Override
	public boolean execute(PipelineContext ctx) {
		String resultType = ctx.getRequestContext().getParameter(RequestContextConstants.TYPE_PARAMETER_NAME);
		if (StringUtils.isEmpty(resultType)) {
			resultType = RequestContextConstants.DEFAULT_RESULT_TYPE;
		}
		
		ResultTypeRewriteService resultTypeRewriteService = (ResultTypeRewriteService) ServiceManagers.getServiceManager().getService(ServiceManager.RESULT_TYPE_REWRITE_SERVICE);
		resultType = resultTypeRewriteService.getFinalResultType(resultType);
		//返回结果映射失败
		if (StringUtils.isEmpty(resultType)) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

}
