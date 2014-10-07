package cn.hang.mvc.pipeline.valve.impl;

import cn.hang.mvc.common.util.RequestContextConstants;
import cn.hang.mvc.common.util.ServiceManagers;
import cn.hang.mvc.common.util.StringUtils;
import cn.hang.mvc.pipeline.PipelineContext;
import cn.hang.mvc.pipeline.valve.AbstractValve;
import cn.hang.mvc.service.ResultTypeRewriteService;
import cn.hang.mvc.service.ServiceManager;

/**
 * ���ڼ�鷵�ص���ͼ�Ƿ���ڵ�Valve������������򽫿���Ȩ������Web����
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
		//���ؽ��ӳ��ʧ��
		if (StringUtils.isEmpty(resultType)) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

}
