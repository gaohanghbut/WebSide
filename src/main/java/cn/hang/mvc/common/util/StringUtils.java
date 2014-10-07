package cn.hang.mvc.common.util;

/**
 * 字符串操作工具类
 * 
 * @author GaoHang
 * 
 */
public abstract class StringUtils {

	private StringUtils() {
	}

	/**
	 * 将源字符串按照正则表达式拆分
	 * 
	 * @param source
	 *            源字符串
	 * @param regex
	 *            拆分字符串的标记，可以是正则表达式
	 * @return 拆分后的字符串数组
	 */
	public static String[] split(String source, String regex) {
		if (source == null || source.trim().equals("")) {
			return new String[0];
		}
		return source.split(regex);
	}

	/**
	 * 字符串判空
	 * 
	 * @param source
	 * @return
	 */
	public static boolean isEmpty(String source) {
		return source == null || source.trim().equals("");
	}

	public static boolean isNotEmpty(String source) {
		return !isEmpty(source);
	}

	/**
	 * 字符串首字母变为大写
	 * 
	 * @param source
	 * @return
	 */
	public static String firstCharToUppercase(String source) {
		StringBuilder sb = new StringBuilder(source);
		char first = sb.charAt(0);
		if (first > 'a' && first < 'z') {
			sb.setCharAt(0, (char) (first - 32));
		}
		return sb.toString();
	}

	/**
	 * 字符串首字母变为大写
	 * 
	 * @param source
	 * @return
	 */
	public static String firstCharToLowercase(String source) {
		StringBuilder sb = new StringBuilder(source);
		char first = sb.charAt(0);
		if (first >= 'A' && first <= 'Z') {
			sb.setCharAt(0, (char) (first + 32));
		}
		return sb.toString();
	}

	/**
	 * 将一个字符串中最后一个目标子串替换成新的字符串
	 * 
	 * @param source
	 *            源字符串
	 * @param target
	 *            目标串，是需要替换的子串
	 * @param replaced
	 *            替换成的新串
	 * @return
	 */
	public static String replaceLast(String source, String target, String replaced) {
		int index = source.lastIndexOf(target);
		if (index < 0) {
			return null;
		}
		if (source.length() <= index + target.length()) {
			return source.substring(0, index) + replaced;
		}
		return source.substring(0, index) + replaced + source.substring(index + target.length());
	}
}
