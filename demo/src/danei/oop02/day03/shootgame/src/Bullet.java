package danei.oop02.day03.shootgame.src;

/**
 * Created by jinhua.chen on 2018/2/23.
 */
public class Bullet extends FlyingObject {
    private int speed = 3;

    public Bullet(int x, int y){
        this.image = ShootGame.bullet;
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
        this.x = x;
        this.y = y;
    }

    @Override
    public void step() {
        this.y -= this.speed;
    }

    @Override
    public boolean outOfBounds() {
        if (this.height < - this.height){
            return true;
        }
        return false;
    }
}
