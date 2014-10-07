package cn.hang.mvc.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.hang.mvc.RequestContext;
import cn.hang.mvc.RunData;
import cn.hang.mvc.exception.DynamicInvocationException;

/**
 * ���������뷴�乤����
 * 
 * @author GaoHang
 * 
 */
public abstract class InvocationUtils {

	private InvocationUtils() {
	}

	/**
	 * ����Ŀ������ϵķ���
	 * 
	 * @param target
	 *            Ŀ�����
	 * @param methodName
	 *            ������
	 * @return �������õķ���ֵ
	 */
	public static Object invokeNoneParameterMethod(Object target, String methodName) throws DynamicInvocationException {
		Class<?> c = target.getClass();
		try {
			Method method = c.getMethod(methodName);
			return method.invoke(target);
		} catch (SecurityException e) {
			throw new DynamicInvocationException(e);
		} catch (NoSuchMethodException e) {
			throw new DynamicInvocationException(e);
		} catch (IllegalArgumentException e) {
			throw new DynamicInvocationException(e);
		} catch (IllegalAccessException e) {
			throw new DynamicInvocationException(e);
		} catch (InvocationTargetException e) {
			throw new DynamicInvocationException(e);
		}
	}

	/**
	 * ����Ŀ������ϵķ���
	 * 
	 * @param target
	 *            Ŀ�����
	 * @param methodName
	 *            ������
	 * @return �������õķ���ֵ
	 */
	public static Object invokeRunDataParameterMethod(Object target,String methodName, RunData runData)throws DynamicInvocationException {
		Class<?> c = target.getClass();
		try {
			Method method = c.getMethod(methodName, RunData.class);
			return method.invoke(target, runData);
		} catch (SecurityException e) {
			throw new DynamicInvocationException(e);
		} catch (NoSuchMethodException e) {
			throw new DynamicInvocationException(e);
		} catch (IllegalArgumentException e) {
			throw new DynamicInvocationException(e);
		} catch (IllegalAccessException e) {
			throw new DynamicInvocationException(e);
		} catch (InvocationTargetException e) {
			throw new DynamicInvocationException(e);
		}
	}
	
