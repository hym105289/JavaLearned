package danei.oop02.day01.interface_unionpay;

/**
 * Created by jinhua.chen on 2018/2/23.
 */
public interface UnionPay {

    boolean checkPwd(String input);
    boolean drawMoney(double money);
    double getBalance();

}
