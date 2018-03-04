package danei.JAVASE01.day05.collection.ListSet;

import java.util.LinkedList;
import java.util.Queue;

/**
 * java.util.Queue 接口 - 队列
 * 先进先出
 * Created by jinhua.chen on 2018/3/4.
 */
public class QueueDemo {
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<String>();

        // offer() 向队列列尾添加元素
        queue.offer("one");
        queue.offer("two");
        queue.offer("three");
        queue.offer("four");
        queue.offer("five");
        System.out.println(queue);

        //E poll() 从队列首部取出元素,获取后该元素从队列里移除
        String str = queue.poll();
        System.out.println(str);
        System.out.println(queue);

        // E peek() 从队列首部引用元素,获取后该元素仍在队列里
        String strQ = queue.peek();
        System.out.println(strQ);
        System.out.println(queue);

        // 遍历集合元素
        for (String s:queue){
            System.out.println(s);
        }
        System.out.println(queue);
    }

}
