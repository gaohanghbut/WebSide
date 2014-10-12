package cn.hang.mvc.common.reflect;

import cn.hang.common.annotation.ThreadSafe;
import cn.hang.common.util.DynamicInvocationException;
import cn.hang.common.util.InvocationUtils;
import cn.hang.common.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 默认实现，使用反射设置属性的值
 * 
 * @author Hang
 * 
 */
@ThreadSafe
public class PropertyValueSetterImpl implements PropertyValueSetter {

	public void setProperties(Object target, Map<String, ?> properties, PropertyNotFoundHandler handler) {
		if (properties == null) {
			return;
		}
		Class<?> c = target.getClass();

		outer: for (Map.Entry<String, ?> e : properties.entrySet()) {
			String[] pros = e.getKey().split(".");// 中间有做好事符
			if (pros.length > 1) {
				Class<?> wrap = c;
				Object prowrap = target;
				for (int i = 0; i < pros.length - 1; i++) {
					try {
						Object obj = getAndSetPropertyEntity(wrap, pros[i], prowrap);
						if (obj == null) {
							continue outer;
						}
						wrap = obj.getClass();
						prowrap = obj;
					} catch (DynamicInvocationException e1) {
						continue outer;
					}
				}
			}
			invokeSetter(target, e.getKey(), e.getValue(), handler, c);
		}
	}

	/**
	 * 如果属性是实体类的对象，此方法用于返回该实体类的对象，并将此对象设置到目标对象上
	 *
	 * @param c
	 * @param property
	 * @return
	 * @throws cn.hang.common.util.DynamicInvocationException
	 */
	private Object getAndSetPropertyEntity(Class<?> c, String property, Object target) throws DynamicInvocationException {
		String methodName = "set" + StringUtils.firstCharToUppercase(property);
		Method method = InvocationUtils.lookupMethod(c, methodName);
		if (method == null) {
			return null;
		}
		Class<?>[] pcs = method.getParameterTypes();
		if (pcs == null) {
			return null;
		}
		Class<?> pc = pcs[0];
		try {
			Object param = pc.newInstance();
			invokeSetter(target, property, param, null, pc);
			return param;
		} catch (InstantiationException e) {
			throw new DynamicInvocationException(e);
		} catch (IllegalAccessException e) {
			throw new DynamicInvocationException(e);
		}
	}
	
	public void setProperties(Object target, Map<String, ?> properties) {
		setProperties(target, properties, null);
	}
	
	public void setProperty(Object target, String property, Object val, PropertyNotFoundHandler handler) {
		if (property == null) {
			return;
		}
		Class<?> c = target.getClass();
		invokeSetter(target, property, val, handler, c);
	}
	
	/**
	 * 执行设置属性值的掕
	 * 
	 * @param target
	 *            目标对象
	 * @param property
	 *            属性名
	 * @param val
	 *            属性值
	 * @param handler
	 *            属性不存在时的处理方式
	 * @param c
	 *            目标对象的class对象
	 */
	protected void invokeSetter(Object target, String property, Object val, PropertyNotFoundHandler handler, Class<?> c) {
		String methodName = "set" + StringUtils.firstCharToUppercase(property);
		Method method = InvocationUtils.lookupMethod(c, methodName);
		if (method == null) {
			if (handler != null) {
				handler.handl(property, val);
			}
			return;
		}
		InvocationUtils.invokeMethod(target, method, val);
	}
	
	public void setProperty(Object target, String property, Object val) {
		setProperty(target, property, val, null);
	}
	
}
