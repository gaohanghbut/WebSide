package cn.hang.mvc.common.util;

/**
 * RunData����ʱ����
 * 
 * @author GaoHang
 * 
 */
public abstract class RequestContextConstants {

	private RequestContextConstants() {
	}

	/**
	 * �������Դ�Ĳ�����
	 */
	public static final String RESOURCE_PARAMETER_NAME = "resource";
	
	/**
	 * �����Action��ִ�еķ���
	 */
	public static final String EVENT_PARAMETER_NAME = "event";
	
	/**
	 * ���ظ����������������
	 */
	public static final String TYPE_PARAMETER_NAME = "type";
	
	/**
	 * Ĭ�Ϸ�����������
	 */
	public static final String DEFAULT_RESULT_TYPE = "html";
	
	/**
	 * �Ƿ��Ѿ���ת
	 */
	public static final String RETURN = "forwarded";
	
	/**
	 * �����Action��
	 */
	public static final String ACTION_PARAMETER_NAME = "action";
	
	/**
	 * action�����а����ĺ�׺�ַ���
	 */
	public static final String ACTION_NAME_SUFIX = "Action";
}
