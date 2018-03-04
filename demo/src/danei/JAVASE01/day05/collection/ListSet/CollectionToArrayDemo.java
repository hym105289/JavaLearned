package danei.JAVASE01.day05.collection.ListSet;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 集合转数组
 *
 * Created by jinhua.chen on 2018/3/3.
 */
public class CollectionToArrayDemo {
    public static void main(String args[]){
        Collection<String> list = new ArrayList<String>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
        list.add("five");
        System.out.println(list);

        String arr[] = list.toArray(new String[list.size()]);
        for (int i=0; i<arr.length; i++){
            System.out.println(arr[i]);
        }

    }
}
