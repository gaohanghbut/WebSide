package cn.hang.mvc.service;

import java.util.Map;

import cn.hang.mvc.type.TypeConvertException;

/**
 * 类型转换服务，用于处理请求参数
 * 
 * @author Hang
 * 
 */
public interface TypeConvertService extends Service {

	/**
	 * 执行转换
	 * 
	 * @param source
	 *            参数值
	 * @param c
	 *            需要转换成的类型
	 * @return 转换后的对象
	 * @throws TypeConvertException
	 *             转换出错时抛出异常
	 */
	Object convert(String source, Class<?> c) throws TypeConvertException;

	/**
	 * 设置<type, converter>映射
	 * 
	 * @param mapping
	 *            <type, converter>
	 */
	void setTypeConverterMapping(Map<Class<?>, Class<?>> mapping);
}
