package cz.cvut.fel.pjv.bomberplane.gameobjects;

import java.awt.*;

public class Killer extends MainGameObj{
    int defaultSpeed = 5;
    int shootTimer;
    int timeCounter = 0;

    private void increaseCounter(){
        timeCounter += 1;
    }

    public int getDefaultSpeed() {
        return defaultSpeed;
    }

    public void setDefaultSpeed(int defaultSpeed) {
        this.defaultSpeed = defaultSpeed;
    }

    public boolean isActive(){
        if (timeCounter > shootTimer){
            return true;
        }
        else{
            increaseCounter();
            return false;
    }}

    public void setShootTimer(int shootTimer) {
        this.shootTimer = shootTimer;
    }

    public int getShootTimer() {
        return shootTimer;
    }

    public Killer(Image pic, int x, int y, int timer){
        setPositionX(x);
        setPositionY(y);
        setPicture(pic);
        shootTimer = timer;
    }

    public void move(){
        setPositionY(getPositionY() - defaultSpeed);
    }
}
