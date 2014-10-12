package cn.hang.mvc.common.util;

import cn.hang.common.util.*;
import cn.hang.common.util.Generator;

import java.util.Random;

/**
 * 工具类，作为常用数据类型的生成器类的包装对象
 * 
 * @author hang.gao
 * 
 */
public class RandomGenerators {

    /**
     * 整型随机数生成器
     * 
     * @author hang.gao
     * 
     */
    public static class Integer implements cn.hang.mvc.common.util.Generator<java.lang.Integer> {

        /**
         * 随机数生成器
         */
        private Random random = new Random();

        /**
         * 随机生成一个整型数
         */
        public java.lang.Integer next() {
            return random.nextInt();
        }

    }

    /**
     * 随机生成一个字符串
     *
     * @author hang.gao
     *
     */
    public static class String implements cn.hang.mvc.common.util.Generator<java.lang.String> {
        /**
         * 字符源，从此字符数组中随机生成字母
         */
        private char[] charSource = "stuvwxyzabcdefghijklmnopqr".toCharArray();

        /**
         * 随机数生成器
         */
        private Random random     = new Random();

        /**
         * 生成字符串，生成策略为随机，先随机生成一个大小在100以内的整形数，然后 以此整形数作为字符串的长度，随机生成字符串中的每一个字符
         */
        public java.lang.String next() {
            int length = random.nextInt(100);
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(charSource[random.nextInt(charSource.length)]);
            }
            return builder.toString();
        }

    }
}
