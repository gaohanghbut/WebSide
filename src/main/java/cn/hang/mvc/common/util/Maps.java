package cn.hang.mvc.common.util;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.buffer.PriorityBuffer;
import org.springframework.util.Assert;

/**
 * 与Map相关的工具类
 * 
 * @author hang.gao
 * 
 */
public class Maps {

    private Maps() {
    }

    /**
     * 在指定的Map中获取key的值，如果key对应的value不存在，则将key:add加入到map中，如果存在，则在value上增加add，
     * 然后再加入到Map中
     * 
     * @param map
     *            存储计数的Map
     * @param key
     *            需要修改计数的key
     * @param add
     *            要增加的数量
     */
    public static <T> void putOrIncrease(Map<T, Integer> map, T key, Integer add) {

        Assert.notNull(map, "The map is null!");
        if (add == null) {
            return;
        }
        Integer count = map.get(key);
        if (count == null) {
            count = add;
        } else {
            count += add;
        }
        map.put(key, count);
    }

    /**
     * 取得一个计数Map的前n大的子map
     * 
     * @param countMap
     *            计数的map
     * @param n
     *            表示前几，用于限定名次
     * @return 如果map的大小比n小则返回map本身，如果n小于或者等于0则返回空map，否则返回前n大的元素的子map
     */
    @SuppressWarnings("unchecked")
    public static <K> Map<K, Integer> getFirstNMax(Map<K, Integer> countMap, int n) {
        Set<Entry<K, Integer>> set = countMap.entrySet();
        //大顶堆
        PriorityBuffer buffer = new PriorityBuffer(true, new Comparator<Entry<K, Integer>>() {

            public int compare(Entry<K, Integer> en, Entry<K, Integer> encmp) {
                // value大的在前
                if (en.getValue() > encmp.getValue()) {
                    return -1;
                } else if (en.getValue() < encmp.getValue()) {
                    return 1;
                }
                return 0;
            }
        });
        buffer.addAll(set);
        Map<K, Integer> result = new LinkedHashMap<K, Integer>();
        Entry<K, Integer> en;
        for (int i = 0; i < n; i++) {
            //pop
            en = (Entry<K, Integer>) buffer.remove();
            result.put(en.getKey(), en.getValue());
        }
        return result;
    }

}
