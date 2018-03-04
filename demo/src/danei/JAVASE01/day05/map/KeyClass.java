package danei.JAVASE01.day05.map;

/** 自定义类型元素作为 map 中的 key 使用时,需要重写 equals 和 hashCode 方法. 且
 * equals 返回值为 true 时,hashCode 方法返回值一定要相同. 否则会在 HashMap 里产生链表,影响查询性能.
 *
 * Created by jinhua.chen on 2018/3/4.
 */
public class KeyClass {
    private int x;
    private int y;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KeyClass)) return false;

        KeyClass keyClass = (KeyClass) o;

        if (x != keyClass.x) return false;
        return y == keyClass.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
