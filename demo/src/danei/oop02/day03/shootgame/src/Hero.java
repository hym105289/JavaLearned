package danei.oop02.day03.shootgame.src;

import java.awt.image.BufferedImage;

/**
 * Created by jinhua.chen on 2018/2/23.
 */
public class Hero extends FlyingObject {
    private int life;

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getDoubleFile() {
        return doubleFile;
    }

    public void setDoubleFile(int doubleFile) {
        this.doubleFile = doubleFile;
    }

    private int doubleFile;
    private BufferedImage images[]={};// 图片数组,默认为0个
    private int index; // 协助图片切换

    // 参数是英雄机移动的x 和y 方向的速度.为什么只有这2个参数,因为这两个参数是变化的,其他参数是固定的.
    public Hero(){
        this.image = ShootGame.hero0;
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
        this.x = 180;
        this.y = 540;
        this.life = 3;
        this.doubleFile = 10;
        this.images = new BufferedImage[]{ShootGame.hero0,ShootGame.hero1};
        this.index = 0;

    }

    @Override
    public void step() { // 每10ms 执行1次
        this.index ++;
        int a = this.index / 10;
        int b = a % 2;
        this.image = this.images[b];  // 每100ms 切换1次

        /*
        * 10ms  s=1   a=0     b=0
        * 20      2     0       0
        * 30      3     0       0
        * ...
        * 100     10    1       1
        * 110     11    1       1
        *
        *
        * */
    }

    @Override
    public boolean outOfBounds() {
        return false; // 英雄机不会越界
    }

    /* 子弹入场*/
    public Bullet[] shoot(){
        int xStep = this.width/4;
        int yStep = 20;
        if (this.doubleFile > 0 ){
            Bullet bs[] = new Bullet[2];
            bs[0] = new Bullet(this.x + xStep,this.y -20);
            bs[1] = new Bullet(this.x + 3*xStep,this.y -20);
            doubleFile -= 2;
            return bs;
        }else{
            Bullet bs[] = new Bullet[1];
            bs[0] = new Bullet(this.x + 2*xStep,this.y -20);
            return bs;
        }
    }

    /* 英雄机随着鼠标移动
    * x: 鼠标的 x 坐标
    * y: 鼠标的 y 坐标
    * 根据鼠标所在的点为英雄机的中心点,从而找到英雄机的位置.
    * */
    public void moveTo(int x,int y){
        this.x = x - this.width/2;
        this.y = y - this.height/2;
    }


    public void addLife(){
        this.life ++;
    }
    public void addDoubleFire(){
        this.doubleFile += 40;
    }

    public boolean hitBy(FlyingObject f){
        int x1 = f.x - this.width;
        int x2 = f.x + f.width;
        int y1 = f.y - this.height;
        int y2 = f.y + f.height;
        if ( this.x >= x1 && this.x <=x2 && this.y>=y1 && this.y <= y2){
            return true;
        }
        return false;
    }

}
