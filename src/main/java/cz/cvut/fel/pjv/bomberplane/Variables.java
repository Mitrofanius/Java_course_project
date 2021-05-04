package cz.cvut.fel.pjv.bomberplane;

import java.awt.*;

public class Variables {
    private int level;
    private int trucks;
    private int tanks;
    private int jeeps;

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    private final Color backgroundColor = new Color(16, 174, 187, 129);


    public int getLevel() {
        return level;
    }

    public int getTrucks() {
        return trucks;
    }

    public int getTanks() {
        return tanks;
    }

    public int getJeeps() {
        return jeeps;
    }

    public int getTotalObjects() {
        return totalObjects;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setTrucks(int trucks) {
        this.trucks = trucks;
    }

    public void setTanks(int tanks) {
        this.tanks = tanks;
    }

    public void setJeeps(int jeeps) {
        this.jeeps = jeeps;
    }

    public void setTotalObjects(int totalObjects) {
        this.totalObjects = totalObjects;
    }

    private int totalObjects;

    public Variables(){
//        initVatiables();
    }

}
