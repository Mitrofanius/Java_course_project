package cz.cvut.fel.pjv.bomberplane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;


/**
 * A class to control the game:
 * check user input and provide methods for interaction between objects
 * */
public class GameController implements KeyListener, ActionListener {
    private int level;
    int trucks;
    int tanks;
    int jeeps;
    int totalObjects;

    public int getLevel() {
        return level;
    }


    /**
     *Set amount the amount of new vehicles on the board
     */
    public void specifyLevel(int level) {
        Random rand = new Random();
        this.trucks = rand.nextInt(level / 2);
        this.tanks = rand.nextInt(level / 3);
        this.jeeps = rand.nextInt(level / 2);
    }


    /**
     * needed to renew levels when all enemy's vehicles and buildings are destroyed
     * */
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
