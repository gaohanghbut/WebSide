package cn.hang.mvc.common.util;

import org.w3c.dom.Element;

/**
 * 处理XML标签工具类
 * 
 * @author GaoHang
 * 
 */
public class TagNameUtils {

	private TagNameUtils() {
	}

	/**
	 * 将标签名前面的命名空间去掉
	 * 
	 * @param tagName
	 * @return
	 */
	public static String getElementName(Element element) {
		String tagName = element.getTagName();
		int index = tagName.indexOf(':');
		if (index < 0) {
			index = 0;
		}
		String elementName = tagName.substring(index + 1);
		return elementName;
	}
}
