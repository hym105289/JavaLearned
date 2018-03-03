package danei.oop02.day02.duotai_atm;

import java.util.Scanner;

/**
 * Created by jinhua.chen on 2018/2/23.
 */
public class ABCATM {
    private UnionPay card;

    public void insertCard(UnionPay card){
        if (this.card == null){
            this.card = card;
        }
    }
    public void outCard(){
        this.card = null;
    }

    public void atmCheckPwd(){
        Scanner scan = new Scanner(System.in);
        System.out.println("请输入密码:");
        String pwd = scan.next();
        if (this.card.checkPwd(pwd)){
            allMenu();
        }else {
            System.out.println("密码错误");
        }
        scan.close();
    }

    public void atmDraw(){
        Scanner in = new Scanner(System.in);
        System.out.println("请输入金额:");
        double draws = Double.parseDouble(in.next());
        if (this.card.drawMoney(draws)){
            System.out.println("取款成功");
        }else {
            System.out.println("取款失败");
        }
        in.close();
    }
    public void atmShowBalance(){
        this.card.getBalance();
    }

    public void atmPayTelBill(){

        if (this.card instanceof ABC){
            ABC abc = (ABC) card;
            Scanner inp = new Scanner(System.in);
            System.out.println("请输入电话号码:");
            String telNo = inp.next();
            System.out.println("请输入缴费金额:");
            double telBill = Double.parseDouble(inp.next());
            inp.close();
            if (abc.payTelBill(telNo,telBill)){
                System.out.println("缴费成功");
            }else {
                System.out.println("缴费失败");
            }
        }else {
            System.out.println("您的卡不是农业银行,无法完成缴费");
        }

    }
    private void allMenu() {
        Scanner input = new Scanner(System.in);
        System.out.println("请选择功能:\n1.查看余额 2.取款 3.缴电话费");
        int choice = Integer.parseInt(input.next());
        switch (choice){
            case 1:
                atmShowBalance();
                break;
            case 2:
                atmDraw();
                break;
            case 3:
                atmPayTelBill();
                break;
            default:
                System.out.println(" 输入有误");
                break;
        }
        input.close();
    }
}
