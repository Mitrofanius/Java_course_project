package cz.cvut.fel.pjv.bomberplane.gameobjects;

import cz.cvut.fel.pjv.bomberplane.Main;

import java.awt.*;

/**
 * An object of missile Class will be created
 * in a shoot method of Plane class
 * and then interact with other vehicles
 * */
public class Missile {
    private int firepower;
    private int positionX;
    private int positionY;
    private Image picture;
    private boolean explosion = false;

    private int speedX;
    private int speedY = 5;

    public int getFirepower() {
        return firepower;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public Image getPicture() {
        return picture;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public boolean isAtomic() {
        return Atomic;
    }

    private boolean Atomic;
    private boolean active;

    public boolean isActive(){
        return active;
    }

    public boolean isExplosion() {
        return explosion;
    }

    public Missile(int x, int y, int speed, Image pic){
        active = true;
        positionX = x + 15;
        positionY = y + 15;
//        speedX = speed - speed / 3;
        speedX = speed;
        picture = pic;
    }

    public void move() {
        positionX += speedX;
        positionY += speedY;
        if (positionY >= 310) {
            explosion = true;
            active = false;
        }
    }

}
