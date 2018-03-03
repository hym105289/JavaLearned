package danei.JAVASE01.day05.list;

import java.util.ArrayList;
import java.util.List;

/** java.util.List 是 Collection 的子接口
 * List 除了可重复外还有一个特性,就是有序,所以提供了一套可以根据下标查找元素的方法.
 * 常用实现类:
 * java.util.ArrayList 数组实现,查询更快
 * java.util.LinkedList 链表实现,增删更快
 * Created by jinhua.chen on 2018/3/3.
 */
public class List_GET_SET {
    public static void main(String args[]){
        List<String> list = new ArrayList<String>();
        list.add("one");
        list.add("two");
        list.add("three");
        System.out.println(list);

        String str = list.get(1);
        System.out.println(str);

        for (int i=0; i<list.size(); i++){
            System.out.println(list.get(i));
        }

        String old = list.set(2,"jinhua");
        System.out.println(list);
        System.out.println(old);
    }
}
