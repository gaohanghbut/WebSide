package cn.hang.mvc.type.impl;

import cn.hang.common.util.StringUtils;
import cn.hang.mvc.type.TypeConvertException;
import cn.hang.mvc.type.TypeConverter;

/**
 * ��Stringת����Byte
 * 
 * @author Hang
 *
 */
public class ByteConverter implements TypeConverter<Byte, String> {

	@Override
	public Byte convert(String arg) {
		if (StringUtils.isEmpty(arg)) {
			return 0;
		}
		try {
			return Byte.valueOf(arg);
		} catch (NumberFormatException e) {
			throw new TypeConvertException(e);
		}
	}

}
