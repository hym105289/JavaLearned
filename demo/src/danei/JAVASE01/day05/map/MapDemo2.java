package danei.JAVASE01.day05.map;

import java.util.*;
import java.util.Map.Entry;

/** map 的遍历
 * map 的遍历有3种: 遍历所有的 key,遍历每一组键值对,遍历 value(不常用,可以用 List 等其他)
 * Created by jinhua.chen on 2018/3/4.
 */
public class MapDemo2 {
    public static void main(String[] args) {
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("语文",100);
        map.put("数学",99);
        map.put("英语",80);

        /* 遍历所有的 key
        Set(K) keySet() 该方法会将 map 中所有的 key 存入set 集合后返回,所以遍历这个 set 集合就相当于遍历所有的 key.
         */
        Set<String> keySet = map.keySet();
        System.out.println(keySet);

        for (String s:keySet){
            System.out.println("key:" + s);
        }

        /*
        遍历 map 中的每一组键值对.
        Map 有一个内部类 Entry,其每一个实例表示一个键值对.
        Set<Entry> entrySet()
         */

        Set<Entry<String,Integer>> entrySet = map.entrySet();
        for (Entry<String,Integer> entry: entrySet){
//            System.out.println("entry:" + entry);
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key + ": " + value);
        }

        /*
        遍历 map 中所有的 value.
        value 值可能会重复,所以方法返回值是 Collection 接口类型.
        Collection<V> values()
         */
        Collection<Integer> values = map.values();
        for (Integer v:values){
            System.out.println("value:" + v);
        }
    }
}
