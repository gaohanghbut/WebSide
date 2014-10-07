package cn.hang.mvc.pipeline.valve.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

import cn.hang.common.annotation.ThreadSafe;
import cn.hang.common.reflect.PropertyValueSetter;
import cn.hang.common.util.ClassUtils;
import cn.hang.common.util.InvocationUtils;
import cn.hang.mvc.RequestContext;
import cn.hang.mvc.anotation.param.ConverterBasedPropertyValueSetter;
import cn.hang.mvc.anotation.param.FormBean;
import cn.hang.mvc.anotation.param.ParamAnnotationConstants;
import cn.hang.mvc.anotation.param.Parameter;
import cn.hang.mvc.anotation.param.WebFile;
import cn.hang.mvc.common.util.ServiceManagers;
import cn.hang.mvc.exception.DynamicInvocationException;
import cn.hang.mvc.file.MultipartFile;
import cn.hang.mvc.pipeline.PipelineContext;
import cn.hang.mvc.pipeline.valve.ControlInvokeResult;
import cn.hang.mvc.pipeline.valve.impl.ActionNotExistsAllowedActionInvokeValve;
import cn.hang.mvc.service.FileUploadService;
import cn.hang.mvc.service.ServiceManager;
import cn.hang.mvc.service.TypeConvertService;

/**
 * 对接收浏览器参数使用注解进行支持
 * 
 * @author Hang
 * 
 */
@ThreadSafe
public class AnnotatedActionInvokeValve extends ActionNotExistsAllowedActionInvokeValve {

	/**
	 * 设置javabean的属性值
	 */
	private PropertyValueSetter propertyValueSetter;
	
	/**
	 * 类型转换服务
	 */
	private TypeConvertService typeConvertService;

	/**
	 * 文件上传服务
	 */
	private FileUploadService	fileUploadService;

	@Override
	public void init() {
		super.init();
		propertyValueSetter = getPropertyValueSetter();
		typeConvertService = (TypeConvertService) ServiceManagers.getServiceManager().getService(ServiceManager.TYPE_CONVERT_SERVICE);
		try {
			fileUploadService = (FileUploadService) ServiceManagers.getServiceManager().getService(ServiceManager.FILE_UPLOAD_SERVICE);
		} catch (Exception e) {
			//没有配置文件上传服务
		}
	}

	@Override
	protected ControlInvokeResult invokeAction(PipelineContext ctx, String methodName, Object action) {
		Method method = InvocationUtils.lookupMethod(action.getClass(), methodName);
		if (method == null) {
			return null;
		}
		Annotation[][] annotations = method.getParameterAnnotations();
		Class<?>[] classes = method.getParameterTypes();
		if (classes == null || classes.length == 0) { // 没有参数
			return new ControlInvokeResult(cn.hang.common.util.InvocationUtils.invokeNoneParameterMethod(action, method), method);
		}
		
		//按照没有指定HTTP参数的形式调用Action
		if (classes.length == 1 && classes[0].equals(RequestContext.class)) {// 默认方式
			return super.invokeAction(ctx, methodName, action);
		}
		//按照使用注解指定了参数的方式调用Action
		Map<String, String> paramValueMap = ctx.getRequestContext().getParameterMap();
		if (paramValueMap != null) {
			Object[] params = extractRequestParams(ctx.getRequestContext(), annotations, classes, paramValueMap);
			return new ControlInvokeResult(InvocationUtils.invokeMethod(action, method, params), method);
		}
		//调用无参方法
		return new ControlInvokeResult(InvocationUtils.invokeMethod(action, method), method);
	}

	/**
	 * @param ctx
	 *            从请求中得到Action调用需要的参数
	 * 
	 * @param annotations
	 *            Action方法的参数上的注解
	 * @param classes
	 *            Action方法的参数类型
	 * @param paramValueMap
	 *            http请求的参数映射
	 * @return Action调用需要的参数列表
	 */
	private Object[] extractRequestParams(RequestContext ctx, Annotation[][] annotations, Class<?>[] classes, Map<String, String> paramValueMap) {
		Object[] params = new Object[classes.length];
		Map<String, FileItem> fileMap = fileUploadService == null? Collections.<String, FileItem>emptyMap():(fileUploadService.isMultipart(ctx)?fileUploadService.upload(ctx):Collections.<String, FileItem>emptyMap());
		for (int i = 0; i < classes.length; i++) {
			if (classes[i].equals(RequestContext.class)) {
				params[i] = ctx;
			} else {
				extractParameter(annotations, classes, paramValueMap, params, fileMap, i);
			}
		}
		return params;
	}

	/**
	 * @param annotations
	 * @param classes
	 * @param paramValueMap
	 * @param params
	 * @param fileMap
	 * @param i
	 */
	private void extractParameter(Annotation[][] annotations, Class<?>[] classes, Map<String, String> paramValueMap, Object[] params, Map<String, FileItem> fileMap, int i) {
		Annotation[] paramAnnotations = annotations[i];// 第i个参数上的注解
		for (Annotation annotation : paramAnnotations) {
			if (annotation.annotationType().equals(FormBean.class)) {// bean类型
				try {
					Object obj = classes[i].newInstance();
					propertyValueSetter.setProperties(obj, paramValueMap);
					params[i] = obj;
				} catch (Exception e) {
					throw new DynamicInvocationException(e);
				}
			} else if (annotation.annotationType().equals(Parameter.class)) {// 单个属性值
				String paramName = (String) InvocationUtils.invokeNoneParameterMethod(annotation, ParamAnnotationConstants.PARAMETER_ANNOTATION_PARAMETER_NAME_PROPERTY_NAME);
				Class<?> type = ClassUtils.to(classes[i]);
				if (!type.equals(String.class)) {
					params[i] = typeConvertService.convert(paramValueMap.get(paramName), type);
				} else {
					params[i] = paramValueMap.get(paramName);
				}
			} else if (WebFile.class.equals(annotation.annotationType())) {
				String paramName = (String) InvocationUtils.invokeNoneParameterMethod(annotation, ParamAnnotationConstants.PARAMETER_ANNOTATION_PARAMETER_NAME_PROPERTY_NAME);
				FileItem item = fileMap.get(paramName);
				if (item == null) {
					continue;
				}
				Class<?> type = ClassUtils.to(classes[i]);
				if (type.equals(byte[].class)) {
					params[i] = item.get();
				} else if (type.equals(MultipartFile.class)) {
					params[i] = new MultipartFile(item);
				}
			}
		}
	}

	/**
	 * @return the propertyValueSetter
	 */
	protected PropertyValueSetter getPropertyValueSetter() {
		return new ConverterBasedPropertyValueSetter();
	}

}
