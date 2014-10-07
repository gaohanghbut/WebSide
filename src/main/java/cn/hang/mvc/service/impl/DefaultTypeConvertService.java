package cn.hang.mvc.service.impl;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.hang.mvc.service.ServiceException;
import cn.hang.mvc.service.TypeConvertService;
import cn.hang.mvc.type.TypeConvertException;
import cn.hang.mvc.type.TypeConverter;

/**
 * ����ת������
 * 
 * @author Hang
 *
 */
public class DefaultTypeConvertService implements TypeConvertService {
	
	/**
	 * ����������ת������class�����ӳ��
	 */
	private Map<Class<?>, Class<?>> typeMapping;
	
	/**
	 * ���͵�class������ת���������ӳ��
	 */
	private Map<Class<?>, Object> typeConverters = new ConcurrentHashMap<Class<?>, Object>();

	@Override
	public void service() {
		if (typeMapping == null) {
			return;
		}
		//��ʼ��ת����
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
		//����Ƿ��ǻ�����������
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
