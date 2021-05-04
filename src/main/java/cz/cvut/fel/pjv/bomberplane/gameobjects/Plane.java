package cz.cvut.fel.pjv.bomberplane.gameobjects;

import cz.cvut.fel.pjv.bomberplane.Main;
import javax.swing.*;
import java.awt.*;

/**
 * Plane object to be controlled by a user
 * */
public class Plane extends Vehicle{

    private int numberOfKills;
    private int numberOfLives;
    private int numOfAtomicBombs;
    private int transferOffset = 50;
    private Image planeleft, planerigth,
            planeleftup, planerightup,
            planedownright, planedownleft,
            planeup, planedown, bomb, planePic;


    public Plane(int speed, int dir) {
        this.setSpeed(speed);
        this.setSpeedX(speed);
        this.setSpeedY(0);
        this.setDir(dir);
        this.setPositionX(0);
        this.setPositionY(0);
        loadImages();
    }

    public void shoot() {
        // TODO
    }

    @Override
    public void move() {
        this.setPositionX(this.getPositionX() + this.getSpeedX());
        this.setPositionY(this.getPositionY() + this.getSpeedY());
        if ((this.getPositionX() + transferOffset) >= Main.panelWidth) {
            this.setPositionX(-transferOffset + 1);
        }
        if ((this.getPositionX() + transferOffset) <= 0) {
            this.setPositionX(Main.panelWidth - transferOffset - 1);
        }
        if ((this.getPositionY() < 0)) {
            this.setSpeedY(this.getSpeed());
        }
    }


    private void loadImages() {
        planeleft = new ImageIcon("Pictures\\left.png").getImage();
        planerigth = new ImageIcon("Pictures\\right.png").getImage();
        planeleftup = new ImageIcon("Pictures\\leftup.png").getImage();
        planerightup = new ImageIcon("Pictures\\rightup.png").getImage();
        planeup = new ImageIcon("Pictures\\up.png").getImage();
        planedown = new ImageIcon("Pictures\\down.png").getImage();
        planedownleft = new ImageIcon("Pictures\\downleft.png").getImage();
        planedownright = new ImageIcon("Pictures\\downright.png").getImage();
        planePic = planerigth;
        bomb = new ImageIcon("Pictures\\bomb4.png").getImage();
    }

    public int getNumberOfKills() {
        return numberOfKills;
    }

    public int getNumberOfLives() {
        return numberOfLives;
    }

    public int getNumOfAtomicBombs() {
        return numOfAtomicBombs;
    }

    public int getTransferOffset() {
        return transferOffset;
    }

    public Image getPlaneleft() {
        return planeleft;
    }

    public Image getPlanerigth() {
        return planerigth;
    }

    public Image getPlaneleftup() {
        return planeleftup;
    }

    public Image getPlanerightup() {
        return planerightup;
    }

    public Image getPlanedownright() {
        return planedownright;
    }

    public Image getPlanedownleft() {
        return planedownleft;
    }

    public Image getPlaneup() {
        return planeup;
    }

    public Image getPlanedown() {
        return planedown;
    }

    public void setPlanePic(Image planePic) {
        this.planePic = planePic;
    }

    public Image getBomb() {
        return bomb;
    }

    public Image getPlanePic() {
        return planePic;
    }
}

