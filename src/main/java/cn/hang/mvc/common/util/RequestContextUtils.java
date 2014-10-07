package cn.hang.mvc.common.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cn.hang.mvc.RequestContextFactory;

/**
 * ���������Ĺ�����
 * 
 * @author Hang
 *
 */
public abstract class RequestContextUtils {

	/**
	 * ������������Ĺ������б�����ֻ��һ��Ԫ��
	 */
	private static List<RequestContextFactory> REQUEST_CONTEXT_FACTORIES = new LinkedList<RequestContextFactory>();
	
	/**
	 * ע�����������Ĺ�����ע��󹤳��������޸�
	 * 
	 * @param requestContextFactory
	 */
	public static void registerRequestContextFactory(RequestContextFactory requestContextFactory) {
		if (REQUEST_CONTEXT_FACTORIES.size() == 0) {
			synchronized (REQUEST_CONTEXT_FACTORIES) {
				if (REQUEST_CONTEXT_FACTORIES.isEmpty()) {
					REQUEST_CONTEXT_FACTORIES.add(requestContextFactory);
					REQUEST_CONTEXT_FACTORIES = Collections.unmodifiableList(REQUEST_CONTEXT_FACTORIES);
				}
			}
		}
	}
	
	/**
	 * ��ȡ���������Ĺ���
	 * 
	 * @return
	 */
	public static RequestContextFactory getRequestContextFactory() {
		if (REQUEST_CONTEXT_FACTORIES.isEmpty()) {
			return null;
		}
		return REQUEST_CONTEXT_FACTORIES.get(0);
	}
}
