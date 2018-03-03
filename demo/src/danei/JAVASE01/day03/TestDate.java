package danei.JAVASE01.day03;

/**
 * Created by jinhua.chen on 2018/3/3.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * 需求:程序启动后要求用户输入自己的生日,格式如:1992-08-20,输出到今天为止生活了多少天
 */
public class TestDate {
    public static void main(String args[]) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入您的生日:(yyyy-mm-dd)");
        String birthday = scanner.next();

        if (birthday.matches("\\d{4}-\\d{1,2}-\\d{1,2}")){
            String str = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(str);
            Date date = sdf.parse(birthday);
            long birthdayTime = date.getTime();
//            System.out.println(birthdayTime);

            Date now = new Date();
            long nowTime = now.getTime();
//            System.out.println(nowTime);


            long lifeTime = nowTime - birthdayTime;
            System.out.println(lifeTime);
            long dayLiveDay = lifeTime/1000/60/60/24;
            System.out.println("从出生到现在,您共生活了:" + dayLiveDay + "天");
            long dayLiveYear = dayLiveDay/365;
            System.out.println("从出生到现在,您共生活了:" + dayLiveYear + "年");

        }else {
            System.out.printf("输入格式错误");
        }

    }

}
