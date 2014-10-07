package cn.hang.mvc.common.util;

import org.w3c.dom.Element;

/**
 * ����XML��ǩ������
 * 
 * @author GaoHang
 * 
 */
public class TagNameUtils {

	private TagNameUtils() {
	}

	/**
	 * ����ǩ��ǰ��������ռ�ȥ��
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
