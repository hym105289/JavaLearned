package danei.oop02.day03.shootgame.src;

/**
 * Created by jinhua.chen on 2018/2/23.
 */
public interface Award {

    // 英雄机的奖励是随机的,可能是生命值,可能是双倍火力
    // 设置常量 DOUBLE_FIRE 为0,LIFE 为1
    int DOUBLE_FIRE = 0;
    int LIFE = 1;


    int getType();
}
