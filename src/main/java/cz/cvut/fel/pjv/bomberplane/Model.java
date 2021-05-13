package cz.cvut.fel.pjv.bomberplane;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

import cz.cvut.fel.pjv.bomberplane.gameobjects.*;
import cz.cvut.fel.pjv.bomberplane.gameobjects.Building.*;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A class to control the game: check user input and provide methods for
 * interaction between objects
 */
public class Model {
    // Variables
    public static final int width = 650;
    public static final int height = 400;

    private Image jeepPic, truckPicLeft, tankPicLeft, tankPicUp, tankPicRight, bombPic, truckPicRight, explosionPic,
            houseRedPic, houseWhitePic, bonusBombPic, atomicBombPic, bombScorePic, lifePic;
    private static Image bulletPic;

    String fromJson;
    private int level = 1;
    private int trucks;
    private int tanks;
    private int jeeps;
    private int numBuildings;
    private int numOfConcurrentBombsToDrop = 0;
    private static int reachLineHeight;

    private int i;

    private Bonus localBon = Bonus.PLUSBOMBS;
    private Plane plane;
    private Jeep jeep;
    private Truck truck;
    private Tank tank;
    private int speed = 4;
    private boolean inGame = false;

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean b) {
        inGame = b;
    }

    private boolean Win = false;
    private int levelCounterTimeShow = 0;

    private HashSet<Vehicle> enemies;
    LinkedList<Vehicle> vehicles = new LinkedList<Vehicle>();
    LinkedList<FloatingReward> rewards = new LinkedList<FloatingReward>();
    private LinkedList<Building> buildings = new LinkedList<Building>();

    public Model() {
        initBoard();
    }

    private void initBoard() {
        loadPics();
        loadLevelInfo(1);
        plane = new Plane(speed, 1, numOfConcurrentBombsToDrop);
        initLevel();
    }

    public void loadLevelInfo(int num) {
        level = num;
        String lvlFile = "level_" + num + ".json";
        fromJson = FileReader.read_file(("JsonFiles\\" + lvlFile));
        System.out.println(fromJson);
        JSONObject temp;
        JSONObject o = new JSONObject(fromJson);
        JSONArray static_objs = o.getJSONArray("buildings");
        for (i = 0; i < static_objs.length(); i++) {
            temp = static_objs.getJSONObject(i);
            if (temp.getString("colour").equals("white")) {

                buildings.add(
                        new Building(houseWhitePic, temp.getInt("xPos"), temp.getInt("yPos"), temp.getString("bonus")));
            } else {
                buildings.add(
                        new Building(houseRedPic, temp.getInt("xPos"), temp.getInt("yPos"), temp.getString("bonus")));
            }
        }

        tanks = o.getInt("numTanks");
        trucks = o.getInt("numTrucks");
        jeeps = o.getInt("numJeeps");
        reachLineHeight = o.getInt("reachLineHeight");
        if (numOfConcurrentBombsToDrop == 0) {
            numOfConcurrentBombsToDrop = o.getInt("numOfCurrentBombsToDrop");
        }
    }

    public HashSet<Vehicle> getEnemies() {
        return enemies;
    }

    public Plane getPlane() {
        return plane;
    }

    public LinkedList<Vehicle> getVehicles() {
        return vehicles;
    }

    public LinkedList<FloatingReward> getRewards() {
        return rewards;
    }

    public LinkedList<Building> getBuildings() {
        return buildings;
    }

    private void death() {
        inGame = false;
    }

    public void playGame() {
        if (plane.isDying()) {
            death();
        }

        checkRewards();
        plane.move();
        if (plane.getPositionY() >= reachLineHeight - 20) {
            doShooting();
        } else {
            doLightShooting();
        }

        moveVehicles();

        if (plane.checkBombs() > 0) {
            for (Missile bomb : plane.getBombs()) {
                bomb.move();
                if (bomb.isExplosion()) {
                    checkVehicles(bomb);
                    checkBuildings(bomb);
                }
            }
        }


    }

    private void checkBuildings(Missile bomb) {
        for (int i = buildings.size() - 1; i > -1; i--) {
            if ((bomb.getPositionX() + 10) > buildings.get(i).getPositionX()
                    && bomb.getPositionX() - 40 < buildings.get(i).getPositionX()) {
                rewards.add(new FloatingReward(bonusBombPic,
                        buildings.get(i).getPositionX(),
                        buildings.get(i).getPositionY(), buildings.get(i).getBon()));
                buildings.remove(i);
            }
        }
    }

    private void checkVehicles(Missile bomb) {
        for (int i = vehicles.size() - 1; i > -1; i--) {
            if ((bomb.getPositionX() + 10) > vehicles.get(i).getPositionX()
                    && bomb.getPositionX() - 30 < vehicles.get(i).getPositionX()) {
                vehicles.remove(i);
            }
        }
    }

    private void moveVehicles() {
        for (int i = vehicles.size() - 1; i > -1; i--) {
            vehicles.get(i).move();
        }
        if (vehicles.size() == 0) {
            startNextLevel();
        }
    }


    private void startNextLevel() {
        String s;
//        g2d.setFont(smallFont);
//        g2d.setColor(Color.white);
        s = "JsonFiles\\level_" + (level + 1) + ".json";
        File check = new File(s);
        if (!check.isFile()) {
            Win = true;
            System.out.println(s);
//            showOutroScreen(g2d);
            return;
        }
//        g2d.drawString(s, width / 2 - 50, 100);
        loadLevelInfo(level + 1);
        initLevel();
    }


    public Image getExplosionPic() {
        return explosionPic;
    }

    public static int getReachLineHeight() {
        return reachLineHeight;
    }

    public Image getHouseRedPic() {
        return houseRedPic;
    }

    public int getNumOfConcurrentBombsToDrop() {
        return numOfConcurrentBombsToDrop;
    }

    public Image getHouseWhitePic() {
        return houseWhitePic;
    }

    public Image getBonusBombPic() {
        return bonusBombPic;
    }

    public Image getAtomicBombPic() {
        return atomicBombPic;
    }

    public Image getBombScorePic() {
        return bombScorePic;
    }

    public Image getLifePic() {
        return lifePic;
    }

    public static Image getBulletPic() {
        return bulletPic;
    }

    private void checkRewards() {
        for (int i = rewards.size() - 1; i > -1; i--) {
            rewards.get(i).move();
            if (((rewards.get(i).getPositionX() <= plane.getPositionX() + 30)
                    && (rewards.get(i).getPositionX() >= plane.getPositionX() - 20)
                    && (rewards.get(i).getPositionY() <= plane.getPositionY() + 20)
                    && (rewards.get(i).getPositionY() >= plane.getPositionY() - 20))) {
                rewards.get(i).setCaught(true);
                if (rewards.get(i).getBenefit().equals(Bonus.PLUSBOMBS)) {
                    plane.setNumOfConcurrentBombsToDrop(plane.getNumOfConcurrentBombsToDrop() + 1);
                }
                rewards.remove(i);}
        }
    }

    private void doShooting() {
        for (int i = vehicles.size() - 1; i > -1; i--) {
            if (vehicles.get(i).isTank()) {
                if (vehicles.get(i).getBullet() == null) {
                    vehicles.get(i).shoot();
                } else if (!vehicles.get(i).getBullet().isActive()) {
                    vehicles.get(i).setBullet(null);
                } else {
                    vehicles.get(i).getBullet().move();
                    if ((plane.getPositionX() + 20 >= vehicles.get(i).getBullet().getPositionX())
                            && plane.getPositionX() - 15 < vehicles.get(i).getBullet().getPositionX()
                            && plane.getPositionY() - 5 < vehicles.get(i).getBullet().getPositionY()
                            && plane.getPositionY() + 20 > vehicles.get(i).getBullet().getPositionY()) {

                        plane.setDying(true);
                    }
                }
            }
        }
    }

    private void doLightShooting() {
        for (int i = vehicles.size() - 1; i > -1; i--) {
            if (vehicles.get(i).isTank()) {
                if (vehicles.get(i).getBullet() == null) {
                    return;
                }
                if (!vehicles.get(i).getBullet().isActive()) {
                    vehicles.get(i).setBullet(null);
                } else {
                    vehicles.get(i).getBullet().move();
                }
            }
        }
    }

    private void initLevel() {
        levelCounterTimeShow = 0;
        int i = 0;
        int coordX = 0;
        int speedDir = 1;
        for (i = 0; i < jeeps; i++) {
            jeep = new Jeep(jeepPic, coordX, 310, 5 * speedDir);
            vehicles.add(jeep);
            coordX += 100;
            speedDir *= -1;
        }

        for (i = 0; i < trucks; i++) {
            truck = new Truck(truckPicLeft, truckPicRight, coordX, 310, 3 * speedDir);
            vehicles.add(truck);
            coordX += 70;
            speedDir *= -1;
        }

        for (i = 0; i < tanks; i++) {
            tank = new Tank(tankPicLeft, tankPicRight, tankPicUp, coordX, 310,
                    3 * speedDir);
            vehicles.add(tank);
            coordX += 50;
            speedDir *= -1;
        }
    }

    public int getLevel() {
        return level;
    }

    public void changePlaneDir(String direction) {
        switch (direction) {
            case "left":
                plane.setSpeedX(-plane.getSpeed());
                plane.setSpeedY(0);
                plane.setPlanePic(plane.getPlaneleft());
                break;
            case "right":
                plane.setSpeedX(plane.getSpeed());
                plane.setPlanePic(plane.getPlanerigth());
                plane.setSpeedY(0);

                break;
            case "up":
                plane.setSpeedX(0);
                plane.setPlanePic(plane.getPlaneup());
                plane.setSpeedY(-plane.getSpeed());
                break;
            case "down":
                plane.setSpeedX(0);
                plane.setPlanePic(plane.getPlanedown());
                plane.setSpeedY(plane.getSpeed());
                break;
            case "rightup":
                plane.setSpeedX(plane.getSpeedDiagonal());
                plane.setPlanePic(plane.getPlanerightup());
                plane.setSpeedY(-plane.getSpeedDiagonal());
                break;
            case "leftup":
                plane.setSpeedX(-plane.getSpeedDiagonal());
                plane.setPlanePic(plane.getPlaneleftup());
                plane.setSpeedY(-plane.getSpeedDiagonal());
                break;
            case "rightdown":
                plane.setSpeedX(plane.getSpeedDiagonal());
                plane.setPlanePic(plane.getPlanedownright());
                plane.setSpeedY(plane.getSpeedDiagonal());
                break;
            case "leftdown":
                plane.setSpeedX(-plane.getSpeedDiagonal());
                plane.setPlanePic(plane.getPlanedownleft());
                plane.setSpeedY(plane.getSpeedDiagonal());
                break;
        }
    }

    private void loadPics() {
        jeepPic = new ImageIcon("Pictures\\jeep2.png").getImage();
        truckPicLeft = new ImageIcon("Pictures\\truck_left.png").getImage();
        truckPicRight = new ImageIcon("Pictures\\truck_right.png").getImage();
        tankPicLeft = new ImageIcon("Pictures\\tank_left.png").getImage();
        tankPicUp = new ImageIcon("Pictures\\tank_up.png").getImage();
        tankPicRight = new ImageIcon("Pictures\\tank_right.png").getImage();
        bombPic = new ImageIcon("Pictures\\bomb4.png").getImage();
        explosionPic = new ImageIcon("Pictures\\explosion2.png").getImage();
        houseRedPic = new ImageIcon("Pictures\\house_red.png").getImage();
        houseWhitePic = new ImageIcon("Pictures\\house_white.png").getImage();
        bonusBombPic = new ImageIcon("Pictures\\bonus.png").getImage();
        atomicBombPic = new ImageIcon("Pictures\\AtomicBomb.png").getImage();
        bombScorePic = new ImageIcon("Pictures\\bombScore.png").getImage();
        lifePic = new ImageIcon("Pictures\\life.png").getImage();
        bulletPic = new ImageIcon("Pictures\\bullet.png").getImage();
    }

    public void planeShoot() {
        plane.shoot();
    }
}
