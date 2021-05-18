package cz.cvut.fel.pjv.bomberplane;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class to control the game: checks user input
 * from the keyboard, controls model and view,
 * contains timer to say the model
 * when to update game states and view when to draw
 * graphics.
 *
 * Contains inner class KeyAdapter for key listening.
 */
public class Controller extends JFrame implements ActionListener {

    private  final Logger LOGGER = Logger.getLogger(Controller.class.getName());


    private Timer timer;
    private boolean inGame = false;
    private boolean inIntro = true;
    private boolean inOutro = false;
    private boolean inMenu = false;
    private boolean inPause = false;
    private boolean isMusic = false;
    public boolean ok;

    public boolean isIsMusic() {
        return isMusic;
    }

    public  void setIsMusic(boolean isMusic) {
        this.isMusic = isMusic;
    }

    public  boolean isInPause() {
        return inPause;
    }

    public  void setInPause(boolean inPause) {
        this.inPause = inPause;
    }

    public  boolean isInGame() {
        return inGame;
    }

    public  boolean isInIntro() {
        return inIntro;
    }

    public  void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public  void setInIntro(boolean inIntro) {
        this.inIntro = inIntro;
    }

    public  void setInOutro(boolean inOutro) {
        this.inOutro = inOutro;
    }

    public  void setInMenu(boolean inMenu) {
        this.inMenu = inMenu;
    }

    public  boolean isInOutro() {
        return inOutro;
    }

    public  boolean isInMenu() {
        return inMenu;
    }

    View gameView;
    Model gameModel;


    public Controller() {
        ok = true;
        inGame = false;
        inIntro = true;
        inOutro = false;
        inMenu = false;
        inPause = false;
        isMusic = false;
        LOGGER.log(Level.INFO, "Application runs");
        gameModel = new Model(this);
        gameView = new View(gameModel);
        addKeyListener(new MyKeyAdapter());

        initUI();
    }

    private void initUI() {
        timer = new Timer(17, this);
        add(gameView);
        setTitle("Atomic Bomber");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 400);
        setLocationRelativeTo(null);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame)
            gameModel.playGame();
        gameView.drawView();
    }

    class MyKeyAdapter extends KeyAdapter {
        String s = "";


        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (inGame) {
                if (key == KeyEvent.VK_LEFT) {
                    s += "00";
                }

                if (key == KeyEvent.VK_RIGHT) {
                    s += "01";
                }

                if (key == KeyEvent.VK_UP) {
                    s += "11";
                }

                if (key == KeyEvent.VK_DOWN) {
                    s += "10";
                }

                switch (s) {
                    case "00":
                        gameModel.changePlaneDir("left");
                        break;
                    case "01":
                        gameModel.changePlaneDir("right");
                        break;
                    case "0111":
                        gameModel.changePlaneDir("rightup");
                        break;
                    case "1101":
                        gameModel.changePlaneDir("rightup");
                        break;
                    case "0011":
                        gameModel.changePlaneDir("leftup");
                        break;
                    case "1100":
                        gameModel.changePlaneDir("leftup");
                        break;
                    case "11":
                        gameModel.changePlaneDir("up");
                        break;
                    case "10":
                        gameModel.changePlaneDir("down");
                        break;
                    case "0010":
                        gameModel.changePlaneDir("leftdown");
                        break;
                    case "1000":
                        gameModel.changePlaneDir("leftdown");
                        break;
                    case "0110":
                        gameModel.changePlaneDir("rightdown");
                        break;
                    case "1001":
                        gameModel.changePlaneDir("rightdown");
                        break;
                }

                if (key == KeyEvent.VK_B) {
                    gameModel.planeShoot();
                } else if (key == KeyEvent.VK_A) {
                        gameModel.planeShootAtomic();
                } else if (key == KeyEvent.VK_SPACE) {
                    if (timer.isRunning()) {
                        inGame = false;
                        inPause = true;
                        LOGGER.log(Level.INFO, "Game paused");

                    } else {
                        inGame = true;
                    }
                }
                else if (gameModel.isWin() == true){
                    inOutro = true;
                    inGame = false;
                }

            } else {
                if (inIntro && key == KeyEvent.VK_SPACE) {
                    inIntro = false;
                    inGame = true;
                    LOGGER.log(Level.INFO, "New Game");
                    gameModel.setInitParams();
                } else if (inIntro && key == KeyEvent.VK_C) {
                    inIntro = false;
                    inGame = true;
                    LOGGER.log(Level.INFO, "Loading last saved game to resume");
                    gameModel.loadLastGame();
                }
                else if (inPause && key == KeyEvent.VK_SPACE) {
                    LOGGER.log(Level.INFO, "Game resumed");

                    inPause = false;
                    inGame = true;
                }
                else if (inPause && key == KeyEvent.VK_ESCAPE) {
                    inPause = false;
                    inGame = false;
                    inIntro = true;
                    LOGGER.log(Level.INFO, "Session ended");
                    gameModel.saveCurrentGame();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            s = "";

        }
    }
}