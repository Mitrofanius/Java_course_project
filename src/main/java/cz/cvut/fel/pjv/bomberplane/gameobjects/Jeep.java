package cz.cvut.fel.pjv.bomberplane.gameobjects;

import cz.cvut.fel.pjv.bomberplane.Main;

import java.awt.*;

/**
 * Enemy's vehicle, can't shoot, moves fast
 *
 * @see Vehicle
 */
public class Jeep extends Vehicle {
    int[] map = new int[650];


    public Jeep(Image pic, int x, int y, int speed) {
        this.setPicture(pic);
        setPicLeft(pic);
        setPicRight(pic);
        this.setPositionX(x);
        this.setPositionY(y);
        this.setSpeedX(speed);
        this.setSpeedY(0);
        filltheMap();
        setDefaultSpeed(Math.abs(speed));
    }

    public void filltheMap() {
        for (int i = 0; i < 650; i++) {
            map[i] = 0;
        }
        for (int i = 490; i < 525; i++) {
            map[i] = 1;
        }
        for (int i = 535; i < 570; i++) {
            map[i] = -1;
        }
    }

    public void lookTheMap() {

    }

    @Override
    public void move() {
        super.move();

    }
}
