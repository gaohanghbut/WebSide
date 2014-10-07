package cn.hang.mvc.common.util;

/**
 * 框架中的常量
 * 
 * @author Hang
 *
 */
public class FrameworkConstants {

	/**
	 * Action类名需要以ACTION_SUFIX结尾
	 */
	public static final String ACTION_SUFIX = "Action";
	
	/**
	 * Screen类名需要以SCREEN_SUFIX结尾
	 */
	public static final String SCREEN_SUFIX = "Screen";
	
	/**
	 * Screen中默认执行执行的方法名
	 */
	public static final String SCREEN_METHOD_NAME = "execute";
	
	/**
	 * 每一个模块的Action类所在的包名需要以.action结尾
	 */
	public static final String MODULE_ACTION_PACKAGE_NAME_SUFIX = "action";
	
	/**
	 * 每一个模块的Action类对应的Screen类所在的包名需要以.screen结尾，Screen与Action的包名除了.screen和.action不同外
	 * 其它部分应该相同
	 */
	public static final String MODULE_SCREEN_PACKAGE_NAME_SUFIX = "screen";
}
