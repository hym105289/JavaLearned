package danei.JAVASE01.day05.collection.ListSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 排序自定义类型元素
 * Created by jinhua.chen on 2018/3/4.
 */
public class Collections_sort3 {
    public static void main(String[] args) {

        List<Points> list = new ArrayList<Points>();
        list.add(new Points(1,2));
        list.add(new Points(2,3));
        list.add(new Points(3,2));
        list.add(new Points(2,2));
        list.add(new Points(1,2));
        list.add(new Points(5,1));
        System.out.println(list);

        /*
        以下代码报错,sort 方法的对象需要实现 comparable 接口
         */
        Collections.sort(list);
        System.out.println(list);
    }
}
