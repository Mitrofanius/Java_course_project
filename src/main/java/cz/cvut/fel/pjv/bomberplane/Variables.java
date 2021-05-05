package cz.cvut.fel.pjv.bomberplane;

import javax.swing.*;
import java.awt.*;

public class Variables {
    public Image getBombPic() {
        return bombPic;
    }

    public Image getExplosionPic() {
        return explosionPic;
    }

    private int level;
    private int trucks;
    private int tanks;
    private int jeeps;
    private Image jeepPic, bombPic, explosionPic;

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

    public Image getJeepPic() {
        return jeepPic;
    }

    public Variables(){
//        initVariables();
        loadPics();
    }
    private void loadPics(){
        jeepPic = new ImageIcon("Pictures\\jeep2.png").getImage();
        bombPic = new ImageIcon("Pictures\\bomb4.png").getImage();
        explosionPic = new ImageIcon("Pictures\\explosion2.png").getImage();

    }

}
