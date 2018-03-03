package danei.JAVASE01.day04;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by jinhua.chen on 2018/3/3.
 */
public class CollectionDemo {
    public static void main(String args[]){
        /*
        contains() 方法
         */
        Collection c = new ArrayList();
        c.add(new Point(1,2));
        c.add(new Point(3,4));
        c.add(new Point(5,6));
        System.out.println(c);

        Point p = new Point(2,3);
        if (c.contains(p)){
            // 是否包含,用的是对象所在类里的 equals 方法,所以元素里 equals 方法直接影响集合的 contains 方法的返回结果
            System.out.println("包含:" + true);
        }else {
            System.out.println("包含:" + false);
        }

        /*
        remove() 方法
        从集合中删除与指定元素 equals 方法比较为 true 的元素. 所以 equals 方法也会影响集合的 remove 方法的返回结果.
        需要注意的是:只会删除1个元素.集合会顺序比较,删除后就会停止删除.即:方法执行1次只删除1个元素,如果有重复的元素,只会删除第1个出现的.
         */
        Point p1 = new Point(1,2);
        c.add(p1);
        System.out.println("集合:" + c);
        c.remove(p1);
        System.out.println("集合:" + c);

        /*
        集合操作
        boolean addAll(Collection c)
        只要集合有数据变化,返回值就是 true,这里并不要求所有的元素都添加了才会返回 true.
         */
        Collection c2 = new ArrayList();
        c2.add("Java");
        c2.add("Python");
        c2.add("oc");

        c.addAll(c2);
        System.out.println("集合:" + c);
    }
}
