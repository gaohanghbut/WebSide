package cn.hang.mvc.common.util;

/**
 * 生成器的接口，用于生某个对象
 * 
 * @author hang.gao
 * 
 * @param <T>
 *            需要生成的对象类型
 */
public interface Generator<T> {

    /**
     * 生成一个T类型的对象，对象的生成方式可以是随机或者其它规则
     * 
     * @return 返回生成的对象
     */
    T next();
}
