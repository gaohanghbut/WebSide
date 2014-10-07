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
 * 返回结果重写服务的默认实现
 * 
 * @author Hang
 *
 */
public class DefaultResultTypeRewriteService implements ResultTypeRewriteService {
	
	/**
	 * 持有原始类型与重写后的类型的映射
	 */
	private ResultTypeMapHolder resultTypeMapHolder;
	
	/**
	 * 重写服务初始化后的依赖注入窗口
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
