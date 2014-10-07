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
 * �Խ������������ʹ��ע�����֧��
 * 
 * @author Hang
 * 
 */
@ThreadSafe
public class AnnotatedActionInvokeValve extends ActionNotExistsAllowedActionInvokeValve {

	/**
	 * ����javabean������ֵ
	 */
	private PropertyValueSetter propertyValueSetter;
	
	/**
	 * ����ת������
	 */
	private TypeConvertService typeConvertService;

	/**
	 * �ļ��ϴ�����
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
			//û�������ļ��ϴ�����
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
		if (classes == null || classes.length == 0) { // û�в���
			return new ControlInvokeResult(cn.hang.common.util.InvocationUtils.invokeNoneParameterMethod(action, method), method);
		}
		
		//����û��ָ��HTTP��������ʽ����Action
		if (classes.length == 1 && classes[0].equals(RequestContext.class)) {// Ĭ�Ϸ�ʽ
			return super.invokeAction(ctx, methodName, action);
		}
		//����ʹ��ע��ָ���˲����ķ�ʽ����Action
		Map<String, String> paramValueMap = ctx.getRequestContext().getParameterMap();
		if (paramValueMap != null) {
			Object[] params = extractRequestParams(ctx.getRequestContext(), annotations, classes, paramValueMap);
			return new ControlInvokeResult(InvocationUtils.invokeMethod(action, method, params), method);
		}
		//�����޲η���
		return new ControlInvokeResult(InvocationUtils.invokeMethod(action, method), method);
	}

	/**
	 * @param ctx
	 *            �������еõ�Action������Ҫ�Ĳ���
	 * 
	 * @param annotations
	 *            Action�����Ĳ����ϵ�ע��
	 * @param classes
	 *            Action�����Ĳ�������
	 * @param paramValueMap
	 *            http����Ĳ���ӳ��
	 * @return Action������Ҫ�Ĳ����б�
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
		Annotation[] paramAnnotations = annotations[i];// ��i�������ϵ�ע��
		for (Annotation annotation : paramAnnotations) {
			if (annotation.annotationType().equals(FormBean.class)) {// bean����
				try {
					Object obj = classes[i].newInstance();
					propertyValueSetter.setProperties(obj, paramValueMap);
					params[i] = obj;
				} catch (Exception e) {
					throw new DynamicInvocationException(e);
				}
			} else if (annotation.annotationType().equals(Parameter.class)) {// ��������ֵ
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
