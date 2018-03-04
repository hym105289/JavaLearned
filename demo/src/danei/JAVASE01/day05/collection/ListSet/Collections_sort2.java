package danei.JAVASE01.day05.collection.ListSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * String 类型数据排序
 * 排序规则:按首字母的 ACSII 码从小到大排序
 * Created by jinhua.chen on 2018/3/4.
 */
public class Collections_sort2 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("admin");
        list.add("book");
        list.add("Alice");
        list.add("jerry");
        list.add("Jhon");
        list.add("Tom");
        System.out.println(list);

        Collections.sort(list);
        System.out.println(list);
    }

}
