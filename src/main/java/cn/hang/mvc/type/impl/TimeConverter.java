package cn.hang.mvc.type.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Time;

import cn.hang.mvc.common.util.StringUtils;
import cn.hang.mvc.type.TypeConvertException;
import cn.hang.mvc.type.TypeConverter;

/**
 * ½«String×ª»»³Éjava.util.Date
 * 
 * @author Hang
 *
 */
public class TimeConverter implements TypeConverter<Time, String> {

	@Override
	public Time convert(String arg) {
		if (StringUtils.isEmpty(arg)) {
			return new Time(System.currentTimeMillis());
		}
		try {
			if (StringUtils.isEmpty(arg)) {
				return null;
			}
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			return new Time(fmt.parse(arg).getTime());
		} catch (NumberFormatException e) {
			throw new TypeConvertException(e);
		} catch (ParseException e) {
			throw new TypeConvertException(e);
		}
	}

}
