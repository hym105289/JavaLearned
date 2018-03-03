package danei.oop02.day02.duotai_atm;


/**
 * Created by jinhua.chen on 2018/2/23.
 */
public interface ABC extends UnionPay {
    int DELT = -2000;
    boolean payTelBill(String TelNo, double telBill);
}
