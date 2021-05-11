package cz.cvut.fel.pjv.bomberplane.gameobjects;

import cz.cvut.fel.pjv.bomberplane.Main;

import java.awt.*;

/**
 * Enemy's vehicle, can shoot
 *
 * @see Vehicle
 */
public class Tank extends Vehicle {
    Image picUp;

    int[] map = new int[650];


    public Tank(Image picl, Image picr, Image picu, int x, int y, int speed) {

        setPicLeft(picl);
        setPicRight(picr);
        this.picUp = picu;
        if (speed > 0) {
            this.setPicture(picr);
        } else {
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
//    @Override
//    public void move() {
//        if (this.getPositionX() > 490 && this.getPositionX() < 560) {
//            if (this.getPositionX() > 490 && this.getPositionX() < 525) {
//                if (this.getSpeedX() > 0) {
//                    setSpeedY(-2);
//                    setSpeedX(3);
//                } else {
//                    setSpeedY(2);
//                    setSpeedX(-3);
//                }
//            }
//            if (this.getPositionX() > 525 && this.getPositionX() < 570) {
//                if (this.getSpeedX() > 0) {
//                    setSpeedY(2);
//                    setSpeedX(3);
//
//                } else {
//                    setSpeedY(-2);
//                    setSpeedX(-3);
//
//                }
//                ;
//            }
////            if (map[this.getPositionX()] == 0) {
////                setSpeedY(0);
////            }
////            this.setSpeedX((getSpeedX() / 3) * 5);
//        }
//
//        this.setPositionX(this.getPositionX() + this.getSpeedX());
//        this.setPositionY(this.getPositionY() + this.getSpeedY());
//        if ((this.getPositionX() + 3 * transferOffset) >= Main.panelWidth) {
//            this.setSpeedX(-this.getSpeedX());
//        }
//        if ((this.getPositionX() + transferOffset) <= 0) {
//            this.setSpeedX(-this.getSpeedX());
//        }
//        this.setSpeedY(0);
//        this.setSpeedX((getSpeedX() / 3) * 3);
//
//    }

    @Override
    public void shoot() {
        // TODO
    }
}
