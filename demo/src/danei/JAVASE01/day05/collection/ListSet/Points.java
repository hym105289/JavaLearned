package danei.JAVASE01.day05.collection.ListSet;


/**
 * 该类作为集合元素测试 Collections 对集合排序
 * Created by jinhua.chen on 2018/3/4.
 */
public class Points implements Comparable<Points>{
    private int x;
    private int y;

    public Points() {
    }

    public Points(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public String toString(){
        return "(" + this.x + "," + this.y + ")";
    }

    /*
    该方法用来比较当前对象 this 和 参数Points 对象的大小的
     */
    @Override
    public int compareTo(Points o) {
        if (this.x > o.x){
            return 1;
        }else if (this.y > o.y){
            return 1;
        }else if (this.x == o.x && this.y == o.y){
            return 0;
        }else {
            return -1;
        }

    }
}
