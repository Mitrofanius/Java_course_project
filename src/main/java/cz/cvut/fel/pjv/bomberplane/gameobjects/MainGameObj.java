package cz.cvut.fel.pjv.bomberplane.gameobjects;

import java.awt.*;

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
