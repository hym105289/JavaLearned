package danei.oop02.day03.shootgame.src;

import java.util.Random;

/**
 * Created by jinhua.chen on 2018/2/23.
 */
public class Bee extends FlyingObject implements Award {
    private int xSpeed = 1;
    private int ySpeed = 2;
    private int awardType;

    public Bee(){

        this.image = ShootGame.bee;
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
        Random random = new Random();
        this.x = random.nextInt(ShootGame.WIDTH-this.width);
        this.y =  this.height;

        // 英雄机的奖励是随机的,可能是生命值,可能是双倍火力
        this.awardType = random.nextInt(2);
    }

    @Override
    public int getType() {
        return this.awardType;
    }

    @Override
    public void step() {
        this.y += this.ySpeed;
        if (this.x == ShootGame.WIDTH - this.width){
            this.xSpeed = -1;
        }else{
            this.xSpeed = 1;
        }
        this.x += this.xSpeed;
    }

    @Override
    public boolean outOfBounds() {
        if (this.y > ShootGame.HEIGHT){
            return true;
        }
        return false;
    }
}
