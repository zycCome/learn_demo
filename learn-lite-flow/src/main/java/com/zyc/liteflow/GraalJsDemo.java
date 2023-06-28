package com.zyc.liteflow;

import javax.script.*;

/**
 * @Description
 * @Author zilu
 * @Date 2023/4/10 7:57 PM
 * @Version 1.0.0
 **/
public class GraalJsDemo {

    public static void main(String[] args) {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine jsEngine = engineManager.getEngineByName("js");
        Invocable funcCall = (Invocable) jsEngine;

        try {

            // 方式一，默认设置变量
            jsEngine.put("hello", "jack");
            // 方式二，使用binding设置变量
            SimpleBindings bindings = new SimpleBindings();
            bindings.put("hello","world");
            jsEngine.setBindings(bindings, ScriptContext.GLOBAL_SCOPE);
            // 方式三，使用Context设置变量
            ScriptContext scriptContext = jsEngine.getContext();
            scriptContext.setAttribute("hello", "polo", ScriptContext.ENGINE_SCOPE);

            // 方式一，直接调用
            jsEngine.eval("print(hello)");
            // 方式二，编译调用(需要重复调用，建议先编译后调用)
            if (jsEngine instanceof Compilable){
                CompiledScript compileScript = ((Compilable) jsEngine).compile("print(hello)");
                if (compileScript != null){
                    for (int i = 0; i < 10; i++) {
                        compileScript.eval();
                    }
                }
            }
            Invocable invocable = ((Invocable)jsEngine);
            // 方式三，使用JavaScript中的顶层方法
            jsEngine.eval("function greet(name) { print('Hello, ' + name); } ");
            invocable.invokeFunction("greet", "tom");
            // 方式四，使用某个对象的方法
            jsEngine.eval("var obj = { getGreeting: function(name){ return 'hello, ' + name; } } ");
            Object obj = jsEngine.get("obj");
            Object result = invocable.invokeMethod(obj, "getGreeting", "kitty");
            System.out.println(result);
//            // 方法五, 指定脚本中的方法为Java接口的实现
//            jsEngine.eval("function getGreeting(name) { return 'Hello, ' + name; } ");
//            Greet greet = invocable.getInterface(Greet.class);
//            String result2 = greet.getGreeting("Alex");
//            System.out.println(result2);


//            jsEngine.eval("");
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("a", "abc");
//
//            Object res= funcCall.invokeFunction("f", jsonObject);
//            System.out.println("invoke param: " + jsonObject);
//            System.out.println("invoke result type: " + res.getClass().getName());
//            System.out.println("invoke result: " + res);
//            System.out.println("invoke result: " + JSON.toJSONString(res));
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }  catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
