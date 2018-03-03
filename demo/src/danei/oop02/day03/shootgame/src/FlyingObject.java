package danei.oop02.day03.shootgame.src;

import java.awt.image.BufferedImage;

/**
 * Created by jinhua.chen on 2018/2/23.
 */
public abstract class FlyingObject {

    protected BufferedImage image;
    protected int width;
    protected int height;
    protected int x;
    protected int y;

    public abstract void step();
    public abstract boolean outOfBounds();

    /* 检测敌人是否被子弹击中
    * this: 敌人
    *
    * */
    public boolean shootBy(Bullet bt){
        int x1 = this.x;
        int x2 = this.x + this.width;
        int y1 = this.y;
        int y2 = this.y + this.height;

        if ( bt.x >= x1 && bt.x<= x2 && bt.y>=y1 && bt.y<= y2){
            return true;
        }else {return false;}
    }


}
