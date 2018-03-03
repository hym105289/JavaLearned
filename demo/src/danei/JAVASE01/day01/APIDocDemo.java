package danei.JAVASE01.day01;


/**
 * 当前类是用于测试文档注释使用的
 * Created by jinhua.chen on 2018/3/1.
 */

/*
    以上是文档注释
    文档注释只在三个地方定义:类\常量\方法(静态方法\构造方法\成员方法)
 */
public class APIDocDemo {

    /**
     * sayHello 中使用的问候语
     */
    public static final String INFO = "你好!";

    /**
     * 给指定参数添加一个问候语
     * @param name 给定的用户的名字
     * @return 含有问候语的字符串
     */
    public static String sayHello(String name){
        return INFO + name;
    }
}
