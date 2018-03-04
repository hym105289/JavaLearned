package danei.JAVASE01.day05.collection.ListSet;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

/**
 * 栈,也可以存储一组元素,但是遵循先进后出原则. 实现类是双端队列.
 * 通常使用栈来实现"后退"功能.
 * Created by jinhua.chen on 2018/3/4.
 */
public class StackDemo {
    public static void main(String[] args) {
        Deque<String> stack = new LinkedList<String>();
        stack.push("one");
        stack.push("two");
        stack.push("three");
        stack.push("four");
        stack.push("five");
        System.out.println(stack);

        String str = stack.pop();
        System.out.println(str);
        System.out.println(stack);

        String strP = stack.peek();
        System.out.println(strP);
        System.out.println(stack);
    }
}
