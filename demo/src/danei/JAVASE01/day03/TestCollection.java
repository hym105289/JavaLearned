package danei.JAVASE01.day03;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by jinhua.chen on 2018/3/3.
 */
public class TestCollection {
    public static void main(String args[]){

        /*
        add() 方法
         */
        Collection c = new ArrayList();
        c.add("one");
        c.add("two");
        c.add("three");
        c.add("four");
        System.out.println("集合内容:" + c);

        /*
         int size()方法
         */
        int size = c.size();
        System.out.println("集合容量:" + size);

        boolean isE = c.isEmpty();
        System.out.println("集合是否为空:" + isE);

        c.clear();
        System.out.println("集合内容:" + c);
        System.out.println("集合是否为空:" + c.isEmpty());
    }


}
