package danei.JAVASE01.day04;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by jinhua.chen on 2018/3/3.
 */
public class CollectionDemo2 {
    public static void main(String args[]){
        Collection c = new ArrayList();
        c.add("one");
        c.add("#");
        c.add("two");
        c.add("#");
        c.add("three");
        c.add("#");
        c.add("four");
        c.add("#");

        System.out.println(c);

        Iterator it = c.iterator();
        while (it.hasNext()){
            String str = (String)it.next();
            if ("#".equals(str)){
                it.remove();
            }
        }
        System.out.println(c);
    }
}
