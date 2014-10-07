package cn.hang.mvc.type;

/**
 * ����ת����
 * 
 * @author GaoHang
 * 
 * @param <Type>
 *            Ҫת��������
 * @param <Arg>
 *            ��ת��������
 */
public interface TypeConverter<Type, Arg> {

	/**
	 * ִ������ת��
	 * 
	 * @param arg
	 *            ��ת�������Ͷ���
	 * @return ת��������Ͷ���
	 */
	Type convert(Arg arg);
}
