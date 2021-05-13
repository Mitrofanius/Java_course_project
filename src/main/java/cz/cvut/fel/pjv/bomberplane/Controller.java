package cz.cvut.fel.pjv.bomberplane;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends JFrame implements ActionListener {

    private Timer timer;
    private boolean inGame = true;
    View gameView;
    Model gameModel;
    View menuView;


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
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                } else if (key == KeyEvent.VK_P) {
                    if (timer.isRunning()) {
                        timer.stop();
                    } else {
                        timer.start();
                    }
                }

            }else{

            }
            if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                inGame = false;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            s = "";

        }
    }
}