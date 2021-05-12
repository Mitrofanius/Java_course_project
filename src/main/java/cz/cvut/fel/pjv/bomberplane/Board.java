package cz.cvut.fel.pjv.bomberplane;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

import cz.cvut.fel.pjv.bomberplane.gameobjects.*;
import cz.cvut.fel.pjv.bomberplane.gameobjects.Building.*;

/**
 * A class to control the game:
 * check user input and provide methods for interaction between objects
 */
public class Board extends JPanel implements ActionListener {
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);
    private final Font bigFont = new Font("Helvetica", Font.BOLD, 24);
    private Variables Vars;
    private Bonus localBon = Bonus.PLUSBOMBS;
    private Plane plane;
    private Jeep jeep;
    private Truck truck;
    private Tank tank;
    private int speed = 4;
    private Timer timer;
    private boolean inGame = false;
    private boolean Win = false;
    public boolean jeepIs = true;
    private int levelCounterTimeShow = 0;
//    private int reachLineHeight;

    private HashSet<Vehicle> enemies;
    LinkedList<Vehicle> vehicles = new LinkedList<Vehicle>();
    LinkedList<FloatingReward> rewards = new LinkedList<FloatingReward>();


    public Board() {
        Vars = new Variables();
        Vars.loadLevelInfo(1);
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new MyKeyAdapter());
        addMouseListener(new MyMouseAdapter());
        initTimer();
        plane = new Plane(speed, 1, Vars.getNumOfCurrentBombsToDrop());

        initLevel();

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
        for (int i = Vars.getBuildings().size() - 1; i > -1; i--) {
//            if (Vars.getBuildings().get(i).isDestroyed()) {
//                rewards.add(new FloatingReward(Vars.getBonusBombPic(),
//                        Vars.getBuildings().get(i).getPositionX(),
//                        Vars.getBuildings().get(i).getPositionY(),
//                        Vars.getBuildings().get(i).getBon()));
//                Vars.getBuildings().remove(i);
//            } else {
            drawObj(g2d, Vars.getBuildings().get(i));
//            }
        }
        for (int i = rewards.size() - 1; i > -1; i--) {
            rewards.get(i).move();
            if (((rewards.get(i).getPositionX() <= plane.getPositionX() + 30) &&
                    (rewards.get(i).getPositionX() >= plane.getPositionX() - 20)
                    && (rewards.get(i).getPositionY() <= plane.getPositionY() + 20)
                    && (rewards.get(i).getPositionY() >= plane.getPositionY() - 20))) {
                rewards.get(i).setCaught(true);
                if (rewards.get(i).getBenefit().equals(Bonus.PLUSBOMBS)) {
                    plane.setNumOfConcurrentBombsToDrop(plane.getNumOfConcurrentBombsToDrop() + 1);
                }
                rewards.remove(i);

            } else {
                drawObj(g2d, rewards.get(i));
            }

        }
        plane.move();
        if (plane.getPositionY() > Variables.getReachLineHeight()) {
            doShooting(g2d);
        } else {
            doLightShooting(g2d);
        }

        for (int i = vehicles.size() - 1; i > -1; i--) {
            vehicles.get(i).move();
        }
        if (plane.checkBombs() > 0) {
            for (Missile bomb : plane.getBombs()) {
                bomb.move();
                drawBomb(g2d, bomb);
                if (bomb.isExplosion()) {
                    for (int i = vehicles.size() - 1; i > -1; i--) {
                        if ((bomb.getPositionX() + 10) > vehicles.get(i).getPositionX() && bomb.getPositionX() - 30 < vehicles.get(i).getPositionX()) {
//                            vehicles.get(i).setDying(true);
                            vehicles.remove(i);
                            g2d.drawImage(Vars.getExplosionPic(), bomb.getPositionX(), bomb.getPositionY(), this);
                        }
                        g2d.drawImage(Vars.getExplosionPic(), bomb.getPositionX(), bomb.getPositionY(), this);
                    }

                    for (int i = Vars.getBuildings().size() - 1; i > -1; i--) {
                        if ((bomb.getPositionX() + 10) > Vars.getBuildings().get(i).getPositionX() && bomb.getPositionX() - 40 < Vars.getBuildings().get(i).getPositionX()) {
//                            Vars.getBuildings().get(i).setDestroyed(true);
//                            if (Vars.getBuildings().get(i).isDestroyed()) {
                            rewards.add(new FloatingReward(Vars.getBonusBombPic(),
                                    Vars.getBuildings().get(i).getPositionX(),
                                    Vars.getBuildings().get(i).getPositionY(),
                                    Vars.getBuildings().get(i).getBon()));
                            Vars.getBuildings().remove(i);
//                            }
                            g2d.drawImage(Vars.getExplosionPic(), bomb.getPositionX(), bomb.getPositionY(), this);
                        }
                        g2d.drawImage(Vars.getExplosionPic(), bomb.getPositionX(), bomb.getPositionY(), this);
                    }
                }
            }
        }
        for (int i = vehicles.size() - 1; i > -1; i--) {
//            if (vehicles.get(i).isDying()) {
//                vehicles.remove(i);
//            } else {
            drawObj(g2d, vehicles.get(i));
//            }
        }

        drawObj(g2d, plane);

        if (vehicles.size() == 0) {
            startNextLevel(g2d);

        }

