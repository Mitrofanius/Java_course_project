package cz.cvut.fel.pjv.bomberplane.gameobjects;


import cz.cvut.fel.pjv.bomberplane.Model;

import java.awt.*;

/**
 * Abstract class which is extended by and enemy's vehicles
 */
public abstract class Vehicle extends MainGameObj{

    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }

    Image picLeft, picRight;
    Bullet bullet = null;
    boolean bulletActive = false;
    private int width, height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Bullet getBullet() {
        return bullet;
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


    private int transferOffset = 10;

    public void setDefaultSpeed(int defaultSpeed) {
        this.defaultSpeed = defaultSpeed;
    }

    int defaultSpeed;

    private int speedX, speedY;

    public boolean isTruck() {
        return false;
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
        }

        this.setPositionX(this.getPositionX() + this.getSpeedX());
        this.setPositionY(this.getPositionY() + this.getSpeedY());
        if ((this.getPositionX() + 3 * transferOffset) >= width) {
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
