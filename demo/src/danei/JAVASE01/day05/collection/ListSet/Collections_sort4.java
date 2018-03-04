package danei.JAVASE01.day05.collection.ListSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Collections 提供了一个重载的 sort() 方法,该方法更推荐使用,因为它有2个优点:
 * 1. 不要求实现 Comparable 接口,减少对我们代码的侵入性;
 * 2. 当元素已经实现了 Comparable 接口如 String 这样的类,但是其提供的比较大小规则不满足我们对排序的需求如对中文排序,也可以使用重载的 sort 方法解决.
 *
 * 该重载的方法要求传入要比较的集合外,还要传入一个额外的比较器,用来比较集合元素.
 * Created by jinhua.chen on 2018/3/4.
 */
public class Collections_sort4 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("英语");
        list.add("语文老师");
        list.add("数学的");

        System.out.println(list);

        /*
        重载的sort 方法不要求实现 Comparable 接口,如果实现了也不会按照Comparable 的规则比较元素大小.
         */
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });
        System.out.println(list);
    }
}


