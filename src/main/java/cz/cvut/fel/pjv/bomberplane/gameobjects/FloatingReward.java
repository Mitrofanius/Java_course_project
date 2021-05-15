package cz.cvut.fel.pjv.bomberplane.gameobjects;


import java.awt.*;
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
    private String benefit;
    int dir = 1;

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }


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

    public FloatingReward(Image img, int x, int y, String bn){
        positionX = x;
        positionY = y;
        picture = img;
        caught = false;
        benefit = bn;
        System.out.println(benefit + " benefit");
    }

    public void move(){
        positionY -= speedY;
        dir *= -1;

    }
}
