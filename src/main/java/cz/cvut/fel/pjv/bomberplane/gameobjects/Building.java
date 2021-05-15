package cz.cvut.fel.pjv.bomberplane.gameobjects;


import java.awt.*;

/**
 * Object of that class should be destroyed
 * by the player for a reward on every level
 */
public class Building {
    private int positionX;
    private int PositionY;
    private boolean destroyed;
    private Image picture;

    public enum Bonus {
        PLUSBOMB,
        ATOMIC,
        FIREPOWER
    }

    Bonus bon;

    public Image getPicture() {
        return picture;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }

    public int getPositionX() {
        return positionX;
    }

    public Bonus getBon() {
        return bon;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return PositionY;
    }

    public void setPositionY(int positionY) {
        PositionY = positionY;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public Building(Image img, int x, int y, String benefit) {
        positionX = x;
        PositionY = y;
        destroyed = false;
        picture = img;
        if (benefit.equals("PLUSBOMB")) {
            bon = Bonus.PLUSBOMB;
        } else if (benefit.equals("ATOMIC")) {
            bon = Bonus.ATOMIC;
        }
        else{
            bon = Bonus.FIREPOWER;
        }

    }

    public void actionWhenDestroyed() {
    }
}
