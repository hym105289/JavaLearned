package danei.oop02.day01.interface_unionpay;

import java.util.Scanner;

/**
 * Created by jinhua.chen on 2018/2/23.
 */
public class TestUnionPay {
    public static void main(String args[]){

//        ICBC icbc = new ICBCImpl("123456",2000.0);
//        Scanner scan = new Scanner(System.in);
//        System.out.println("请输入密码");
//        String input = scan.next();
//        if (icbc.checkPwd(input)){
//            System.out.println("请输入金额");
//            double draws = Double.parseDouble(scan.next());
//            if (icbc.drawMoney(draws)){
//                System.out.println("取钱成功,卡余额为:" + icbc.getBalance());
//            }else {
//                System.out.println("取钱失败,余额不足");
//            }
//        }else {
//            System.out.println("密码错误");
//        }


        ABC abc = new ABCImpl(2000.0,"123456");
        Scanner scan = new Scanner(System.in);
        System.out.println("请输入密码");
        String input = scan.next();
        if (abc.checkPwd(input)){
            System.out.println("请输入金额");
            double draws = Double.parseDouble(scan.next());
            if (abc.drawMoney(draws)){
                System.out.println("取钱成功,卡余额为:" + abc.getBalance());
            }else {
                System.out.println("取钱失败,余额不足");
            }
        }else {
            System.out.println("密码错误");
        }

    }
}
