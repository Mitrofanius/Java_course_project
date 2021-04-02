package cz.cvut.fel.pjv.bomberplane.gameobjects;

/**
 * Enemy's vehicle, can shoot
 * @see Vehicle
 * */
public class Tank extends Vehicle{

    public Tank(int speed, int dir) {
        this.setSpeed(speed);
        this.setDir(dir);
    }

    @Override
    public void shoot() {
        // TODO
    }
}
