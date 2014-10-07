package cn.hang.mvc.type.impl;

import cn.hang.common.util.StringUtils;
import cn.hang.mvc.type.TypeConvertException;
import cn.hang.mvc.type.TypeConverter;

/**
 * ½«×Ö·û´®×ª»»³ÉLong
 * 
 * @author GaoHang
 * 
 */
public class LongConverter implements TypeConverter<Long, String> {

	@Override
	public Long convert(String arg) {
		if (StringUtils.isEmpty(arg)) {
			return 0L;
		}
		try {
			return Long.valueOf(arg);
		} catch (NumberFormatException e) {
			throw new TypeConvertException(e);
		}
	}

}
