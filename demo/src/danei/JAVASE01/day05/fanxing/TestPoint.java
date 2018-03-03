package danei.JAVASE01.day05.fanxing;

import danei.JAVASE01.day05.fanxing.Point;
import danei.JAVASE01.day05.fanxing.Point2;

/**
 * 测试泛型的应用
 * Created by jinhua.chen on 2018/3/3.
 */
public class TestPoint {
    public static void main(String args[]){

        Point<Integer> p = new Point<Integer>(1,2);
        int x1 = p.getX();
        System.out.println(x1);

        Point<Double> p2 = new Point<Double>(1.1,2.2);
        double x2 = p2.getX();
        System.out.println(x2);

        Point<String> p3 = new Point<String>("一","二");
        String x3 = p3.getX();
        System.out.println(x3);

        Point2<Integer,Double> p21 = new Point2<Integer, Double>(10,22.2);
        double y = p21.getY();
        System.out.println(y);

    }
}
