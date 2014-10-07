package cn.hang.mvc.type.impl;

import cn.hang.mvc.common.util.StringUtils;
import cn.hang.mvc.type.TypeConvertException;
import cn.hang.mvc.type.TypeConverter;

/**
 * ½«String×ª»»³ÉCharactor
 * 
 * @author Hang
 *
 */
public class CharacterConverter implements TypeConverter<Character, String> {

	@Override
	public Character convert(String arg) {
		if (StringUtils.isEmpty(arg)) {
			return '\t';
		}
		try {
			if (StringUtils.isEmpty(arg)) {
				return null;
			}
			return arg.charAt(0);
		} catch (NumberFormatException e) {
			throw new TypeConvertException(e);
		}
	}

}
