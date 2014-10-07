package cn.hang.mvc.service.impl;

import org.springframework.context.ApplicationContext;

import com.google.common.base.Optional;

import cn.hang.mvc.common.util.ApplicationContextUtils;
import cn.hang.mvc.common.util.StringUtils;
import cn.hang.mvc.result.ResultTypeHandler;
import cn.hang.mvc.result.ResultTypeMapHolder;
import cn.hang.mvc.service.ResultTypeRewriteService;
import cn.hang.mvc.spring.context.ResultTypeRewriteApplicationContext;
import cn.hang.mvc.spring.holder.ApplicationContextHolder;

/**
 * ���ؽ����д�����Ĭ��ʵ��
 * 
 * @author Hang
 *
 */
public class DefaultResultTypeRewriteService implements ResultTypeRewriteService {
	
	/**
	 * ����ԭʼ��������д������͵�ӳ��
	 */
	private ResultTypeMapHolder resultTypeMapHolder;
	
	/**
	 * ��д�����ʼ���������ע�봰��
	 */
	private ApplicationContext applicationContext;

	@Override
	public void service() {
		applicationContext = new ResultTypeRewriteApplicationContext();
		resultTypeMapHolder = applicationContext.getBean(RESULT_TYPE_REWRITE_BEAN_NAME, ResultTypeMapHolder.class);
		ApplicationContextUtils.getApplicationContextHolder().set(ApplicationContextHolder.RESULT_TYPE_REWRITE_CONTEXT, applicationContext);
	}

	@Override
	public ResultTypeMapHolder getResultTypeMapHolder() {
		return resultTypeMapHolder;
	}

	@Override
	public ResultTypeHandler getResultTypeHandler(String resultType) {
		if (StringUtils.isEmpty(resultType)) {
			getOriginalResultTypeHandler("default");
		}
		return applicationContext.getBean(resultType, ResultTypeHandler.class);
	}

	@Override
	public ResultTypeHandler getOriginalResultTypeHandler(String originalResultType) {
		if (StringUtils.isEmpty(originalResultType)) {
			originalResultType = "default";
		}
		return applicationContext.getBean(Optional.fromNullable(resultTypeMapHolder.getFinalResultType(originalResultType)).or(originalResultType), ResultTypeHandler.class);
	}

	@Override
	public String getFinalResultType(String originalResultType) {
		return resultTypeMapHolder.getFinalResultType(originalResultType);
	}

	@Override
	public String getFinalResultSufix(String originalResultType) {
		return resultTypeMapHolder.getFinalResultSufix(originalResultType);
	}

}
