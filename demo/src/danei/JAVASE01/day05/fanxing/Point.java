package danei.JAVASE01.day05.fanxing;

/**
 * 泛型,又称参数化类型.
 * Created by jinhua.chen on 2018/3/3.
 */
public class Point<T> {
    private T x;
    private T y;



    public Point(){}
    public Point(T x, T y) {
        super();
        this.x = x;
        this.y = y;
    }

    public T getX() {
        return x;
    }

    public void setX(T x) {
        this.x = x;
    }

    public T getY() {
        return y;
    }

    public void setY(T y) {
        this.y = y;
    }

}
