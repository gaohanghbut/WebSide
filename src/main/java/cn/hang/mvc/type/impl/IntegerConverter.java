package cn.hang.mvc.type.impl;

import cn.hang.common.util.StringUtils;
import cn.hang.mvc.type.TypeConvertException;
import cn.hang.mvc.type.TypeConverter;

/**
 * ��������ת���������ַ���ת��������
 * 
 * @author GaoHang
 * 
 */
public class IntegerConverter implements TypeConverter<Integer, String> {

	@Override
	public Integer convert(String arg) {
		if (StringUtils.isEmpty(arg)) {
			return 0;
		}
		try {
			return Integer.valueOf(arg);
		} catch (NumberFormatException e) {
			throw new TypeConvertException(e);
		}
	}

}