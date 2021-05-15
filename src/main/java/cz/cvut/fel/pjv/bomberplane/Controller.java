package cz.cvut.fel.pjv.bomberplane;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.KeyStore;

public class Controller extends JFrame implements ActionListener {

    private Timer timer;
    private static boolean inGame = false;
    private static boolean inIntro = true;
    private static boolean inOutro = false;
    private static boolean inMenu = false;
    private static boolean inPause = false;

    public static boolean isInPause() {
        return inPause;
    }

    public static void setInPause(boolean inPause) {
        Controller.inPause = inPause;
    }

    public static boolean isInGame() {
        return inGame;
    }

    public static boolean isInIntro() {
        return inIntro;
    }

    public static void setInGame(boolean inGame) {
        Controller.inGame = inGame;
    }

    public static void setInIntro(boolean inIntro) {
        Controller.inIntro = inIntro;
    }

    public static void setInOutro(boolean inOutro) {
        Controller.inOutro = inOutro;
    }

    public static void setInMenu(boolean inMenu) {
        Controller.inMenu = inMenu;
    }

    public static boolean isInOutro() {
        return inOutro;
    }

    public static boolean isInMenu() {
        return inMenu;
    }

    View gameView;
    Model gameModel;


    public Controller() {
        gameModel = new Model();
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
                    } else {
                        inGame = true;
                    }
                }
                else if (gameModel.isWin() == true){
                    inOutro = true;
                    inGame= false;
                }

            } else {
                if (inIntro && key == KeyEvent.VK_SPACE) {
                    inIntro = false;
                    inGame = true;
                    gameModel.setInitParams();
                } else if (inIntro && key == KeyEvent.VK_C) {
                    inIntro = false;
                    inGame = true;
                    gameModel.loadLastGame();
                }
                if (inPause && key == KeyEvent.VK_SPACE) {
                    inPause = false;
                    inGame = true;
                }
                if (inPause && key == KeyEvent.VK_ESCAPE) {
                    inPause = false;
                    inIntro = true;
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