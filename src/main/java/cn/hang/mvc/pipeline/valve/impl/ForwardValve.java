package cn.hang.mvc.pipeline.valve.impl;

import com.google.common.base.Strings;

import cn.hang.mvc.RequestContext;
import cn.hang.mvc.common.util.RequestContextConstants;
import cn.hang.mvc.common.util.ServiceManagers;
import cn.hang.mvc.common.util.StringUtils;
import cn.hang.mvc.pipeline.PipelineContext;
import cn.hang.mvc.pipeline.valve.AbstractValve;
import cn.hang.mvc.result.ResultTypeHandler;
import cn.hang.mvc.service.ResultTypeRewriteService;
import cn.hang.mvc.service.ServiceManager;

/**
 * ������ת��Valve
 * 
 * @author Hang
 * 
 */
public class ForwardValve extends AbstractValve {

    @Override
    public boolean execute(PipelineContext ctx) {
        if (ctx.hasReturn()) {
            return true;
        }
        String resultType = ctx.getRequestContext().getParameter(RequestContextConstants.TYPE_PARAMETER_NAME);
        if (StringUtils.isEmpty(resultType)) {
            resultType = RequestContextConstants.DEFAULT_RESULT_TYPE;
        }

        ResultTypeRewriteService resultTypeRewriteService = (ResultTypeRewriteService) ServiceManagers
                .getServiceManager().getService(ServiceManager.RESULT_TYPE_REWRITE_SERVICE);
        //���ؽ�����ļ���׺
        String sufixName = resultTypeRewriteService.getFinalResultSufix(resultType);
        resultType = resultTypeRewriteService.getFinalResultType(resultType);
        //���ؽ��ӳ��ʧ��
        if (StringUtils.isEmpty(resultType)) {
            resultType = ctx.getRequestContext().getParameter(RequestContextConstants.TYPE_PARAMETER_NAME);
        }
        if (StringUtils.isEmpty(sufixName)) {
            sufixName = resultType;
        }
        //���������
        ResultTypeHandler resultTypeHandler = resultTypeRewriteService.getOriginalResultTypeHandler(resultType);
        String uri = getReturnPath(ctx.getRequestContext(), resultType, sufixName);
        if (!uri.startsWith("/")) {
            uri = "/" + uri;
        }
        try {
            return resultTypeHandler.handleResult(ctx.getRequestContext(), uri);
        } catch (Exception e) {
            uri = getDefaultReturnPath(ctx.getRequestContext(), resultType, sufixName);
            return resultTypeHandler.handleResult(ctx.getRequestContext(), uri);
        } finally {}
    }

    /**
     * ȡ����ת·�����˷����ж�������ת·����ȡ�Ĺ���
     * 
     * @param runData ����������
     * 
     * @param finalResultType ��д��ķ�������
     * 
     * @param sufixName ��ͼ�ļ�����չ��
     * @return
     */
    protected String getReturnPath(RequestContext runData, String finalResultType, String sufixName) {
        //��ת·����url/resultType/actionName.resultType
        StringBuilder sb = new StringBuilder('/').append(runData.getRequestModuleName()).append('/')
                .append(finalResultType).append('/').append(runData.getResource());

        String event = runData.getParameter(RequestContextConstants.EVENT_PARAMETER_NAME);
        if (StringUtils.isNotEmpty(event) && !event.equals("execute")) {
            sb.append('_').append(event);
        }
        if (!Strings.isNullOrEmpty(sufixName)) {
            sb.append('.').append(sufixName).toString();
        }
        return sb.toString();
    }

    /**
     * ȡ����ת·�����˷����ж�������ת·����ȡ�Ĺ���
     * 
     * @param runData ����������
     * 
     * @param finalResultType ��д��ķ�������
     * 
     * @param sufixName ��ͼ�ļ�����չ��
     * @return
     */
    protected String getDefaultReturnPath(RequestContext runData, String finalResultType, String sufixName) {
        //��ת·����url/resultType/actionName.resultType
        return new StringBuilder('/').append(runData.getRequestModuleName()).append('/').append(finalResultType)
                .append('/').append(runData.getResource()).append('.').append(sufixName).toString();
    }

}
