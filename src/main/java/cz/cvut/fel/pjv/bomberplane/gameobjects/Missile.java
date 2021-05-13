package cz.cvut.fel.pjv.bomberplane.gameobjects;

import cz.cvut.fel.pjv.bomberplane.Main;
import cz.cvut.fel.pjv.bomberplane.Model;

import java.awt.*;

/**
 * An object of missile Class will be created
 * in a shoot method of Plane class
 * and then interact with other vehicles
 */
public class Missile extends MainGameObj{
    private int firepower;
//    private int positionX;
//    private int positionY;
    private Image picture;
    private boolean explosion = false;

    private int speedX;
    private int speedY = 5;

    public int getFirepower() {
        return firepower;
    }

//    public int getPositionX() {
//        return positionX;
//    }
//
//    public int getPositionY() {
//        return positionY;
//    }

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

    public boolean isActive() {
        return active;
    }

    public boolean isExplosion() {
        return explosion;
    }

    public Missile(int x, int y, int speed, Image pic) {
        active = true;
        setPositionX(x + 15);
        setPositionY(y + 15);
//        speedX = speed - speed / 3;
        speedX = speed;
        picture = pic;
    }

    public void move() {
        setPositionX(getPositionX() + speedX);
        setPositionY(getPositionY() + speedY);
        if (getPositionY() >= 310) {
            explosion = true;
            active = false;
        }
        if (getPositionX() >= Model.width - 5) {
            setPositionX(0);
        }
        else if (getPositionX() <= 0) {
            setPositionX(Model.width - 6);
        }
    }

}
