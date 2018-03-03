package danei.oop02.day02.duotai_atm;

/**
 * Created by jinhua.chen on 2018/2/23.
 */
public class TestAtm {
    public static void main(String args[]){

        ABCATM abcatm = new ABCATM();
        UnionPay card = new ABCImpl("123456",2000);
        abcatm.insertCard(card);
        abcatm.atmCheckPwd();
    }
}
