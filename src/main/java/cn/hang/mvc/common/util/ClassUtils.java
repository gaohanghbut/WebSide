package cn.hang.mvc.common.util;

/**
 * 基本数据类型到包装器类型的转换
 * 
 * @author Hang
 * 
 */
public abstract class ClassUtils {

	/**
	 * 将基本数据类型的class对象转换成包装器类的class对象
	 * 
	 * @param c
	 *            基本数据类型的class对象
	 * @return 包装器类型的class对象
	 */
	public static Class<?> to(Class<?> c) {
		if (c == null) {
			return null;
		}
		if (c.equals(int.class)) {
			return Integer.class;
		}
		if (c.equals(float.class)) {
			return Float.class;
		}
		if (c.equals(double.class)) {
			return Double.class;
		}
		if (c.equals(short.class)) {
			return Short.class;
		}
		if (c.equals(byte.class)) {
			return Byte.class;
		}
		if (c.equals(long.class)) {
			return Long.class;
		}
		if (c.equals(char.class)) {
			return Character.class;
		}
		return c;
	}
}
