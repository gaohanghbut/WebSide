package cn.hang.mvc.type.impl;

import cn.hang.common.util.StringUtils;
import cn.hang.mvc.type.TypeConvertException;
import cn.hang.mvc.type.TypeConverter;

/**
 * ½«×Ö·û´®×ª»»³ÉDouble
 * 
 * @author Hang
 *
 */
public class DoubleConverter implements TypeConverter<Double, String> {

	@Override
	public Double convert(String arg) {
		if (StringUtils.isEmpty(arg)) {
			return 0.0;
		}
		try {
			return Double.valueOf(arg);
		} catch (NumberFormatException e) {
			throw new TypeConvertException(e);
		}
	}

}
