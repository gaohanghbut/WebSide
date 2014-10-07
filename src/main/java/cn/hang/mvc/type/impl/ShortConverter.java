package cn.hang.mvc.type.impl;

import cn.hang.common.util.StringUtils;
import cn.hang.mvc.type.TypeConvertException;
import cn.hang.mvc.type.TypeConverter;

/**
 * ½«String×ª»»³ÉShort
 * 
 * @author Hang
 *
 */
public class ShortConverter implements TypeConverter<Short, String> {

	@Override
	public Short convert(String arg) {
		if (StringUtils.isEmpty(arg)) {
			return 0;
		}
		try {
			return Short.valueOf(arg);
		} catch (NumberFormatException e) {
			throw new TypeConvertException(e);
		}
	}

}
