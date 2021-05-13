package cz.cvut.fel.pjv.bomberplane.gameobjects;

import cz.cvut.fel.pjv.bomberplane.Main;
import cz.cvut.fel.pjv.bomberplane.Model;

import java.awt.*;

/**
 * An object of missile Class will be created
 * in a shoot method of Plane class
 * and then interact with other vehicles
 */
public class Bullet {
    private int firepower;
    private int positionX;
    private int positionY;
    private Image picture;
    private boolean explosion = false;

    private int speedX;
    private int speedY = 4;

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

    public boolean isActive() {
        return active;
    }

    public boolean isExplosion() {
        return explosion;
    }

    public Bullet(int x, int y, int speed, Image pic) {
        active = true;
        positionX = x + 15;
        positionY = y;
        speedX = speed;
        picture = pic;
    }

    public void move() {
        positionX += speedX;
        positionY -= speedY;
        if (positionY <= Model.getReachLineHeight()) {
            active = false;
        }
        if (positionX >= Model.width - 5) {
            positionX = 0;
        }
        else if (positionX <= 0) {
            positionX = Model.width - 6;
        }
    }

}
