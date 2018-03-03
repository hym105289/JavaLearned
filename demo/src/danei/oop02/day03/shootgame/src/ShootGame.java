package danei.oop02.day03.shootgame.src;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.Timer;


/**
 * Created by jinhua.chen on 2018/2/23.
 */
public class ShootGame extends JPanel {
    public static final int WIDTH = 400; // 窗口的宽
    public static final int HEIGHT = 654; //  窗口的高

    public static BufferedImage airplane;
    public static BufferedImage background;
    public static BufferedImage bee;
    public static BufferedImage bullet;
    public static BufferedImage gameover;
    public static BufferedImage hero0;
    public static BufferedImage hero1;
    public static BufferedImage pause;
    public static BufferedImage start;

    // 加载静态图片资源
    static {
        try{
            airplane = ImageIO.read(ShootGame.class.getResource("airplane.png"));
            background = ImageIO.read(ShootGame.class.getResource("background.png"));
            bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
            bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
            gameover = ImageIO.read(ShootGame.class.getResource("gameover.png"));
            hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
            hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));
            pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
            start = ImageIO.read(ShootGame.class.getResource("start.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Hero hero = new Hero();
    private FlyingObject flyings[] = {};
    private Bullet buls[] = new Bullet[]{};

    private static final int START = 0;
    private static final int RUNING = 1;
    private static final int PAUSE =2;
    private static final int GAMEOVER = 3;
    private int state = ShootGame.START;

    public ShootGame(){}

    public FlyingObject nextOne(){
        Random rand = new Random();
        int count = rand.nextInt(10);
        if (count < 2){
            return new Bee();
        }else {
            return new AirPlane();
        }
    }

    int entryIndex =0;
    public void enterAction() {
        entryIndex ++;
        if (entryIndex % 40== 0){ // 每 400ms 执行1次
            FlyingObject flyingObj = nextOne();
            flyings = Arrays.copyOf(flyings,flyings.length+1);
            flyings[flyings.length-1] = flyingObj;
        }
    }

    public void stepAction(){
        hero.step();
        for (int i=0;i<flyings.length;i++){
            flyings[i].step();
        }
        for (int i=0;i<buls.length;i++){
            buls[i].step();
        }
    }
    int shootIndex = 0;
    public void shootAction(){
        shootIndex ++;
        if (shootIndex % 30 == 0){
            Bullet bs[] = hero.shoot();
            buls = Arrays.copyOf(buls,buls.length+bs.length);
            System.arraycopy(bs,0,buls,buls.length-bs.length,bs.length);
        }
    }
    public void outOfBoundsAction(){

        /* 敌人越界处理 */
        int index = 0;
        FlyingObject enemiesAlive [] = new FlyingObject[flyings.length];
        for (int i =0; i <flyings.length;i++){
            if (!flyings[i].outOfBounds()){
                enemiesAlive[index] = flyings[i];
                index++;
            }
        }
        flyings = Arrays.copyOf(enemiesAlive,index);

        /* 子弹越界处理 */
        int bIndex = 0;
        Bullet bsAlive[] = new Bullet[buls.length];
        for (int i=0;i<buls.length;i++){
            if (! buls[i].outOfBounds()){
                bsAlive[bIndex] = buls[i];
                bIndex++;
            }
        }
        buls = Arrays.copyOf(bsAlive,bIndex);

    }

    /* 所有子弹与所有敌人碰撞 */
    public void bangAction(){
        for (int i=0;i<buls.length;i++){
            bang(buls[i]);
        }
    }

    /* 一个子弹与所有敌人碰撞 */
    int score = 0;
    public void bang(Bullet b){
        int index = -1; // 被撞敌人的下标
        for (int i=0;i<flyings.length;i++){
            if (flyings[i].shootBy(b)){
                index = i;
                break; // 其余敌人不再比较了
            }
        }
        /* 相撞了 */
        if (index!=-1){
            FlyingObject fObj = flyings[index]; // 获取被撞敌人

           /* 判断敌人类型
            * 若是敌机,玩家得分
            * 若是蜜蜂,则玩家得奖励
            * 判断奖励的类型
                    若是命,英雄机得命
                    若是火力值,英雄机得火力值
            */
            if (fObj instanceof Enemy){
                Enemy e = (Enemy) fObj;
                score += e.getScore();
            }
            if (fObj instanceof Award){
                Award a = (Award) fObj;
                int type = a.getType();
                switch(type){
                    case Award.DOUBLE_FIRE:
                        hero.addDoubleFire();
                        break;
                    case Award.LIFE:
                        hero.addLife();
                        break;
                }
            }
            /*
            * 敌人消失
            * */
            for (int i=0;i<flyings.length;i++){
                if (flyings[i] == fObj){
                    flyings[i] = flyings[flyings.length-1];
                    flyings[flyings.length-1] = fObj;
                    // 缩容,去掉最后一个元素
                    flyings = Arrays.copyOf(flyings,flyings.length-1);

                }
            }
        }
    }

    public void hitAction(){
        int index = -1;
        for (int i=0;i<flyings.length;i++){
            // 英雄机与敌人相撞
            if (hero.hitBy(flyings[i])){
                index = i;
                break;
            }
        }

        // 相撞了. 之所以将逻辑拎出来不放在上面的 if 里是为了减少嵌套,增加代码可读性
        if (index != -1){
            // 英雄机减命
            hero.setLife(hero.getLife() -1);
            hero.setDoubleFile(0);
            // 敌人消失
            for (int i=0;i<flyings.length;i++){
                FlyingObject fObj = flyings[index];
                if ( fObj == flyings[i]){
                    flyings[i] = flyings[flyings.length-1];
                    flyings[flyings.length-1] = fObj;
                    flyings = Arrays.copyOf(flyings,flyings.length-1);
                }
             }
        }
    }
    public void checkGameOver(){
        if (hero.getLife()<=0){
            state = GAMEOVER;
        }
    }
    public void ShootByAction() {
        for (int i=0;i<buls.length;i++){
            shootEnemy(buls[i]);
        }
    }

    public void shootEnemy(Bullet b) {
        int shootIndex = -1;
        for (int i=0;i<flyings.length;i++){
            if (flyings[i].shootBy(b)){
                shootIndex = i;
                break;
            }
        }
        if (shootIndex != -1){
            FlyingObject f = flyings[shootIndex];
            if (f instanceof Enemy){
                Enemy s = (Enemy) f;
                score += s.getScore();
            }
            if (f instanceof Award){
                Award a = (Award) f;
                switch (a.getType()){
                    case Award.DOUBLE_FIRE:
                        hero.addDoubleFire();
                        break;
                    case Award.LIFE:
                        hero.addLife();
                        break;
                }
            }
            flyings[shootIndex] = flyings[flyings.length-1];
            flyings[flyings.length-1] = f;
            flyings = Arrays.copyOf(flyings,flyings.length-1);
        }
    }
    public void action(){
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (state){
                    case START:
                        state = RUNING;
                        break;
                    case GAMEOVER:
                        score = 0;
                        hero = new Hero();
                        flyings = new FlyingObject[0];
                        buls = new Bullet[0];
                        state = START;
                        break;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (state == PAUSE){
                    state = RUNING;
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (state == RUNING){
                    state = PAUSE;
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (state==RUNING){
                    int x = e.getX();
                    int y = e.getY();
                    hero.moveTo(x,y);
                }

            }
        };

        this.addMouseListener(ma);// 处理鼠标的操作事件
        this.addMouseMotionListener(ma);//处理鼠标的滑动事件


        Timer timer = new Timer();
        TimerTask ttask = new TimerTask() {
            @Override
            public void run() {
                if (state == RUNING){
                    enterAction();
                    stepAction();
                    shootAction();
                    outOfBoundsAction();
//                    bangAction()
                    ShootByAction();
                    hitAction();
                    checkGameOver();
                }

                repaint();
            }
        };
        timer.schedule(ttask,10,10);
    }



    public void paint(Graphics g){
        g.drawImage(ShootGame.background,0,0,null);
        paintHero(g);
        paintBullets(g);
        paintFlyings(g);
        paintScoreAndLife(g);
        paintState(g);
    }
    public void paintHero(Graphics g) {
        g.drawImage(hero.image,hero.x,hero.y,null);
    }
    public void paintFlyings(Graphics g) {
        for (int i=0;i<flyings.length;i++){
            g.drawImage(flyings[i].image,flyings[i].x,flyings[i].y,null);
        }
    }
    public void paintBullets(Graphics g) {
        for (int i=0;i<buls.length;i++){
            g.drawImage(ShootGame.bullet,buls[i].x,buls[i].y,null);
        }
    }
    public void paintScoreAndLife(Graphics g){
        g.drawString("SCORE: " + score,10,25 );
        g.drawString("LIFE:" + hero.getLife(),10,45);
    }
    public void paintState(Graphics g){
        switch (state){
            case ShootGame.START:
                g.drawImage(ShootGame.start,0,0,null);
                break;
            case ShootGame.PAUSE:
                g.drawImage(ShootGame.pause,0,0,null);
                break;
            case ShootGame.GAMEOVER:
                g.drawImage(ShootGame.gameover,0,0,null);


        }
    }



    public static void main(String args[]){

        JFrame jFrame = new JFrame("Fly");
        ShootGame game = new ShootGame();
        jFrame.add(game);
        jFrame.setSize(ShootGame.WIDTH,ShootGame.HEIGHT);
        jFrame.setAlwaysOnTop(true);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);

        game.action();

    }
}
