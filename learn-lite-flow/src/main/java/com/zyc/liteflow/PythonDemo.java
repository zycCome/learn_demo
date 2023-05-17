package com.zyc.liteflow;

import com.zyc.liteflow.cmp.Hello;
import org.junit.Test;
import org.python.core.Py;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptException;
import java.util.*;

/**
 * @Description
 * @Author zilu
 * @Date 2023/4/11 9:41 AM
 * @Version 1.0.0
 **/
public class PythonDemo {

    public static void main(String[] args) throws ScriptException {
        Properties props = new Properties();
        props.put("python.home", "/Users/zilu/.m2/repository/org/python/jython-standalone/2.7.2/jython-standalone-2.7.2");
        props.put("python.console.encoding", "UTF-8");
        props.put("python.security.respectJavaAccessibility", "false");
        props.put("python.import.site", "false");
        Properties preprops = System.getProperties();
        PythonInterpreter.initialize(preprops, props, new String[0]);

        PyObject pyObject = Py.java2py(new Hello());
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.set("h",pyObject);
        interpreter.exec("vars = globals();print(h.hello1())");


    }



    @Test
    public void testPython() {
        Properties props = new Properties();
        props.put("python.import.site", "false");
        Properties preprops = System.getProperties();
        PythonInterpreter.initialize(preprops, props, new String[0]);
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("# coding=utf-8\n" +
                "def main(str):\n" +
                "  print(str)");
// python脚本中的方法名为main
        PyFunction function = interpreter.get("main",PyFunction.class);
// 将参数代入并执行python脚本
        PyObject o = function.__call__(Py.newString("abccccc"));
        System.out.println(o);
    }


    @Test
    public void testPython2() {
        Properties props = new Properties();
        props.put("python.import.site", "false");
        Properties preprops = System.getProperties();
        PythonInterpreter.initialize(preprops, props, new String[0]);
        PythonInterpreter interpreter = new PythonInterpreter();
        // 创建Python解释器对象

        // 定义Python脚本
        String pythonScript = "import sys\n"
                + "def my_function(param_dict):\n"
                + "    # 在Python中打印字典对象\n"
                + "    print(param_dict)\n"
                + "    # 构造返回值\n"
                + "    ret_dict = {'message': 'Hello from Python!', 'data': param_dict}\n"
                + "    return ret_dict";

        // 在Python解释器中执行脚本
        interpreter.exec(pythonScript);

        // 准备参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("key1", "value1");
        paramMap.put("key2", "value2");

        // 将Java的HashMap转换为Python的字典对象
        PyObject pyDict = Py.java2py(paramMap);

        // 调用Python脚本，并传递参数
        PyObject retObj = interpreter.get("my_function").__call__(pyDict);

        // 将Python的字典对象转换为Java的HashMap对象
        Map<String, Object> retMap = Py.tojava(retObj, Map.class);

        // 打印返回值
        System.out.println(retMap);
    }

    private static Logger logger = LoggerFactory.getLogger(PythonDemo.class);

    /**
     * 使用jython运行py代码，缺点：一旦引用第三方库容易报错，而即便手动设置第三方库，也有可能出现错误
     * @param script python解析代码
     * @param params python代码中的参数
     * @return
     */
    public static Map<String,Object> runPythonByJython(String script, String params){
        Map<String,Object> rtnMap = new HashMap<>();

        Properties props = new Properties();
        props.put("python.import.site", "false");
        Properties preprops = System.getProperties();
        PythonInterpreter.initialize(preprops, props, new String[0]);
        PythonInterpreter interpreter = new PythonInterpreter();
//        // 下面是加入jython的库，需要指定jar包所在的路径。如果有自己写的包或者第三方，可以继续追加
//        interpreter.exec("import sys");
//        interpreter.exec("sys.path.append('C:/jython2.7.1/Lib')");
//        interpreter.exec("sys.path.append('C:/jython2.7.1/Lib/site-packages')");
        try {
            interpreter.exec(script);
            // 假设python有一个main方法，包含所有实现需求的代码。相当于Java程序入口main方法
            PyFunction function = interpreter.get("main",PyFunction.class);
            // 将报文代入并执行python进行解析
            PyObject o = function.__call__(Py.newString(params));
            rtnMap.put("result",o);
            interpreter.cleanup();
            interpreter.close();
        } catch (Exception e) {
            e.printStackTrace();
            rtnMap.put("error",e);
        }
        return rtnMap;
    }

}
