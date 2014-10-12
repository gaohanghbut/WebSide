package cn.hang.mvc.common.reflect;

/**
 * 调用 PropertySetter上的方法时，需要设置的属性不存在时应该采取的处理方式
 * 
 * @author Hang
 * 
 */
public interface PropertyNotFoundHandler {

	/**
	 * 处理属性不存在的情况
	 * 
	 * @param property
	 *            要设置的属性
	 * @param value
	 *            属性的值
	 * @return 返回处理结果，可以是null
	 */
	Object handl(String property, Object value);
}
