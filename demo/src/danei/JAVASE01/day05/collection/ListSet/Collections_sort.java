package danei.JAVASE01.day05.collection.ListSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Java 提供了一个工具类 Collections(注意和 Collection 接口的区别)
 * Collections 工具类提供了很多静态方法,sort()是其中一个方法,用于 List 排序
 * Created by jinhua.chen on 2018/3/4.
 */
public class Collections_sort {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            Random rand = new Random();
            list.add(rand.nextInt(100));
        }
        System.out.println(list);

        Collections.sort(list);
        System.out.println(list);
    }

}
