package com.zyc.learn_demo.algorithm.ryf;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * json对象搜索
 *
 * @author zhuyc
 * @date 2022/03/17 20:55
 **/
public class JsonSearch {

    String json = "{\"奴隶社会\":{\"亚洲\":{\"古印度\":[\"种姓制度\",\"佛教的创立\"],\"两河流域文明\":[\"汉谟拉比法典\"]},\"欧洲\":{\"希腊罗马古典文化\":[\"建筑艺术\",\"公历\"],\"罗马\":[\"城邦\",\"帝国的征服与扩展\"],\"希腊\":[\"希腊城邦\",\"雅典民主\"]},\"非洲\":{\"古埃及文明\":[\"金字塔\"]}}}";

    Map<String, String> map = new HashMap<>();

    @Test
    public void test() {
        // 先遍历一遍所有的key-value
        JSONObject root = JSONUtil.parseObj(json);
        recursionGenerateNode(root, null);
        System.out.println(map.computeIfAbsent("金字塔", k -> "存在的关键字：" + k));
        System.out.println(map.computeIfAbsent("金字塔", k -> "存在的关键字：" + k));
        System.out.println(map.computeIfAbsent("美洲", k -> "不存在的关键字：" + k));
    }

    /**
     * 递归处理
     *
     * @param obj    需要需要的对象，可能说数组或者对象
     * @param prefix 前缀
     */
    public void recursionGenerateNode(Object obj, String prefix) {
        if (obj == null) {
            return;
        }
        if (obj instanceof JSONObject) {
            JSONObject temp = (JSONObject) obj;
            // attr就是一个属性名称+值
            for (Map.Entry<String, Object> attr : temp.entrySet()) {
                String v = prefix == null ? attr.getKey() : prefix + "." + attr.getKey();
                map.put(attr.getKey(), v);
                recursionGenerateNode(attr.getValue(), v);
            }
        } else if (obj instanceof JSONArray) {
            // json数组，不是key-value结构
            JSONArray temp = (JSONArray) obj;
            for (String arrayElement : temp.toList(String.class)) {
                String v = prefix == null ? arrayElement : prefix + "." + arrayElement;
                map.put(arrayElement, v);
            }
        }

    }


}
