package danei.oop02.day02.duotai_atm;


/**
 * Created by jinhua.chen on 2018/2/23.
 */
public class ICBCImpl implements ICBC {
    private double money;
    private String pwd;

    public ICBCImpl(String pwd, double money) {
        this.pwd = pwd;
        this.money = money;
    }

    @Override
    public boolean payOnline(double bills) {
        if (this.getBalance() > bills){
            this.money -= bills;
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
        if (this.getBalance() > money){
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
