package cn.hang.mvc.type.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.hang.mvc.common.util.StringUtils;
import cn.hang.mvc.type.TypeConvertException;
import cn.hang.mvc.type.TypeConverter;

/**
 * ½«String×ª»»³Éjava.util.Date
 * 
 * @author Hang
 *
 */
public class DateConverter implements TypeConverter<Date, String> {

	@Override
	public Date convert(String arg) {
		if (StringUtils.isEmpty(arg)) {
			return new Date();
		}
		try {
			if (StringUtils.isEmpty(arg)) {
				return null;
			}
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			return fmt.parse(arg);
		} catch (NumberFormatException e) {
			throw new TypeConvertException(e);
		} catch (ParseException e) {
			throw new TypeConvertException(e);
		}
	}

}
