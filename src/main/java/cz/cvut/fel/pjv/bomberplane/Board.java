package cz.cvut.fel.pjv.bomberplane;

import javax.sound.midi.MidiChannel;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.time.chrono.MinguoChronology;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import cz.cvut.fel.pjv.bomberplane.Main;
import cz.cvut.fel.pjv.bomberplane.gameobjects.Jeep;
import cz.cvut.fel.pjv.bomberplane.gameobjects.Missile;
import cz.cvut.fel.pjv.bomberplane.gameobjects.Plane;
import cz.cvut.fel.pjv.bomberplane.gameobjects.Vehicle;


/**
 * A class to control the game:
 * check user input and provide methods for interaction between objects
 */
public class Board extends JPanel implements ActionListener {
    private Variables Vars;
    private Plane plane;
    private Jeep jeep;
    private int speed = 3;
    private Timer timer;
    private boolean inGame = false;
    public boolean jeepIs = true;

    private HashSet<Vehicle> enemies;

    public Board() {
        Vars = new Variables();
        Vars.init_level(1);
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new MyKeyAdapter());
        addMouseListener(new MyMouseAdapter());
        initTimer();
        plane = new Plane(speed, 1);
        jeep = new Jeep(Vars.getJeepPic(), 0, 310, 5);

        setFocusable(true);
        setBackground(Vars.getBackgroundColor());
    }

    private void initTimer() {
        timer = new Timer(17, this);
        timer.start();
    }

    private void death() {
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        inGame = false;
    }

    private void playGame(Graphics2D g2d) {
        if (plane.isDying()) {
            g2d.drawImage(Vars.getExplosionPic(), plane.getPositionX(), plane.getPositionY(), this);
            death();
        }
//        else {
//            movePacman();
//            moveGhosts(g2d);
//            checkMaze();
//            drawPacman(g2d);
//        }
        plane.move();
        jeep.move();
        if (plane.checkBombs() > 0) {
            for (Missile bomb : plane.getBombs()) {
                bomb.move();
                drawBomb(g2d, bomb);
                if (bomb.isExplosion()) {
                    if ((bomb.getPositionX() + 20) > jeep.getPositionX() && bomb.getPositionX() - 20 < jeep.getPositionX()) {
                        jeepIs = false;
                        g2d.drawImage(Vars.getExplosionPic(), bomb.getPositionX(), bomb.getPositionY(), this);
                    }
                    g2d.drawImage(Vars.getExplosionPic(), bomb.getPositionX(), bomb.getPositionY(), this);
                }
            }
        }
        drawObj(g2d, plane);
        if (jeepIs) {
            drawObj(g2d, jeep);
        }

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

    private void showIntroScreen(Graphics2D g2d) {
        g2d.setColor(new Color(16, 92, 130));
        g2d.fillRect(50, Main.panelHeight / 2 - 30, Main.panelWidth - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, Main.panelHeight / 2 - 30, Main.panelWidth - 100, 50);

        String s = "Press space to start";
        Font small = new Font("Helvetica", Font.BOLD, 24);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, Main.panelHeight / 2, (Main.panelWidth - 100) / 2 - 70);
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;


        g2d.setColor(Vars.getBackgroundColor());
        g2d.fillRect(0, 0, Main.panelWidth, Main.panelHeight);
        g2d.setColor(new Color(19, 177, 19));
        g2d.fillRect(0, 320, Main.panelWidth, Main.panelHeight);
//        g2d.fillArc(0, 310, 100, 20, 90, 90);
        g2d.fillOval(0, 315, 70, 50);
        g2d.fillOval(50, 310, 70, 30);
        g2d.fillOval(150, 310, 100, 30);
        g2d.fillOval(350, 310, 80, 50);
        g2d.fillOval(500, 300, 70, 50);

        if (inGame) {
            playGame(g2d);
        } else {
            showIntroScreen(g2d);
        }
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    private void drawObj(Graphics2D g2d, Vehicle obj) {
        g2d.drawImage(obj.getPicture(), obj.getPositionX(), obj.getPositionY(), this);
    }

    private void drawBomb(Graphics2D g2d, Missile obj) {
        g2d.drawImage(obj.getPicture(), obj.getPositionX(), obj.getPositionY(), this);
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
                if (key == KeyEvent.VK_LEFT) {
                    plane.setSpeedX(-plane.getSpeed());

                    plane.setPlanePic(plane.getPlaneleft());
                    plane.setSpeedY(0);

                } else if (key == KeyEvent.VK_RIGHT) {
                    plane.setSpeedX(plane.getSpeed());

                    plane.setPlanePic(plane.getPlanerigth());
                    plane.setSpeedY(0);


                } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                    plane.setSpeedY(-plane.getSpeed());

                    plane.setPlanePic(plane.getPlaneup());
                    plane.setSpeedX(0);


                } else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {

                    plane.setSpeedY(plane.getSpeed());

                    plane.setPlanePic(plane.getPlanedown());
                    plane.setSpeedX(0);

                } else if (key == KeyEvent.VK_Q) {
                    plane.setSpeedY(-plane.getSpeed());
                    plane.setSpeedX(-plane.getSpeed());
                    plane.setPlanePic(plane.getPlaneleftup());
                } else if (key == KeyEvent.VK_E) {
                    plane.setSpeedY(-plane.getSpeed());
                    plane.setSpeedX(plane.getSpeed());
                    plane.setPlanePic(plane.getPlanerightup());
                } else if (key == KeyEvent.VK_A) {
                    plane.setSpeedY(plane.getSpeed());
                    plane.setSpeedX(-plane.getSpeed());
                    plane.setPlanePic(plane.getPlanedownleft());
                } else if (key == KeyEvent.VK_D) {
                    plane.setSpeedY(plane.getSpeed());
                    plane.setSpeedX(plane.getSpeed());
                    plane.setPlanePic(plane.getPlanedownright());
                }

                if (key == KeyEvent.VK_B) {
//                    dropBomb();
                    plane.shoot();
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
                if (key == 's' || key == 'S' || key == KeyEvent.VK_SPACE) {
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
