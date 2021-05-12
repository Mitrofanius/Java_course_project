package cz.cvut.fel.pjv.bomberplane.gameobjects;


import java.awt.*;
import cz.cvut.fel.pjv.bomberplane.gameobjects.Building.Bonus;
/**
 * Several rewards can be gained by the player
 * on every level, they will improve his firepower
 * */
public class FloatingReward {
    private int positionX;
    private int positionY;
    private int speedX = 2;
    private int speedY = 1;
    private boolean caught;
    private Image picture;
    private Bonus benefit;
    int dir = 1;

    public Bonus getBenefit() {
        return benefit;
    }

    public void setBenefit(Bonus benefit) {
        this.benefit = benefit;
    }

//    enum Bonus {
//        PLUSBOMBS,
//        ATOMIC,
//        FIREPOWER
//    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setCaught(boolean caught) {
        this.caught = caught;
    }

    public boolean isCaught() {
        return caught;
    }

    public Image getPicture() {
        return picture;
    }

    public FloatingReward(Image img, int x, int y, Bonus bn){
        positionX = x;
        positionY = y;
        picture = img;
        caught = false;
        benefit = bn;
    }

    public void move(){
//        positionX += speedX * dir;
        positionY -= speedY;
        dir *= -1;

    }
}
