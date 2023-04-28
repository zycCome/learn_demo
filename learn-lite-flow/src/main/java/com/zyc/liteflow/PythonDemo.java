package com.zyc.liteflow;

import com.zyc.liteflow.cmp.Hello;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import javax.script.ScriptException;
import java.util.Properties;

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

}
