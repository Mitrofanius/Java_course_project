package cz.cvut.fel.pjv.bomberplane.gameobjects;


/**
 * Plane object to be controlled by a user
 * */
public class Plane extends Vehicle{

    private int numberOfKills;
    private int numberOfLives;
    private int numOfAtomicBombs;

    public Plane(int speed, int dir) {
        this.setSpeed(speed);
        this.setDir(dir);
    }

    public void shoot() {
        // TODO
    }

}

