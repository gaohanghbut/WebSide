package cn.hang.mvc.type.impl;

import cn.hang.common.util.StringUtils;
import cn.hang.mvc.type.TypeConvertException;
import cn.hang.mvc.type.TypeConverter;

/**
 * ½«×Ö·û´®×ª»»³ÉFloat/float
 * 
 * @author Hang
 *
 */
public class FloatConverter implements TypeConverter<Float, String> {

	@Override
	public Float convert(String arg) {
		if (StringUtils.isEmpty(arg)) {
			return 0f;
		}
		try {
			return Float.valueOf(arg);
		} catch (NumberFormatException e) {
			throw new TypeConvertException(e);
		}
	}

}
