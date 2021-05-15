package cz.cvut.fel.pjv.bomberplane.gameobjects;


import java.awt.*;

/**
 * Abstract class which is extended by player's plane class and enemy's vehicles
 * */
public abstract class PlaneBuilder extends MainGameObj{
    private int speed;
    private int dir;


    private int radius;
    private int speedX, speedY;
    private boolean dying = false;


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

    public void shoot (){}
    public void move (){}
}
