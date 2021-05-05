package cz.cvut.fel.pjv.bomberplane.gameobjects;

import cz.cvut.fel.pjv.bomberplane.Main;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Plane object to be controlled by a user
 */
public class Plane extends Vehicle {

    private int numberOfKills;
    private int numOfConcurrentBombsToDrop = 5;
    private long startTime;

    public boolean isDying() {
        return dying;
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }

    private long elapsedTime = 0L;
    private boolean dying = false;

    private int numberOfLives;
    private int numOfAtomicBombs;
    private int transferOffset = 50;
    private Image planeleft, planerigth,
            planeleftup, planerightup,
            planedownright, planedownleft,
            planeup, planedown, bombPic;

    private HashSet<Missile> bombs;

    public Plane(int speed, int dir) {
        this.setSpeed(speed);
        this.setSpeedX(speed);
        this.setSpeedY(0);
        this.setDir(dir);
        this.setPositionX(0);
        this.setPositionY(0);
        bombs = new HashSet<Missile>();
        loadImages();
    }

    public HashSet<Missile> getBombs() {
        return bombs;
    }

    public void shoot() {
        if (numOfConcurrentBombsToDrop > bombs.size()) {
            startTime = System.currentTimeMillis();
            bombs.add(new Missile(this.getPositionX(), this.getPositionY(), this.getSpeedX(), this.getBombPic()));
        }
    }
    public int checkBombs(){
        for (Missile bomb: bombs){
            if (!bomb.isActive()){
                bombs.remove(bomb);
            }
        }
        return bombs.size();
    }

    public void removeBomb(Missile obj){
        bombs.remove(obj);
    }

    @Override
    public void move() {
        this.setPositionX(this.getPositionX() + this.getSpeedX());
        this.setPositionY(this.getPositionY() + this.getSpeedY());
        if ((this.getPositionX() + transferOffset - transferOffset / 3) >= Main.panelWidth) {
            this.setPositionX(-transferOffset + 1);
        }
        if ((this.getPositionX() + transferOffset) <= 0) {
            this.setPositionX(Main.panelWidth - transferOffset - 1);
        }
        if ((this.getPositionY() < 0)) {
            this.setSpeedY(this.getSpeed());
        }
        if (this.getPositionY() > 300){
            dying = true;
        }
    }

    public void animate(){}

    private void loadImages() {
        planeleft = new ImageIcon("Pictures\\left.png").getImage();
        planerigth = new ImageIcon("Pictures\\right.png").getImage();
        planeleftup = new ImageIcon("Pictures\\leftup.png").getImage();
        planerightup = new ImageIcon("Pictures\\rightup.png").getImage();
        planeup = new ImageIcon("Pictures\\up.png").getImage();
        planedown = new ImageIcon("Pictures\\down.png").getImage();
        planedownleft = new ImageIcon("Pictures\\downleft.png").getImage();
        planedownright = new ImageIcon("Pictures\\downright.png").getImage();
        this.setPicture(planerigth);
        bombPic = new ImageIcon("Pictures\\bomb4.png").getImage();
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
        this.setPicture(planePic);
    }

    public Image getBombPic() {
        return bombPic;
    }


}

