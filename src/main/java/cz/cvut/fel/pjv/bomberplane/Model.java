package cz.cvut.fel.pjv.bomberplane;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import cz.cvut.fel.pjv.bomberplane.gameobjects.*;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A class that provides methods for
 * interaction between the objects
 * and updating the game state.
 * <p>
 * All methods starting with "check"
 * check some list of game objects
 * in a "for" loop and
 * delete inactive entities
 * <p>
 * All methods starting with "move"
 * update coordinates of the game objects
 * on the map
 * <p>
 * Contains methods to initialize levels,
 * to parse level information
 * from json and saving game states to json.
 */
public class Model {
    // Variables declaration
    private int atomicCounter = 0;
    private boolean savedGameExists;
    private String savedGameFileName = "JsonFiles\\saved_game.json";

    public boolean isSavedGameExists() {
        return savedGameExists;
    }

    private static final Logger LOGGER = Logger.getLogger(Model.class.getName());


    public final int width = 650;

    public int getHeight() {
        return height;
    }

    public final int height = 400;

    public int getWidth() {
        return width;
    }

    private Image jeepPic, truckPicLeft, tankPicLeft, tankPicUp,
            tankPicRight, bombPic, truckPicRight, explosionPic,
            houseRedPic, houseWhitePic, bonusBombPic, atomicBombPic,
            bombScorePic, lifePic, skullPic, atomicExplosionPic, killerPic;
    private Image bulletPic;

    String fromJson;
    private int level = 1;
    private int groundY = 300;
    private int numTrucks;
    private int numTanks;
    private int numJeeps;
    private int reachLineHeight;

    private int i;

    private Plane plane;
    private Jeep jeep;
    private Truck truck;
    private Tank tank;
    private Killer killer;
    private int speed = 4;

    private boolean Win = false;
    private int levelCounterTimeShow = 0;

    private LinkedList<Vehicle> vehicles;
    private LinkedList<Bullet> bullets;
    private LinkedList<FloatingReward> rewards;
    private LinkedList<Building> buildings;
    private LinkedList<Killer> killers;

//    End of variables declaration

    public Model() {
        initBoard();
    }

    /**
     * This method is called from constructor,
     * creates the plane, loads pictures
     * and set initial parameters to the game board/model -
     * downloads them from a file
     */
    private void initBoard() {
        savedGameExists = false;
        if (!FileHandler.readFile(savedGameFileName).equals("NOINFO")) {
            savedGameExists = true;
        }
        plane = new Plane(speed, 1, 0, width, height);
        loadPics();
        setInitParams();
    }

    /**
     * loads level information from a
     * json file
     *
     * @param num
     */
    public void loadLevelInfo(int num) {
        if (num == 0) {
            fromJson = FileHandler.readFile(savedGameFileName);
        } else {
            String lvlFile = "level_" + num + ".json";
            fromJson = FileHandler.readFile(("JsonFiles\\" + lvlFile));
        }
        JSONObject o = new JSONObject(fromJson);
        level = o.getInt("level");

        createBuildings(o);
        createKillers(o);

        numTanks = o.getInt("numTanks");
        numTrucks = o.getInt("numTrucks");
        numJeeps = o.getInt("numJeeps");
        reachLineHeight = o.getInt("reachLineHeight");
        if (plane.getNumOfConcurrentBombsToDrop() == 0) {
            plane.setNumOfConcurrentBombsToDrop(o.getInt("numOfCurrentBombsToDrop"));
        }
    }

    public void createBuildings(JSONObject jo) {
        JSONArray static_objs = jo.getJSONArray("buildings");
        JSONObject temp;
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
    }

