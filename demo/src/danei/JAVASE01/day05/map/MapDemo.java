package danei.JAVASE01.day05.map;

import java.util.HashMap;
import java.util.Map;

/** java.util.Map
 *  查找表
 *  常用实现类: java.util.HashMap (由散列算法实现的Map)
 * Created by jinhua.chen on 2018/3/4.
 */
public class MapDemo {
    public static void main(String[] args) {
        Map<String,Integer> map = new HashMap<String,Integer>();

        /*
        V put(K k,V v)
        key 不允许重复,如果用已有的 key 存储元素则会替换原有的 value 值,那么 put方法的返回值就是被替换的值,否则返回 null.
        map 的key 是否重复是使用的 key 自身的 equals 方法.
         */
        map.put("语文",98);
        map.put("数学",100);
        map.put("英语",60);
        System.out.println(map);
        Integer num = map.put("英语",70);
        System.out.println(num);
        System.out.println(map);

        /*
        V get(K k)
        若指定的 key 不存在则返回 null.
         */
        Integer num1 = map.get("语文");
        System.out.println("num1 =" + num1);
        Integer num2 = map.get("体育");
        System.out.println(num2);

        /*
        V remove(K k)
         */
        Integer num3 = map.remove("语文");
        System.out.println("num3 = " + num3);
        System.out.println(map);
        Integer num4 = map.remove("体育");
        System.out.println("num4 = "+num4);
    }
}
