package com.zyc;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.System.out;

/**
 * @Description
 * @Author zilu
 * @Date 2023/5/25 8:20 PM
 * @Version 1.0.0
 **/
public class FeatureTest {

    /**
     * JDK 10 支持使用 var 简化局部变化的类型声明，比如：
     */
    public void testVar() {
        // 显然 arrayList 是一个 ArrayList<Integer> 类型。
        ArrayList<Integer> arrayList = new ArrayList<>();

        var arrayList2 = new ArrayList<Integer>();

    }

    /**
     * JDK 11 新增了部分对字符串的处理方法。
     */
    @Test
    public void testStringEnhance() {
        out.println("".isBlank());      // 判断字符串是否为 "".
        out.println("  welcome to java 17 ".strip());  // 去掉首尾空格
        out.println("  welcome to java 17".stripLeading()); // 去掉首部空格
        out.println("welcome to java 17 ".stripTrailing()); // 去掉尾部空格
        out.println("word".repeat(2));     // "wordword"

    }


    @FunctionalInterface
    interface Mapper<A,B>{
        B map(A a);
    }

    /**
     * JDK 11 允许将 var 关键字应用到 lambda 表达式中，起到简化声明的作用。比如：
     */
    public void testLambdaVar() {
        // var 关键字现可以用于 lambda 表达式。
        Mapper<String,Integer> string2int = (var a) -> a.length();
    }


    /**
     * 增强版 switch 在 JDK 12 作为预览特性引入。在 JDK 14 之后，增强版 switch 语句块具备返回值。
     */
    @Test
    public void testSwitch() {
        String lang = "java";
        var simple = switch (lang) {
            case "java" -> "j";
            case "go"   -> "g";
            default -> "<non>";
        };
        out.println(simple);


        lang = "groovy";
        /**
         * 在新版本的 switch 语句中，分支创建可以使用 -> 符号，
         * 且 cases 的判断不会引发穿透现象，因此不需要显式地在每一个分支后面标注 break 了。下面例子展示了增强 switch 的更多特性：
         */
        var simple2 = switch (lang) {
            // 1. 允许在一个 case 中匹配多个值
            case "java","scala","groovy" -> "jvm";
            case "c","cpp" -> "c";
            case "go"   -> "g";
            default -> {
                // 2. 复杂分支内返回值使用 yield 关键字 (相当于 return)
                if(lang == null){
                    yield "<unknown>";
                }else yield "<non>";
            }
        };

        out.println(simple2);

    }

    /**
     * 文本块 TextBlock
     * JDK 13 允许使用三引号 """ 表示长文本内容。
     */
    @Test
    public void testTextBlock() {
        var block = """
        lang: java   
        version: 13
        dbname: mysql  
        ip: 192.168.140.2    
        usr: root
        pwd: 1000 
        """;

        // 6 行
        out.println(block.lines().count());


        // 文本块内允许插入 \ 阻止换行，如：
        //另外注意，每行末尾的空格会被忽略，除非主动将其替换为 /s。
        var block2 = """
        lang: java\
        version: 13\
        dbname: mysql\
        ip: 192.168.140.2\
        usr: root\
        pwd: 1000
        """;
        // 实际效果：
        // "lang:javaversion: 13dbname: mysql..."
        // 1 行
        out.println(block2.lines().count());
    }


    /**
     * jdk16: instanceof 模式匹配
     * 如果一个上转型对象的具体类型已经被 instanceof 关键字确定，那么其后续操作可省略强制转换，见下面的示例：
     */
    @Test
    public void testInstanceof() {
        // o 可能是 String 类型或者是 Double 类型。
        Object o = new Random().nextInt() % 2 == 0 ?  "java16" : 1000.0d;

        // s 相当于被确定类型之后的 o。
        if(o instanceof String s){
            // 不再需要 ((String)o).length()
            System.out.println(s.length());
        }else {
            System.out.println();
        }


    }


    /**
     * jdk16: Records
     * 被 record 关键字修饰的类相当于 Scala 的样例类 case class，或者可看成是 Lombok 中 @Data 注解的一个 "低配" 替代。
     * 编译器会一次性代替用户生成 getters，constructors，toString 等方法。
     */
    @Test
    public void testRecords() {
        // ....
        var student = new Student("Wang Fang",13);
        student.age();
        student.name();


        var student2 = new Student("Wang Fang",12);
        // 没有get set？
        student2.age();
        student2.name();
    }

    record Student(String name,Integer age){}


    // 密封类
    public sealed class People {}

    // 密封类 People 必须至少有一个子类。
    // 非密封的 People 子类
    non-sealed class Teacher extends People{}

    // 密封的 People 子类。
    sealed class Driver extends People{}

    non-sealed class TruckDriver extends Driver{}

    /**
     * JDK 17 对 switch 语句做了进一步增强，它现在支持匹配类型以及判空的功能：
     */
    @Test
    public void testSwitch2() {
        Number v1 = 3.1415d;

        switch (v1) {
            case null -> out.println("null");
            case Float f -> out.println(f);
            case Double d -> out.println(d);
            default ->out.println("NaN");
        }

    }

}
