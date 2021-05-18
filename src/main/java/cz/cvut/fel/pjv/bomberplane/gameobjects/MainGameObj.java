package cz.cvut.fel.pjv.bomberplane.gameobjects;

import java.awt.*;

/**
 * All game objects have
 * coordinates and a picture.
 *
 * This common ancestor makes it easy
 * to provide effective interface to draw
 * different object with the same method
 * in View.
 */
public abstract class MainGameObj {

    private int positionX;
    private int positionY;
    private Image picture;

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public Image getPicture() {
        return picture;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }
}
