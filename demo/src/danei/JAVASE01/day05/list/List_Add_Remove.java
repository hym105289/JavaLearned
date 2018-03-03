package danei.JAVASE01.day05.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jinhua.chen on 2018/3/3.
 */
public class List_Add_Remove {
    public static void main(String args[]){
        List<Integer> list = new ArrayList<Integer>();
        list.add(0);
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.add(50);
        list.add(60);
        list.add(70);
        System.out.println(list);

        // add() 方法
        list.add(8,80);
        System.out.println(list);

        // remove() 方法
        int removed = list.remove(2);
        System.out.println(list);
        System.out.println(removed);

        // 获取子集
        List<Integer> subList = list.subList(3,7);
        System.out.println(subList);

        // 将subList集合中每个元素缩小10倍
        for (int i=0;i<subList.size();i++){
            Integer value = subList.get(i);
            subList.set(i,value/10);
            System.out.println(subList.get(i));
        }
        System.out.println(subList);
        System.out.println(list); // 对子集的修改就是对原集合对应元素的修改.

        // 删除 list 集合中索引 2-8 的元素
        List<Integer> sList = list.subList(2,8);
        sList.clear();
        System.out.println(sList);
        System.out.println(list);
    }
}
