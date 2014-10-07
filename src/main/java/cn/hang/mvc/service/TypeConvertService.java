package cn.hang.mvc.service;

import java.util.Map;

import cn.hang.mvc.type.TypeConvertException;

/**
 * ����ת���������ڴ����������
 * 
 * @author Hang
 * 
 */
public interface TypeConvertService extends Service {

	/**
	 * ִ��ת��
	 * 
	 * @param source
	 *            ����ֵ
	 * @param c
	 *            ��Ҫת���ɵ�����
	 * @return ת����Ķ���
	 * @throws TypeConvertException
	 *             ת������ʱ�׳��쳣
	 */
	Object convert(String source, Class<?> c) throws TypeConvertException;

	/**
	 * ����<type, converter>ӳ��
	 * 
	 * @param mapping
	 *            <type, converter>
	 */
	void setTypeConverterMapping(Map<Class<?>, Class<?>> mapping);
}
