package cn.hang.mvc.type;

/**
 * 类型转换器
 * 
 * @author GaoHang
 * 
 * @param <Type>
 *            要转换的类型
 * @param <Arg>
 *            被转换的类型
 */
public interface TypeConverter<Type, Arg> {

	/**
	 * 执行类型转换
	 * 
	 * @param arg
	 *            被转换的类型对象
	 * @return 转换后的类型对象
	 */
	Type convert(Arg arg);
}
