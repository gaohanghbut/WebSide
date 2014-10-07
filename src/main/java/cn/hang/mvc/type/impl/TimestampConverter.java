package cn.hang.mvc.type.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;

import cn.hang.mvc.common.util.StringUtils;
import cn.hang.mvc.type.TypeConvertException;
import cn.hang.mvc.type.TypeConverter;

/**
 * ½«String×ª»»³Éjava.util.Date
 * 
 * @author Hang
 *
 */
public class TimestampConverter implements TypeConverter<Timestamp, String> {

	@Override
	public Timestamp convert(String arg) {
		if (StringUtils.isEmpty(arg)) {
			return new Timestamp(System.currentTimeMillis());
		}
		try {
			if (StringUtils.isEmpty(arg)) {
				return null;
			}
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
			return new Timestamp(fmt.parse(arg).getTime());
		} catch (NumberFormatException e) {
			throw new TypeConvertException(e);
		} catch (ParseException e) {
			throw new TypeConvertException(e);
		}
	}

}
