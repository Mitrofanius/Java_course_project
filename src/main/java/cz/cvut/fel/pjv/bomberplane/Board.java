package cz.cvut.fel.pjv.bomberplane;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.time.chrono.MinguoChronology;
import java.util.Random;

import cz.cvut.fel.pjv.bomberplane.Main;
import cz.cvut.fel.pjv.bomberplane.gameobjects.Plane;


/**
 * A class to control the game:
 * check user input and provide methods for interaction between objects
 */
public class Board extends JPanel implements ActionListener {
    private Variables Vars;
    private Plane plane;
    private int speed = 3;
    private Timer timer;
    private boolean inGame = true;

    public Board() {
        Vars = new Variables();
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new MyKeyAdapter());
        addMouseListener(new MyMouseAdapter());
        initTimer();
        plane = new Plane(speed, 1);

        setFocusable(true);
        setBackground(Vars.getBackgroundColor());
    }

    private void initTimer() {
        timer = new Timer(17, this);
        timer.start();
    }

    private void playGame(Graphics2D g2d) {
//        if (dying) {
//            death();
//        } else {
//            movePacman();
//            moveGhosts(g2d);
//            checkMaze();
//            drawPacman(g2d);
//        }
        plane.move();
        drawPlane(g2d);
//
//        if (bomba) {
//            moveBomb();
//            drawBomb(g2d);
//        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        doDrawing(g);

//        Graphics2D g2d = (Graphics2D) g;

//        g2d.setColor(myColor);
//        g2d.fillRect(0, 0, width, height);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;


        g2d.setColor(Vars.getBackgroundColor());
        g2d.fillRect(0, 0, Main.panelWidth, Main.panelHeight);
        g2d.setColor(new Color(19, 177, 19));
        g2d.fillRect(0, 320, Main.panelWidth, Main.panelHeight);

//        drawPlane(g2d);
        playGame(g2d);
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    private void drawPlane(Graphics2D g2d) {
        g2d.drawImage(plane.getPlanePic(), plane.getPositionX(), plane.getPositionY(), this);
    }


    public int getLevel() {
        return Vars.getLevel();
    }


    /**
     * Set amount the amount of new vehicles on the board
     */
    public void specifyLevel(int level) {
        Random rand = new Random();
        Vars.setTrucks(rand.nextInt(level / 2));
        Vars.setTanks(rand.nextInt(level / 3));
        Vars.setJeeps(rand.nextInt(level / 2));
    }


    /**
     * needed to renew levels when all enemy's vehicles and buildings are destroyed
     */
    public void setLevel(int level) {
        Vars.setLevel(level);
    }


    class MyMouseAdapter extends MouseInputAdapter {

//        @Override
//        public void mouseClicked(MouseEvent event) {
//            mouseX = event.getX();
//            mouseY = event.getY();
//
//            if ((mouseX > getPlaneX() + mousePrecision) &&(mouseY > getPlaneY() + mousePrecision)) {
//                speedX = planeSpeed;
//                speedY = planeSpeed;
//                plane = planedownright;
//            } else if ((mouseX < getPlaneX()  - mousePrecision) && (mouseY > getPlaneY() + mousePrecision)) {
//                speedX = -planeSpeed;
//                speedY = planeSpeed;
//                plane = planedownleft;
//            } else if ((mouseX < getPlaneX() - mousePrecision) && (mouseY < getPlaneY() - mousePrecision)) {
//                speedX = -planeSpeed;
//                speedY = -planeSpeed;
//                plane = planeleftup;
//            } else if ((mouseX > getPlaneX() + mousePrecision) && (mouseY < getPlaneY() - mousePrecision)) {
//                speedX = planeSpeed;
//                speedY = -planeSpeed;
//                plane = planerightup;
//            }
//
//            System.out.println("x: " + mouseX + "y: " + mouseY);
//        }
    }

    class MyKeyAdapter extends KeyAdapter {


        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
//
            if (inGame) {
                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                    if (plane.getSpeedX() >= 0) {
                        plane.setSpeedX(-plane.getSpeed());

                        plane.setSpeedY(0);
                        plane.setPlanePic(plane.getPlaneleft());
                    }
                } else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                    if (plane.getSpeedX() <= 0) {
                        plane.setSpeedX(plane.getSpeed());

                        plane.setSpeedY(0);
                        plane.setPlanePic(plane.getPlanerigth());
                    }

                } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                    if (plane.getSpeedY() >= 0) {
                        plane.setSpeedY(-plane.getSpeed());

                        plane.setSpeedX(0);
                        plane.setPlanePic(plane.getPlaneup());
                    }

                } else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {

                    if (plane.getSpeedY() <= 0) {
                        plane.setSpeedY(plane.getSpeed());

                        plane.setSpeedX(0);
                        plane.setPlanePic(plane.getPlanedown());
                    }
                }

                if (key == KeyEvent.VK_B) {
//                    dropBomb();
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                } else if (key == KeyEvent.VK_P) {
                    if (timer.isRunning()) {
                        timer.stop();
                    } else {
                        timer.start();
                    }
                }


            } else {
                if (key == 's' || key == 'S') {
                    inGame = true;
//                    initGame();
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
