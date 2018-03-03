package danei.oop02.day01.interface_unionpay;

/**
 * Created by jinhua.chen on 2018/2/23.
 */
public class ABCImpl implements ABC {
    private double money;
    private String pwd;

    public ABCImpl(double money, String pwd) {
        this.money = money;
        this.pwd = pwd;
    }

    @Override
    public boolean payTelBill(String TelNo, double telBill) {
        if (TelNo.length()==11 && ((this.getBalance() - telBill)>DELT)){
            this.money -= telBill;
            return true;
        }
        return false;
    }

    @Override
    public boolean checkPwd(String input) {
        if (pwd.equals(input)){
            return true;
        }
        return false;
    }

    @Override
    public boolean drawMoney(double money) {
        if ((this.getBalance() - money)>DELT){
            this.money -= money;
            return true;
        }
        return false;
    }

    @Override
    public double getBalance() {
        return this.money;
    }
}
