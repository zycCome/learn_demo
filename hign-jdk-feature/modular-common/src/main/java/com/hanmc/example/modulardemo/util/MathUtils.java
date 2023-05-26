package com.hanmc.example.modulardemo.util;

/**
 * @Description 接口增加
 * @Author zilu
 * @Date 2023/5/25 8:19 PM
 * @Version 1.0.0
 **/
public interface MathUtils {

    // implements 该接口的类将自动获取该方法。 - jdk 8.
    default void h(){}

    // 在接口定义的属性直接被视为 static。 -jdk 8.
    double Pi = 3.1415;

    // 可以在接口直接定义静态方法。 - jdk 8.
    static void f(){}

    // 可以在接口内直接定义私有方法。 -jdk 9.
    private void g(){}

}
