package danei.oop02.day03.shootgame.src;

import java.util.Random;

/**
 * Created by jinhua.chen on 2018/2/23.
 */
public class AirPlane extends FlyingObject implements Enemy {
    private int speed = 3;

    public AirPlane(){

        this.image = ShootGame.airplane;
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
        Random random = new Random();
        this.x = random.nextInt(ShootGame.WIDTH-this.width);
        this.y =  this.height;
    }

    @Override
    public int getScore() {
        return 5;
    }

    @Override
    public void step() {
        this.y += this.speed;
    }

    @Override
    public boolean outOfBounds() {
        if (this.y > ShootGame.HEIGHT){
            return true;
        }
        return false;
    }
}
