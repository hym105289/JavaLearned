package danei.JAVASE01.day05.fanxing;

/**
 * Created by jinhua.chen on 2018/3/3.
 */
public class Point2<X,Y> {
    private X x;
    private Y y;

    public Point2(){}

    public Point2(X x, Y y) {
        super();
        this.x = x;
        this.y = y;
    }

    public X getX() {
        return x;
    }

    public void setX(X x) {
        this.x = x;
    }

    public Y getY() {
        return y;
    }

    public void setY(Y y) {
        this.y = y;
    }
}
