package cn.hang.mvc.common.util;

/**
 * �ַ�������������
 * 
 * @author GaoHang
 * 
 */
public abstract class StringUtils {

	private StringUtils() {
	}

	/**
	 * ��Դ�ַ�������������ʽ���
	 * 
	 * @param source
	 *            Դ�ַ���
	 * @param regex
	 *            ����ַ����ı�ǣ�������������ʽ
	 * @return ��ֺ���ַ�������
	 */
	public static String[] split(String source, String regex) {
		if (source == null || source.trim().equals("")) {
			return new String[0];
		}
		return source.split(regex);
	}

	/**
	 * �ַ����п�
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
	 * �ַ�������ĸ��Ϊ��д
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
	 * �ַ�������ĸ��Ϊ��д
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
	 * ��һ���ַ��������һ��Ŀ���Ӵ��滻���µ��ַ���
	 * 
	 * @param source
	 *            Դ�ַ���
	 * @param target
	 *            Ŀ�괮������Ҫ�滻���Ӵ�
	 * @param replaced
	 *            �滻�ɵ��´�
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
