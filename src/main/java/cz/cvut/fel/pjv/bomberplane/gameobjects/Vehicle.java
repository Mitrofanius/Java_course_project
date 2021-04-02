package cz.cvut.fel.pjv.bomberplane.gameobjects;


/**
 * A class which is extended by player's plane class and enemy's vehicles
 * */
public class Vehicle {
    private int speed;
    private int dir;
    private int positionX;
    private int positionY;
    private int radius;

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getDir() {
        return dir;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getPositionY() {
        return positionY;
    }

    public void shoot (){}
    public void move (){}
}
