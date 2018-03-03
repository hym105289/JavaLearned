package danei.oop02.day02.duotai_atm;

/**
 * Created by jinhua.chen on 2018/2/23.
 */
public interface UnionPay {

    boolean checkPwd(String input);
    boolean drawMoney(double money);
    double getBalance();

}
