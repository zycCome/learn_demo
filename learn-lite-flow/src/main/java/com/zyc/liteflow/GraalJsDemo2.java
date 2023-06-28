package com.zyc.liteflow;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @Description
 * @Author zilu
 * @Date 2023/4/10 7:57 PM
 * @Version 1.0.0
 **/
public class GraalJsDemo2 {

    public static void main(String[] args) {
        System.setProperty("polyglot.engine.WarnInterpreterOnly", "false");


        //allowAllAccess 允许js调用对象的方法
        Context context = Context.newBuilder(new String[0]).allowAllAccess(true).engine(Engine.create()).build();
        Value bindings = context.getBindings("js");
        Value bindings2 = context.getBindings("js");
        Logger logger = Logger.getLogger(GraalJsDemo2.class.getName());
        // 设置日志输出工具对象
        bindings.putMember("logger", logger);

        Map<String,Object> nodeOutput = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("inner","node1");
        JSONObject like = new JSONObject();
        like.set("inner","innerName");
        jsonObject.set("like",like);
        nodeOutput.put("node1",jsonObject);
        bindings.putMember("node", nodeOutput);
//        logger.info("zyc");
        // 默认获取最后一行作为返回值
        Value result = context.eval("js", "var a = 42;var output = {name:\"zyc\",age:11,money:1.1,child:{\"name\":\"childName\"}}; a + 1;logger.info(\"js inner\");node.get('node1').get('like')");

        // 获取js中的变量
        Value a = context.getBindings("js").getMember("output");
        JSONObject output = JSONUtil.parseObj(a.toString());
        System.out.println(jsonObject);

        Value result2 = context.eval("js","console.log(a)");
        bindings.removeMember("logger");
        Set<String> memberKeys = bindings.getMemberKeys();
        for (String memberKey : memberKeys) {
            bindings.putMember(memberKey,null);
        }
        Value result3 = context.eval("js","console.log(a)");

    }


}
