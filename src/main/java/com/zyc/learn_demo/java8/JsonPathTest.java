package com.zyc.learn_demo.java8;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author zilu
 * @Date 2023/6/12 5:57 PM
 * @Version 1.0.0
 **/
public class JsonPathTest {


    public static void main(String[] args) {
        try {
            // 创建JsonPath表达式和对应的值
            String jsonPath = "$.person";
            String value = "wang";

            // 创建目标JSON对象
            Map<String, Object> targetJson = new HashMap<>();

            // 解析JsonPath表达式，并将值设置到目标JSON对象中
            DocumentContext targetJsonContext = JsonPath.parse(targetJson);
            targetJsonContext.set(jsonPath, value);

            // 将目标JSON对象转换为JSON字符串
            String targetJsonString = targetJsonContext.jsonString();

            // 打印目标JSON字符串
            System.out.println(targetJsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
