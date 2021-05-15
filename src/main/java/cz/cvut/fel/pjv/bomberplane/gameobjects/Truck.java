package cz.cvut.fel.pjv.bomberplane.gameobjects;


import cz.cvut.fel.pjv.bomberplane.Main;

import java.awt.*;

/** Enemy's vehicle, can't shoot, moves slow
 * @see Vehicle
 */
public class Truck extends Vehicle{
    int[] map = new int[650];

    public Truck(Image picl, Image picr, int x, int y, int speed) {
        setTruck(true);
        setPicLeft(picl);
        setPicRight(picr);
        if (speed > 0) {
            this.setPicture(picr);
        }else{
            setPicture(picl);
        }
        this.setPositionX(x);
        this.setPositionY(y);
        this.setSpeedX(speed);
        this.setSpeedY(0);
        setDefaultSpeed(Math.abs(speed));
        filltheMap();
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