//        for (int i = 0; i < Vars.getBuildings().size(); i++){
//            drawObj(g2d, Vars.getBuildings().get(i));
//        }

//        for (int i = 0; i < vehicles.size(); i++) {
//            if (!vehicles.get(i).isDying()) {
//                drawObj(g2d, vehicles.get(i));
//            }
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

    private void startNextLevel(Graphics2D g2d) {
        String s;
        g2d.setFont(smallFont);
        g2d.setColor(Color.white);
        s = "JsonFiles\\level_" + (Vars.getLevel() + 1) + ".json";
        File check = new File(s);
        if (!check.isFile()) {
            Win = true;
            System.out.println(s);
            showOutroScreen(g2d);
            return;
        }
        g2d.drawString(s, Main.panelWidth / 2 - 50, 100);
        Vars.loadLevelInfo(Vars.getLevel() + 1);
        initLevel();
    }

    private void showIntroScreen(Graphics2D g2d) {
        g2d.setColor(new Color(16, 92, 130));
        g2d.fillRect(50, Main.panelHeight / 2 - 30, Main.panelWidth - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, Main.panelHeight / 2 - 30, Main.panelWidth - 100, 50);

        String s = "Press space to start a new Game";
//        Font small = new Font("Helvetica", Font.BOLD, 24);
        FontMetrics metr = this.getFontMetrics(bigFont);

        g2d.setColor(Color.white);
        g2d.setFont(bigFont);
        g2d.drawString(s, Main.panelWidth / 2 - 190, (Main.panelHeight) / 2);

        g2d.setColor(new Color(16, 92, 130));
        g2d.fillRect(50, Main.panelHeight / 2 + 20, Main.panelWidth - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, Main.panelHeight / 2 + 20, Main.panelWidth - 100, 50);
        s = "Continue your previous Game";
        g2d.drawString(s, Main.panelWidth / 2 - 190, (Main.panelHeight) / 2 + 50);

    }

    private void showOutroScreen(Graphics2D g2d) {
        inGame = false;
        g2d.setColor(new Color(16, 92, 130));
        g2d.fillRect(50, Main.panelHeight / 2 - 30, Main.panelWidth - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, Main.panelHeight / 2 - 30, Main.panelWidth - 100, 50);

        String s = "Enough, you are too good";
        Font small = new Font("Helvetica", Font.BOLD, 24);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, Main.panelWidth / 2 - 150, (Main.panelHeight) / 2);
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (levelCounterTimeShow < 100) {
            drawLevelNumber(g2d);
        }

        drawDottedLine(g2d);

        g2d.setColor(Vars.getBackgroundColor());
        g2d.fillRect(0, 0, Main.panelWidth, Main.panelHeight);
        g2d.setColor(new Color(19, 177, 19));
        g2d.fillRect(0, 320, Main.panelWidth, Main.panelHeight);
//        g2d.fillArc(0, 310, 100, 20, 90, 90);

        drawHills(g2d);
        drawScore(g2d);

        if (inGame) {
            playGame(g2d);
        } else {
            if (Win == true) {
                showOutroScreen(g2d);
            } else {
                showIntroScreen(g2d);
            }
        }
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    private void drawHills(Graphics2D g2d) {
        g2d.fillOval(0, 315, 70, 50);
        g2d.fillOval(50, 310, 70, 30);
        g2d.fillOval(150, 310, 100, 30);
        g2d.fillOval(350, 310, 80, 50);
        g2d.fillOval(500, 300, 70, 50);
    }

    private void drawScore(Graphics2D g2d) {
        int i;
        String s;
        g2d.setFont(smallFont);
        g2d.setColor(Color.white);

        g2d.drawImage(Vars.getBombScorePic(), 0, 2, this);
        s = "x" + plane.getNumOfConcurrentBombsToDrop();
        g2d.drawString(s, 14, 16);

        g2d.drawImage(Vars.getAtomicBombPic(), 30, 0, this);
        s = "x" + plane.getNumOfAtomicBombs();
        g2d.drawString(s, 50, 16);

        g2d.drawImage(Vars.getLifePic(), 66, 0, this);
        s = "x" + plane.getNumberOfLives();
        g2d.drawString(s, 92, 16);

    }

    private void drawLevelNumber(Graphics2D g2d) {
        String s;
        g2d.setFont(bigFont);
        g2d.setColor(Color.white);
        s = "Level " + (Vars.getLevel());
        g2d.drawString(s, Main.panelWidth / 2 - 50, 100);
        levelCounterTimeShow += 1;
    }

    private void drawObj(Graphics2D g2d, Vehicle obj) {
        g2d.drawImage(obj.getPicture(), obj.getPositionX(), obj.getPositionY(), this);
    }

    private void drawObj(Graphics2D g2d, PlaneBuilder obj) {
        g2d.drawImage(obj.getPicture(), obj.getPositionX(), obj.getPositionY(), this);
    }

    private void drawObj(Graphics2D g2d, Building obj) {
        g2d.drawImage(obj.getPicture(), obj.getPositionX(), obj.getPositionY(), this);
    }

    private void drawObj(Graphics2D g2d, Bullet obj) {
        g2d.drawImage(obj.getPicture(), obj.getPositionX(), obj.getPositionY(), this);
    }

    private void drawObj(Graphics2D g2d, FloatingReward obj) {
        g2d.drawImage(obj.getPicture(), obj.getPositionX(), obj.getPositionY(), this);
    }

    private void drawBomb(Graphics2D g2d, Missile obj) {
        g2d.drawImage(obj.getPicture(), obj.getPositionX(), obj.getPositionY(), this);
    }

    void drawDottedLine(Graphics2D g2d) {
        g2d.setColor(new Color(0x70F1DBDB, true));
//        g2d.setColor(Color.white);
        for (int i = 0; i < Main.panelWidth; i += 6) {
            g2d.drawLine(i, Vars.getReachLineHeight(), i + 2, Vars.getReachLineHeight());
        }
    }

