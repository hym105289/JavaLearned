package danei.JAVASE01.day04;

/**
 * Created by jinhua.chen on 2018/3/3.
 */
public class Point {
    private int x;
    private int y;

    public Point(){}

    public Point(int x, int y) {
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
        return "(" + x + "," + y + ")";
    }
    public boolean equals(Object o){
        if (o == null){
            return false;
        }
        if (o == this){
            return true;
        }
        if (o instanceof Point){
            Point p = (Point) o;
            return this.x == p.x && this.y == p.y;
        }
        return false;
    }
}