	/**
	 * ����Ŀ������ϵķ���
	 * 
	 * @param target
	 *            Ŀ�����
	 * @param methodName
	 *            ������
	 * @return �������õķ���ֵ
	 */
	public static Object invokeRunDataParameterMethod(Object target,String methodName, RequestContext runData)throws DynamicInvocationException {
		Class<?> c = target.getClass();
		try {
			Method method = c.getMethod(methodName, RequestContext.class);
			return method.invoke(target, runData);
		} catch (SecurityException e) {
			throw new DynamicInvocationException(e);
		} catch (NoSuchMethodException e) {
			throw new DynamicInvocationException(e);
		} catch (IllegalArgumentException e) {
			throw new DynamicInvocationException(e);
		} catch (IllegalAccessException e) {
			throw new DynamicInvocationException(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new DynamicInvocationException(e);
		}
	}
	
	/**
	 * ����Ŀ������ϵķ���
	 * 
	 * @param target
	 *            Ŀ�����
	 * @param methodName
	 *            ������
	 * @return �������õķ���ֵ
	 */
	public static Object invokeRunDataParameterMethodWithObject(Object target,String methodName, RequestContext runData, Object... objs)throws DynamicInvocationException {
		Class<?> c = target.getClass();
		try {
			int l = 1;
			if (objs != null) {
				l += objs.length;
			}
			Method method = cn.hang.common.util.InvocationUtils.lookupMethod(c, methodName);
			if (method == null) {
				throw new DynamicInvocationException("Method not exists");
			}
			Object[] params = new Object[l];
			params[0] = runData;
			if (l != 1) {//objs����null
				int i = 1;
				for (Object obj : objs) {
					params[i++] = obj;
				}
			}
			return method.invoke(target, params);
		} catch (SecurityException e) {
			throw new DynamicInvocationException(e);
		} catch (IllegalArgumentException e) {
			throw new DynamicInvocationException(e);
		} catch (IllegalAccessException e) {
			throw new DynamicInvocationException(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new DynamicInvocationException(e);
		}
	}
	
	/**
	 * ����Ŀ������ϵķ���
	 * 
	 * @param target
	 *            Ŀ�����
	 * @param methodName
	 *            ������
	 * @return �������õķ���ֵ
	 */
	public static Object invokeMethod(Object target, String methodName, Object...args)throws DynamicInvocationException {
		Class<?> c = target.getClass();
		try {
			Class<?>[] argClasses = new Class[args.length];
			for (int i = 0; i < args.length; i++) {
				argClasses[i] = args[i].getClass();
			}
			Method method = c.getMethod(methodName, argClasses);
			return method.invoke(target, args);
		} catch (SecurityException e) {
			throw new DynamicInvocationException(e);
		} catch (NoSuchMethodException e) {
			throw new DynamicInvocationException(e);
		} catch (IllegalArgumentException e) {
			throw new DynamicInvocationException(e);
		} catch (IllegalAccessException e) {
			throw new DynamicInvocationException(e);
		} catch (InvocationTargetException e) {
			throw new DynamicInvocationException(e);
		}
	}

	/**
	 * �ж�source�ǲ���target�ӿڵ�ʵ����
	 * 
	 * @param source
	 *            Դ��
	 * @param target
	 *            Ŀ����
	 * @return ���source��target��ʵ���򷵻�true�����򷵻�false
	 */
	public static boolean isImplementationOf(Class<?> source, Class<?> target) {
		Class<?>[] intercs = source.getInterfaces();
		for (Class<?> clazz : intercs) {
			if (clazz.equals(target)) {
				return true;
			}
		}
		Class<?> parent = source;
		while (!parent.equals(Object.class)) {
			parent = source.getSuperclass();
			intercs = parent.getInterfaces();
			source = parent;
			for (Class<?> clazz : intercs) {
				if (clazz.equals(target)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * �ж�source�ǲ���target������
	 * 
	 * @param source
	 *            Դ��
	 * @param target
	 *            Ŀ����
	 * @return ���source��target��ʵ���򷵻�true�����򷵻�false
	 */
	public static boolean isSubclassOf(Class<?> source, Class<?> target) {
		if (source.equals(target)) {
			return true;
		}
		Class<?> parent = source;
		while (!parent.equals(Object.class)) {
			parent = source.getSuperclass();
			if (parent.equals(target)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �ж�source�ǲ���target���������ʵ����
	 * 
	 * @param source
	 *            Դ��
	 * @param target
	 *            Ŀ����
	 * @return ���source��target��ʵ���򷵻�true�����򷵻�false
	 */
	public static boolean isSubclassOrImplementationOf(Class<?> source, Class<?> target) {
		boolean isImpl = isImplementationOf(source, target);
		return isImpl ? isImpl : isSubclassOf(source, target);
	}

	/**
	 * ����Ŀ������ָ�����Ե�setter����
	 * 
	 * @param target
	 *            Ŀ�����
	 * @param property
	 *            ������
	 * @param arg
	 *            ����
	 * @param argType
	 *            ��������
	 */
	public static void invokeSetter(Object target, String property, Object arg,
			Class<?> argType) {
		Class<?> c = target.getClass();
		String methodName = setterMethodName(property);
		try {
			Method method = c.getMethod(methodName, argType);
			method.invoke(target, arg);
		} catch (SecurityException e) {
			throw new DynamicInvocationException(e);
		} catch (NoSuchMethodException e) {
			throw new DynamicInvocationException(e);
		} catch (IllegalArgumentException e) {
			throw new DynamicInvocationException(e);
		} catch (IllegalAccessException e) {
			throw new DynamicInvocationException(e);
		} catch (InvocationTargetException e) {
			throw new DynamicInvocationException(e);
		}
	}

	/**
	 * ��ȡ���Ե�setter������
	 * 
	 * @param property
	 *            ������
	 * @return
	 */
	private static String setterMethodName(String property) {
		StringBuilder sb = new StringBuilder("set");
		sb.append(StringUtils.firstCharToUppercase(property));
		String methodName = sb.toString();
		return methodName;
	}

	/**
	 * ����Ŀ������ָ�����Ե�setter����
	 * 
	 * @param target
	 *            Ŀ�����
	 * @param property
	 *            ������
	 * @param arg
	 *            ����
	 */
	public static void invokeSetter(Object target, String property, Object arg) {
		String methodName = setterMethodName(property);
		Method[] methods = target.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class<?> argType = method.getParameterTypes()[0];
				if (argType.isInstance(arg)) {
					invokeSetter(target, property, arg, argType);
					return;
				}
			}
		}
	}

	/**
	 * �ж�һ�����Ƿ����
	 * 
	 * @param className
	 *            ����
	 * 
	 * @return
	 */
	public static boolean classExists(String className) {
		if (StringUtils.isEmpty(className)) {
			return Boolean.FALSE;
		}
		try {
			Class.forName(className);
			return Boolean.TRUE;
		} catch (ClassNotFoundException e) {
			return Boolean.FALSE;
		}
	}

    public static Object invokeRunDataParameterMethodWithObject(Object screen, Method method,
            RequestContext requestContext, Object actionInvokeResult) {
        try {
            return method.invoke(screen, requestContext, actionInvokeResult);
        } catch (Exception e) {
            throw new DynamicInvocationException(e);
        }
        
    }

    public static Object invokeRunDataParameterMethod(Object screen, Method method,
            RequestContext requestContext) {
        try {
            return method.invoke(screen, requestContext);
        } catch (Exception e) {
            throw new DynamicInvocationException(e);
        }
    }
}
