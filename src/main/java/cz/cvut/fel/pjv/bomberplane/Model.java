package cz.cvut.fel.pjv.bomberplane;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;

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
    private int atomicCounter = 0;
    private boolean savedGameExists;
    private String savedGameFileName = "JsonFiles\\saved_game.json";

    public boolean isSavedGameExists() {
        return savedGameExists;
    }

    public static final int width = 650;
    public static final int height = 400;

    private Image jeepPic, truckPicLeft, tankPicLeft, tankPicUp,
            tankPicRight, bombPic, truckPicRight, explosionPic,
            houseRedPic, houseWhitePic, bonusBombPic, atomicBombPic,
            bombScorePic, lifePic, skullPic, atomicExplosionPic;
    private static Image bulletPic;

    public Image getSkullPic() {
        return skullPic;
    }

    String fromJson;
    private int level = 1;
    private int numTrucks;
    private int numTanks;
    private int numJeeps;
    private int numBuildings;
    //    private int numOfConcurrentBombsToDrop = 0;
    private static int reachLineHeight;

    private int i;

    private Bonus localBon = Bonus.PLUSBOMB;
    private Plane plane;
    private Jeep jeep;
    private Truck truck;
    private Tank tank;
    private int speed = 4;

    private boolean Win = false;
    private int levelCounterTimeShow = 0;

    private HashSet<Vehicle> enemies;
    LinkedList<Vehicle> vehicles = new LinkedList<Vehicle>();
    LinkedList<Bullet> bullets = new LinkedList<Bullet>();
    LinkedList<FloatingReward> rewards = new LinkedList<FloatingReward>();
    private LinkedList<Building> buildings = new LinkedList<Building>();

    public Model() {
        initBoard();
    }

    private void initBoard() {
        savedGameExists = false;
        if (!FileHandler.readFile(savedGameFileName).equals("NOINFO")){
            savedGameExists = true;
        }
        plane = new Plane(speed, 1, 0);
        loadPics();
        loadLevelInfo(1);
        initLevel();
    }

    public void loadLevelInfo(int num) {
        level = num;
        if (num == 0)
            fromJson = FileHandler.readFile(savedGameFileName);
        else{
            String lvlFile = "level_" + num + ".json";
            fromJson = FileHandler.readFile(("JsonFiles\\" + lvlFile));
        }
        System.out.println(fromJson);
        JSONObject temp;
        JSONObject o = new JSONObject(fromJson);
        System.out.println(o.toString());
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

        numTanks = o.getInt("numTanks");
        numTrucks = o.getInt("numTrucks");
        numJeeps = o.getInt("numJeeps");
        reachLineHeight = o.getInt("reachLineHeight");
        if (plane.getNumOfConcurrentBombsToDrop() == 0) {
            plane.setNumOfConcurrentBombsToDrop(o.getInt("numOfCurrentBombsToDrop"));
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

    public void death() {
        if (plane.getNumberOfLives() > 1) {
            plane.setSpeedX(speed);
            plane.setSpeedY(0);
            plane.setDir(1);
            plane.setNumOfAtomicBombs(0);
            plane.setPositionX(0);
            plane.setPositionY(0);
            plane.setBombs(new LinkedList<>());
            plane.setNumberOfLives(plane.getNumberOfLives() - 1);
            plane.setPicture(plane.getPlanerigth());
            plane.setDying(false);
        } else {
            FileHandler.writeToFile(savedGameFileName, "NOINFO");
            Controller.setInGame(false);
            Controller.setInIntro(true);
        }
    }

    public int getAtomicCounter() {
        return atomicCounter;
    }

    public void setAtomicCounter(int atomicCounter) {
        this.atomicCounter = atomicCounter;
    }

    public void playGame() {
        if (plane.isDying()) {
            death();
        }

        if (atomicCounter > 0 && atomicCounter < 50) {
            plane.move();
            return;
        } else if (atomicCounter >= 50) {
            atomicCounter = 0;
            startNextLevel();
        }


        checkRewards();
        plane.move();
        if (plane.getPositionY() >= reachLineHeight - 20) {
            doShooting();
            moveBullets(bullets);
        } else {
            moveBullets(bullets);
        }

        moveVehicles();

        if (plane.checkBombs() > 0) {
            for (Missile bomb : plane.getBombs()) {
                bomb.move();
                if (bomb.isExplosion()) {
                    if (bomb.isAtomic()) {
                        AtomicExplosion();
                    }
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
                if (buildings.get(i).getPicture().equals(bonusBombPic)) {
                    rewards.add(new FloatingReward(bonusBombPic,
                            buildings.get(i).getPositionX(),
                            buildings.get(i).getPositionY(), Bonus.PLUSBOMB));
                } else {
                    rewards.add(new FloatingReward(atomicBombPic,
                            buildings.get(i).getPositionX(),
                            buildings.get(i).getPositionY(), Bonus.ATOMIC));
                }
                buildings.remove(i);
            }
        }
    }

    private void checkVehicles(Missile bomb) {
        for (int i = vehicles.size() - 1; i > -1; i--) {
            if ((bomb.getPositionX() + 10) > vehicles.get(i).getPositionX()
                    && bomb.getPositionX() - 30 < vehicles.get(i).getPositionX()) {
                vehicles.remove(i);
                plane.setNumberOfKills(plane.getNumberOfKills() + 1);
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

    public void loadLastGame() {
        loadLevelInfo(0);
        JSONObject jo = new JSONObject(fromJson);
        plane.setNumOfAtomicBombs(jo.getInt("numOfAtomicBombs"));
        plane.setNumberOfKills(jo.getInt("numberOfKills"));
        plane.setNumOfAtomicBombs(jo.getInt("numOfAtomicBombs"));
        plane.setNumOfConcurrentBombsToDrop(jo.getInt("numOfConcurrentBombsToDrop"));
        plane.setPositionX(jo.getInt("planeX"));
        plane.setPositionY(jo.getInt("planeY"));
    }

    public void saveCurrentGame() {
        savedGameExists = true;
        int tanks = 0;
        int trucks = 0;
        int jeeps = 0;
        JSONObject o = new JSONObject(fromJson);
        JSONObject temp = new JSONObject();
        JSONObject jo = new JSONObject();
        JSONObject joHelp = new JSONObject();
        JSONArray ja = new JSONArray();
        jo.put("level", level);
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).isTank())
                tanks += 1;

            else if (vehicles.get(i).isTruck())
                trucks += 1;
            else
                jeeps += 1;
        }
        jo.put("numTanks", tanks);
        jo.put("numTrucks", trucks);
        jo.put("numJeeps", jeeps);
        jo.put("reachLineHeight", reachLineHeight);
        jo.put("numberOfKills", plane.getNumberOfKills());
        jo.put("numOfAtomicBombs", plane.getNumOfAtomicBombs());
        jo.put("numOfConcurrentBombsToDrop", plane.getNumOfConcurrentBombsToDrop());
        jo.put("planeX", plane.getPositionX());
        jo.put("planeY", plane.getPositionY());


        for (i = 0; i < buildings.size(); i++) {
            joHelp = new JSONObject();
            if (buildings.get(i).getPicture().equals(houseRedPic))
                joHelp.put("colour", "red");
            else
                joHelp.put("colour", "white");
            joHelp.put("xPos", buildings.get(i).getPositionX());
            joHelp.put("yPos", buildings.get(i).getPositionY());
            joHelp.put("bonus", buildings.get(i).getBon());
            ja.put(joHelp);
        }
        jo.put("buildings", ja);

        FileHandler.writeToFile(savedGameFileName, jo.toString());
    }

    private void startNextLevel() {
        String s;

        s = "JsonFiles\\level_" + (level + 1) + ".json";
        File check = new File(s);
        if (!check.isFile()) {
            Win = true;
            System.out.println(s);
            return;
        }
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
                if (rewards.get(i).getBenefit().equals(Bonus.PLUSBOMB)) {
                    plane.setNumOfConcurrentBombsToDrop(plane.getNumOfConcurrentBombsToDrop() + 1);
                } else {
                    plane.setNumOfAtomicBombs(plane.getNumOfAtomicBombs() + 1);
                }
                rewards.remove(i);
            }
        }
    }

    public LinkedList<Bullet> getBullets() {
        return bullets;
    }

    private void doShooting() {
        for (int i = vehicles.size() - 1; i > -1; i--) {
            if (vehicles.get(i).isTank()) {
                if (vehicles.get(i).getBullet() == null) {
                    vehicles.get(i).shoot();
                    bullets.add(vehicles.get(i).getBullet());
                } else if (!vehicles.get(i).getBullet().isActive()) {
                    vehicles.get(i).setBullet(null);
                } else {
                }
            }
        }
    }

    private void moveBullets(LinkedList<Bullet> bullets) {
        for (int i = bullets.size() - 1; i > -1; i--) {
            if (bullets.get(i).isActive()) {
                bullets.get(i).move();
                if ((plane.getPositionX() + 30 >= bullets.get(i).getPositionX())
                        && plane.getPositionX() - 15 < bullets.get(i).getPositionX()
                        && plane.getPositionY() - 5 < bullets.get(i).getPositionY()
                        && plane.getPositionY() + 20 > bullets.get(i).getPositionY()) {

                    plane.setDying(true);
                }
            } else
                bullets.remove(i);
        }
    }


    public void setLevelCounterTimeShow(int levelCounterTimeShow) {
        this.levelCounterTimeShow = levelCounterTimeShow;
    }

    public int getLevelCounterTimeShow() {
        return levelCounterTimeShow;
    }

    private void initLevel() {
        levelCounterTimeShow = 0;
        int i = 0;
        int coordX = 0;
        int speedDir = 1;
        for (i = 0; i < numJeeps; i++) {
            jeep = new Jeep(jeepPic, coordX, 310, 5 * speedDir);
            vehicles.add(jeep);
            coordX += 100;
            speedDir *= -1;
        }

        for (i = 0; i < numTrucks; i++) {
            truck = new Truck(truckPicLeft, truckPicRight, coordX, 310, 3 * speedDir);
            vehicles.add(truck);
            coordX += 70;
            speedDir *= -1;
        }

        for (i = 0; i < numTanks; i++) {
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
        skullPic = new ImageIcon("Pictures\\skull.png").getImage();
        atomicExplosionPic = new ImageIcon("Pictures\\AtomicExplosion.png").getImage();
    }

    public Image getAtomicExplosionPic() {
        return atomicExplosionPic;
    }

    public void planeShoot() {
        plane.shoot();
    }

    private boolean isExplosion = false;

    public void AtomicExplosion() {
        for (int i = 1; i <= vehicles.size(); i++) {
            plane.setNumberOfKills(plane.getNumberOfKills() + 1);
        }
        atomicCounter = 1;
        isExplosion = true;
    }

    public void planeShootAtomic() {
        plane.shootAtomic();
    }
}
