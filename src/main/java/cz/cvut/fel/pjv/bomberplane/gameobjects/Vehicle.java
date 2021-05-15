package cz.cvut.fel.pjv.bomberplane.gameobjects;


import cz.cvut.fel.pjv.bomberplane.Main;
import cz.cvut.fel.pjv.bomberplane.Model;

import java.awt.*;

/**
 * Abstract class which is extended by player's plane class and enemy's vehicles
 */
public abstract class Vehicle extends MainGameObj{
//    private Image picture;

    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }

    public void setBulletActive(boolean bulletActive) {
        this.bulletActive = bulletActive;
    }

    Image picLeft, picRight;
    Bullet bullet = null;
    boolean bulletActive = false;

    public Bullet getBullet() {
        return bullet;
    }

    public boolean isBulletActive() {
        return bulletActive;
    }

    public Image getPicLeft() {
        return picLeft;
    }

    public void setPicLeft(Image picLeft) {
        this.picLeft = picLeft;
    }

    public Image getPicRight() {
        return picRight;
    }

    public void setPicRight(Image picRight) {
        this.picRight = picRight;
    }

    public int getTransferOffset() {
        return transferOffset;
    }

    public void setTransferOffset(int transferOffset) {
        this.transferOffset = transferOffset;
    }

    private int speed;
    private int dir;
    private int transferOffset = 10;

    public int getDefaultSpeed() {
        return defaultSpeed;
    }

    public void setDefaultSpeed(int defaultSpeed) {
        this.defaultSpeed = defaultSpeed;
    }

    int defaultSpeed;

//    public Image getPicture() {
//        return picture;
//    }
//
//    public void setPicture(Image picture) {
//        this.picture = picture;
//    }

//    private int positionX;
//    private int positionY;
    private int radius;
    private int speedX, speedY;
    private boolean dying = false;

    public boolean isTruck() {
        return false;
    }

    public boolean isDying() {
        return dying;
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }

    public int getSpeed() {
        return speed;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getDir() {
        return dir;
    }

    public void shoot() {
    }

    public void move() {
        if (this.getPositionX() > 490 && this.getPositionX() < 560) {
            if (this.getPositionX() > 490 && this.getPositionX() < 525) {
                if (this.getSpeedX() > 0) {
                    setSpeedY(-2);
                    setSpeedX(3);
                } else {
                    setSpeedY(2);
                    setSpeedX(-3);
                }
            }
            if (this.getPositionX() > 525 && this.getPositionX() < 570) {
                if (this.getSpeedX() > 0) {
                    setSpeedY(2);
                    setSpeedX(3);

                } else {
                    setSpeedY(-2);
                    setSpeedX(-3);

                }
                ;
            }
//            if (map[this.getPositionX()] == 0) {
//                setSpeedY(0);
//            }
//            this.setSpeedX((getSpeedX() / 3) * 5);
        }

        this.setPositionX(this.getPositionX() + this.getSpeedX());
        this.setPositionY(this.getPositionY() + this.getSpeedY());
        if ((this.getPositionX() + 3 * transferOffset) >= Model.width) {
            this.setSpeedX(-this.getSpeedX());
            changeDirPic();
        }
        if ((this.getPositionX() + transferOffset) <= 0) {
            this.setSpeedX(-this.getSpeedX());
            changeDirPic();
        }
        this.setSpeedY(0);
        this.setSpeedX((getSpeedX() / 3) * defaultSpeed);

    }

    public void changeDirPic(){
        if (getPicture().equals(getPicRight())){
            setPicture(getPicLeft());
            return;
        }
        setPicture(getPicRight());
    }
    public boolean isTank(){
        return false;
    }
}
