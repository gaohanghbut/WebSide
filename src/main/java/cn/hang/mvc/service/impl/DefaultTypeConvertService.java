package cn.hang.mvc.service.impl;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.hang.mvc.service.ServiceException;
import cn.hang.mvc.service.TypeConvertService;
import cn.hang.mvc.type.TypeConvertException;
import cn.hang.mvc.type.TypeConverter;

/**
 * 类型转换服务
 * 
 * @author Hang
 *
 */
public class DefaultTypeConvertService implements TypeConvertService {
	
	/**
	 * 类型与类型转换器的class对象的映射
	 */
	private Map<Class<?>, Class<?>> typeMapping;
	
	/**
	 * 类型的class对象与转换器对象的映射
	 */
	private Map<Class<?>, Object> typeConverters = new ConcurrentHashMap<Class<?>, Object>();

	@Override
	public void service() {
		if (typeMapping == null) {
			return;
		}
		//初始化转换器
		for (Map.Entry<Class<?>, Class<?>> e : typeMapping.entrySet()) {
			try {
				typeConverters.put(e.getKey(), e.getValue().newInstance());
			} catch (Exception e1) {
				throw new ServiceException(e1);
			}
		}
		typeConverters = Collections.unmodifiableMap(typeConverters);
	}

	@Override
	public Object convert(String source, Class<?> c) throws TypeConvertException {
		if (c == null || c.equals(String.class)) {
			return source;
		}
		//检查是否是基本数据类型
		if (!typeConverters.containsKey(c)) {
			return null;
		}
		@SuppressWarnings("unchecked")
		TypeConverter<?, String> converter = (TypeConverter<?, String>) typeConverters.get(c);
		if (converter != null) {
			return converter.convert(source);
		}
		return null;
	}

	@Override
	public void setTypeConverterMapping(Map<Class<?>, Class<?>> mapping) {
		this.typeMapping = mapping;
	}

}
