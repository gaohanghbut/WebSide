package cn.hang.mvc.common.reflect;

import java.util.Map;

/**
 * 设置指定对象的属性值
 * 
 * @author Hang
 * 
 */
public interface PropertyValueSetter {

	/**
	 * 设置属性值
	 * 
	 * @param target
	 *            目标对象
	 * @param properties
	 *            属性与属性值的映射
	 */
	void setProperties(Object target, Map<String, ?> properties, PropertyNotFoundHandler handler);
	
	/**
	 * 设置属性值
	 * 
	 * @param target
	 *            目标对象
	 * @param properties
	 *            属性与属性值的映射
	 */
	void setProperties(Object target, Map<String, ?> properties);

	/**
	 * 设置单个属性值
	 * 
	 * @param property
	 *            属性名
	 * @param val
	 *            属性值
	 */
	void setProperty(Object target, String property, Object val, PropertyNotFoundHandler handler);
	
	/**
	 * 设置单个属性值
	 * 
	 * @param property
	 *            属性名
	 * @param val
	 *            属性值
	 */
	void setProperty(Object target, String property, Object val);

}