    public void createKillers(JSONObject jo) {
        JSONObject temp;
        JSONArray killersJO = jo.getJSONArray("killers");
        for (i = 0; i < killersJO.length(); i++) {
            temp = killersJO.getJSONObject(i);
            killer = new Killer(killerPic, temp.getInt("xPos"), temp.getInt("yPos"), temp.getInt("killerTimer"));
            killers.add(killer);
        }
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

    public Image getKillerPic() {
        return killerPic;
    }

    public LinkedList<Killer> getKillers() {
        return killers;
    }

    public void death() {
        LOGGER.log(Level.INFO, "Plane has been terminated");
        if (plane.getNumberOfLives() > 1) {
            plane.setSpeedX(speed);
            plane.setSpeedY(0);
            plane.setDir(1);
            plane.setNumOfAtomicBombs(0);
            plane.setNumOfConcurrentBombsToDrop(1);
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

    public void setInitParams() {
        bullets = new LinkedList<Bullet>();
        vehicles = new LinkedList<Vehicle>();
        buildings = new LinkedList<Building>();
        rewards = new LinkedList<FloatingReward>();
        killers = new LinkedList<Killer>();
        plane.setPositionX(0);
        plane.setPositionY(0);
        plane.setPicture(plane.getPlanerigth());
        plane.setDir(1);
        plane.setSpeedX(plane.getSpeed());
        plane.setSpeedY(0);
        plane.setNumOfConcurrentBombsToDrop(0);
        plane.setNumOfAtomicBombs(0);
        plane.setNumberOfKills(0);
        plane.setDying(false);
        loadLevelInfo(1);
        initLevel();
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
            doTankShooting();
            moveBullets(bullets);
        } else {
            moveBullets(bullets);
        }

        moveKillers();
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
                    checkKillers(bomb);
                }
            }
        }


    }

    private void checkKillers(Missile bomb) {
        for (int i = killers.size() - 1; i > -1; i--) {
            if (((bomb.getPositionX() + 10) > killers.get(i).getPositionX()
                    && bomb.getPositionX() - 30 < killers.get(i).getPositionX()
                    && killers.get(i).getPositionY() >= groundY - 20)
                    || killers.get(i).getPositionY() < 0) {
                killers.remove(i);
                LOGGER.log(Level.INFO, "Enemy's rocket destroyed");
            }
        }
    }

    private void checkBuildings(Missile bomb) {
        for (int i = buildings.size() - 1; i > -1; i--) {
            if ((bomb.getPositionX() + 10) > buildings.get(i).getPositionX()
                    && bomb.getPositionX() - 40 < buildings.get(i).getPositionX()) {
                if (buildings.get(i).getBon().equals(BonusType.PLUSBOMB)) {
                    rewards.add(new FloatingReward(bonusBombPic,
                            buildings.get(i).getPositionX(),
                            buildings.get(i).getPositionY(), BonusType.PLUSBOMB));
                } else {
                    rewards.add(new FloatingReward(atomicBombPic,
                            buildings.get(i).getPositionX(),
                            buildings.get(i).getPositionY(), BonusType.ATOMIC));
                }
                buildings.remove(i);
                LOGGER.log(Level.INFO, "Enemy's building destroyed");

            }
        }
    }

    private void checkVehicles(Missile bomb) {
        for (int i = vehicles.size() - 1; i > -1; i--) {
            if ((bomb.getPositionX() + 10) > vehicles.get(i).getPositionX()
                    && bomb.getPositionX() - 30 < vehicles.get(i).getPositionX()) {
                vehicles.remove(i);
                LOGGER.log(Level.INFO, "Enemy's vehicle destroyed");
                plane.setNumberOfKills(plane.getNumberOfKills() + 1);
            }
        }
    }

    private void moveKillers() {
        for (int i = killers.size() - 1; i > -1; i--) {
            if (killers.get(i).isActive()) {
                killers.get(i).move();
                if (((plane.getPositionX() + 30 >= killers.get(i).getPositionX())
                        && plane.getPositionX() - 15 < killers.get(i).getPositionX()
                        && plane.getPositionY() - 6 < killers.get(i).getPositionY()
                        && plane.getPositionY() + 20 > killers.get(i).getPositionY())) {

                    plane.setDying(true);
                    killers.remove(i);
                }
            } else if (killers.get(i).getPositionY() <= 0) {
                killers.remove(i);
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
        buildings = new LinkedList<Building>();
        vehicles = new LinkedList<Vehicle>();
        buildings = new LinkedList<Building>();
        bullets = new LinkedList<Bullet>();
        killers = new LinkedList<Killer>();
        loadLevelInfo(0);
        fromJson = FileHandler.readFile(savedGameFileName);
        JSONObject jo = new JSONObject(fromJson);
        plane.setNumOfAtomicBombs(jo.getInt("numOfAtomicBombs"));
        plane.setNumberOfKills(jo.getInt("numberOfKills"));
        plane.setNumOfAtomicBombs(jo.getInt("numOfAtomicBombs"));
        plane.setNumOfConcurrentBombsToDrop(jo.getInt("numOfConcurrentBombsToDrop"));
        plane.setPositionX(jo.getInt("planeX"));
        plane.setPositionY(jo.getInt("planeY"));
        initLevel();
    }

    public void saveCurrentGame() {
        savedGameExists = true;
        int tanks = 0;
        int trucks = 0;
        int jeeps = 0;
        JSONObject jo = new JSONObject();
        JSONObject joHelp;
        jo.put("level", level);
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).isTank()) {
                tanks += 1;
            } else if (vehicles.get(i).isTruck())
                trucks += 1;
            else
                jeeps += 1;
        }

        JSONArray ja = new JSONArray();

        for (int i = 0; i < killers.size(); i++) {
            joHelp = new JSONObject();
            joHelp.put("xPos", killers.get(i).getPositionX());
            joHelp.put("yPos", killers.get(i).getPositionY());
            joHelp.put("killerTimer", 0);
            ja.put(joHelp);
        }
        jo.put("killers", ja);
        ja = new JSONArray();
        jo.put("numTanks", tanks);
        jo.put("numTrucks", trucks);
        jo.put("numJeeps", jeeps);
        jo.put("reachLineHeight", reachLineHeight);
        jo.put("numberOfKills", plane.getNumberOfKills());
        jo.put("numOfAtomicBombs", plane.getNumOfAtomicBombs());
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

    public boolean isWin() {
        return Win;
    }

    private void startNextLevel() {
        String s;

        s = "JsonFiles\\level_" + (level + 1) + ".json";
        File check = new File(s);
        if (!check.isFile()) {
            Win = true;
            LOGGER.log(Level.INFO, "Last level ended");
            LOGGER.log(Level.WARNING, "No more levels can be loaded");
            FileHandler.writeToFile(savedGameFileName, "NOINFO");
            return;
        }
        loadLevelInfo(level + 1);
        initLevel();
    }


    public Image getExplosionPic() {
        return explosionPic;
    }

    public Image getSkullPic() {
        return skullPic;
    }


    public int getReachLineHeight() {
        return reachLineHeight;
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

    public Image getBulletPic() {
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

                if (rewards.get(i).getBenefit().equals(BonusType.PLUSBOMB)) {
                    LOGGER.log(Level.INFO, "Bonus is caught, TYPE PLUSBOMB");
                    plane.setNumOfConcurrentBombsToDrop(plane.getNumOfConcurrentBombsToDrop() + 1);
                } else {
                    LOGGER.log(Level.INFO, "Bonus is caught, TYPE ATOMIC");
                    plane.setNumOfAtomicBombs(plane.getNumOfAtomicBombs() + 1);
                }
                rewards.remove(i);
            }
        }
    }

    public LinkedList<Bullet> getBullets() {
        return bullets;
    }

    private void doTankShooting() {
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
        if (plane.getNumberOfLives() == 0) {
            plane.setNumberOfLives(2);
        }
        int i = 0;
        int coordX = 0;
        int speedDir = 1;
        for (i = 0; i < numJeeps; i++) {
            jeep = new Jeep(jeepPic, coordX, 310, 5 * speedDir, width);
            vehicles.add(jeep);
            coordX += 100;
            speedDir *= -1;
        }

        for (i = 0; i < numTrucks; i++) {
            truck = new Truck(truckPicLeft, truckPicRight, coordX, 310, 3 * speedDir, width);
            vehicles.add(truck);
            coordX += 70;
            speedDir *= -1;
        }

        for (i = 0; i < numTanks; i++) {
            tank = new Tank(tankPicLeft, tankPicRight, tankPicUp, coordX, 310,
                    3 * speedDir, width, reachLineHeight, bulletPic);
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
        killerPic = new ImageIcon("Pictures\\killer.png").getImage();
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
        LOGGER.log(Level.INFO, "Atomic bomb exploded");

        isExplosion = true;
    }

    public void playMusic(String musicLocation){

        try{
            File musicPath = new File(musicLocation);
            if (musicPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }else{
                System.out.println("Cannot find the Audio   File");
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void planeShootAtomic() {
        plane.shootAtomic();
    }
}
