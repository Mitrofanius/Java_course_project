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

    String bon;

    public Image getPicture() {
        return picture;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }

    public int getPositionX() {
        return positionX;
    }

    public String getBon() {
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
            bon = BonusType.PLUSBOMB;
        } else if (benefit.equals("ATOMIC")) {
            bon = BonusType.ATOMIC;
        }
        else{
            bon = BonusType.FIREPOWER;
        }

    }

    public void actionWhenDestroyed() {
    }
}
