package cn.hang.mvc.anotation.param;

import java.lang.reflect.Method;

import cn.hang.common.reflect.PropertyNotFoundHandler;
import cn.hang.common.reflect.PropertyValueSetterImpl;
import cn.hang.common.util.ClassUtils;
import cn.hang.common.util.InvocationUtils;
import cn.hang.common.util.StringUtils;
import cn.hang.mvc.common.util.ServiceManagers;
import cn.hang.mvc.service.ServiceManager;
import cn.hang.mvc.service.TypeConvertService;

/**
 *
 * @author Hang
 *
 */
public class ConverterBasedPropertyValueSetter extends PropertyValueSetterImpl {
	
	/**
	 */
	private TypeConvertService typeConvertService;
	
	public ConverterBasedPropertyValueSetter() {
		typeConvertService = (TypeConvertService) ServiceManagers.getServiceManager().getService(ServiceManager.TYPE_CONVERT_SERVICE);
	}

	@Override
	protected void invokeSetter(Object target, String property, Object val, PropertyNotFoundHandler handler, Class<?> c) {
		String methodName = "set" + StringUtils.firstCharToUppercase(property);
		Method method = InvocationUtils.lookupMethod(c, methodName);
		if (method == null) {
			if (handler != null) {
				handler.handl(property, val);
			}
			return;
		}
		Class<?>[] classes = method.getParameterTypes();
		if (classes == null || classes.length == 0 || classes.length > 1) {//??????
			return;
		}
		val = typeConvertService.convert(val.toString(), ClassUtils.to(classes[0]));
		InvocationUtils.invokeMethod(target, method, val);
	}

}
