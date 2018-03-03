package danei.JAVASE01.day05.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数组转集合
 * 使用数组的工具类Arrays 的 asList 方法
 * Created by jinhua.chen on 2018/3/3.
 */
public class ArrayToListDemo {
    public static void main(String args[]){
        String arr[] = {"1","2","3","4","jinhua"};

        List<String> list = Arrays.asList(arr);
        System.out.println(list);
        System.out.println(list.size());
        list.set(4,"5");
        System.out.println(list);

        // 对集合元素的操作就是对原数组相对应元素的操作
        for (String str:arr){
            System.out.println(str);
        }

        // 从数组转换过来的集合是不能添加新元素的
        // 因为对集合的操作就是对原数组操作, 添加元素需要数组扩容,从而表示不能表示原数组
//        list.add("six");

        List<String> list1 = new ArrayList<String>();
        System.out.println(list1);
        list1.addAll(list);
        list1.add("six");
        System.out.println("list1:" + list1);
        System.out.println(list1.size());
        System.out.println(list1.get(5));
        list1.add(6,"6");
        System.out.println(list1);
    }
}