//    public int getLevel() {
//        return Vars.getLevel();
//    }


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

    private void doShooting(Graphics2D g2d) {
        for (int i = vehicles.size() - 1; i > -1; i--) {
            if (vehicles.get(i).isTank()) {
                if (vehicles.get(i).getBullet() == null) {
                    vehicles.get(i).shoot();
                    drawObj(g2d, vehicles.get(i).getBullet());

                } else if (!vehicles.get(i).getBullet().isActive()) {
                    vehicles.get(i).setBullet(null);
                } else {
                    vehicles.get(i).getBullet().move();
                    if ((plane.getPositionX() + 20 >= vehicles.get(i).getBullet().getPositionX())
                            && plane.getPositionX() - 15 < vehicles.get(i).getBullet().getPositionX()
                            && plane.getPositionY() - 5 < vehicles.get(i).getBullet().getPositionY()
                            && plane.getPositionY() + 20 > vehicles.get(i).getBullet().getPositionY()) {
                        g2d.drawImage(Vars.getExplosionPic(), plane.getPositionX(), plane.getPositionY(), this);

                        plane.setDying(true);
                    }
                    drawObj(g2d, vehicles.get(i).getBullet());
                }
            }
        }
    }

    private void doLightShooting(Graphics2D g2d) {
        for (int i = vehicles.size() - 1; i > -1; i--) {
            if (vehicles.get(i).isTank()) {
                if (vehicles.get(i).getBullet() == null) {
                    return;
                }
                    if (!vehicles.get(i).getBullet().isActive()) {
                    vehicles.get(i).setBullet(null);
                } else {
                    vehicles.get(i).getBullet().move();
                    drawObj(g2d, vehicles.get(i).getBullet());
                }
            }
        }
    }

    private void initLevel() {
        levelCounterTimeShow = 0;
        int i = 0;
        int coordX = 0;
        int speedDir = 1;
        for (i = 0; i < Vars.getJeeps(); i++) {
            jeep = new Jeep(Vars.getJeepPic(), coordX, 310, 5 * speedDir);
            vehicles.add(jeep);
            coordX += 100;
            speedDir *= -1;
        }

        for (i = 0; i < Vars.getTrucks(); i++) {
            truck = new Truck(Vars.getTruckPicLeft(), Vars.getTruckPicRight(), coordX, 310, 3 * speedDir);
            vehicles.add(truck);
            coordX += 70;
            speedDir *= -1;
        }

        for (i = 0; i < Vars.getTanks(); i++) {
            tank = new Tank(Vars.getTankPicLeft(), Vars.getTankPicRight(), Vars.getTankPicUp(), coordX, 310, 3 * speedDir);
            vehicles.add(tank);
            coordX += 50;
            speedDir *= -1;
        }
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
                    plane.setSpeedY(-plane.getSpeed() + 1);
                    plane.setSpeedX(-plane.getSpeed() + 1);
                    plane.setPlanePic(plane.getPlaneleftup());
                } else if (key == KeyEvent.VK_E) {
                    plane.setSpeedY(-plane.getSpeed() + 1);
                    plane.setSpeedX(plane.getSpeed() - 1);
                    plane.setPlanePic(plane.getPlanerightup());
                } else if (key == KeyEvent.VK_A) {
                    plane.setSpeedY(plane.getSpeed() - 1);
                    plane.setSpeedX(-plane.getSpeed() + 1);
                    plane.setPlanePic(plane.getPlanedownleft());
                } else if (key == KeyEvent.VK_D) {
                    plane.setSpeedY(plane.getSpeed() - 1);
                    plane.setSpeedX(plane.getSpeed() - 1);
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
