package com.kevin.redis.test;

import java.util.Scanner;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:AssertTest
 * @Description: TODO
 */
public class AssertTest {

    public static void main(String[] args) {

        test(1);
    }

    /**
     * 断言检查
     *
     * @param a
     */
    public static void test(int a) {
        assert a > 0 : "输入小于或等于0";
        System.out.println("断言正确，继续执行");
    }
}
