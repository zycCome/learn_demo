package com.zyc;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;


public class PerfMonXformer implements ClassFileTransformer {


    private final static Map<String, List<String>> methodMap = new HashMap<String, List<String>>();

    public PerfMonXformer() {
    }

    public PerfMonXformer(String agentArgs) {
        System.out.println("Load successfully from args!");
        add(agentArgs);
    }

    private void add(String methodString) {
        String className = methodString.substring(0, methodString.lastIndexOf("."));
        String methodName = methodString.substring(methodString.lastIndexOf(".") + 1);
        List<String> list = methodMap.get(className);
        if (list == null) {
            list = new ArrayList<String>();
            methodMap.put(className, list);
        }
        list.add(methodName);
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        className = className.replace("/", ".");
        if (methodMap.containsKey(className)) {
            CtClass ctclass = null;
            try {
                ctclass = ClassPool.getDefault().get(className);
                CtField startTime = new CtField(CtClass.intType, "startTime", ctclass);
                ctclass.addField(startTime);
                CtField endTime = new CtField(CtClass.intType, "endTime", ctclass);
                ctclass.addField(endTime);

                for (String methodName : methodMap.get(className)) {
                    System.out.println("1111111");
                    CtMethod ctmethod = ctclass.getDeclaredMethod(methodName);
                    ctmethod.insertBefore("startTime = System.currentTimeMillis();");
                    String endSrc = "endTime = System.currentTimeMillis();";
                    endSrc += "System.out.println(\"The method " + ctmethod.getName() + " cost time:\"+(endTime-startTime)+\"ms\");";
                    ctmethod.insertAfter(endSrc);
                }
                return ctclass.toBytecode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}


