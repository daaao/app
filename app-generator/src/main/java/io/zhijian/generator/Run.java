package io.zhijian.generator;

import io.zhijian.generator.core.Generator;

/**
 * @author Hao
 * @create 2017-03-27
 */
public class Run {

    public static void main(String[] args) {
        Generator generator = new Generator();
        try {
            generator.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
