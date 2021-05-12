package cz.cvut.fel.pjv.bomberplane;

import cz.cvut.fel.pjv.bomberplane.gameobjects.Building;
import org.json.JSONObject;
import org.json.JSONArray;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
//import org.apache.commons.io.IOUtils;


public class Variables {
    public Image getBombPic() {
        return bombPic;
    }

    private int i;

    private int numOfCurrentBombsToDrop = 0;

    public Image getExplosionPic() {
        return explosionPic;
    }

    String fromJson;
    private int level = 1;
    private int trucks;
    private int tanks;
    private int jeeps;
    private int numBuildings;
    private static int reachLineHeight;

    public static int getReachLineHeight() {
        return reachLineHeight;
    }

    public int getNumOfCurrentBombsToDrop() {
        return numOfCurrentBombsToDrop;
    }

    public LinkedList<Building> getBuildings() {
        return buildings;
    }

    private LinkedList<Building> buildings = new LinkedList<Building>();

    private Image jeepPic, truckPicLeft, tankPicLeft,
            tankPicUp, tankPicRight, bombPic, truckPicRight,
            explosionPic, houseRedPic, houseWhitePic, bonusBombPic,
            atomicBombPic, bombScorePic, lifePic;

    private static Image bulletPic;

    public static Image getBulletPic() {
        return bulletPic;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    private final Color backgroundColor = new Color(16, 174, 187, 129);


    public int getLevel() {
        return level;
    }

    public int getTrucks() {
        return trucks;
    }

    public int getTanks() {
        return tanks;
    }

    public int getJeeps() {
        return jeeps;
    }

    public int getTotalObjects() {
        return totalObjects;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setTrucks(int trucks) {
        this.trucks = trucks;
    }

    public void setTanks(int tanks) {
        this.tanks = tanks;
    }

    public void setJeeps(int jeeps) {
        this.jeeps = jeeps;
    }

    public void setTotalObjects(int totalObjects) {
        this.totalObjects = totalObjects;
    }

    private int totalObjects;

    public Image getJeepPic() {
        return jeepPic;
    }

    public Variables() {
//        initVariables();
        loadPics();
    }

    public void loadLevelInfo(int num) {
        level = num;
        String lvlFile = "level_" + num + ".json";
        fromJson = read_file(("JsonFiles\\" + lvlFile));
        System.out.println(fromJson);
//        parseJson(fromJson);
        JSONObject temp;
        JSONObject o = new JSONObject(fromJson);
        JSONArray static_objs = o.getJSONArray("buildings");
        for (i = 0; i < static_objs.length(); i++) {
            temp = static_objs.getJSONObject(i);
            if (temp.getString("colour").equals("white")) {
                buildings.add(new Building(houseWhitePic, temp.getInt("xPos"), temp.getInt("yPos"), temp.getString("bonus")));
            }
            else {
                buildings.add(new Building(houseRedPic, temp.getInt("xPos"), temp.getInt("yPos"), temp.getString("bonus")));
            }
        }

        setTanks(o.getInt("numTanks"));
        setTrucks(o.getInt("numTrucks"));
        setJeeps(o.getInt("numJeeps"));
        reachLineHeight = o.getInt("reachLineHeight");
        if (numOfCurrentBombsToDrop == 0) {
            numOfCurrentBombsToDrop = o.getInt("numOfCurrentBombsToDrop");
        }
    }

//    public void parseJson(String input) {
//        JSONObject o = new JSONObject(input);
////        JSONObject level = o.getJSONObject("level");
//        System.out.println("\n\n\n" + "This is the level " + o.getInt("level"));
//    }

    public Image getTruckPicLeft() {
        return truckPicLeft;
    }

    public Image getTankPicLeft() {
        return tankPicLeft;
    }

    public String read_file(String arg) {
        String everything = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(arg);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
//            everything = IOUtils.toString(inputStream);
            try {
                everything = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return everything;
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

    public Image getAtomicBombPic() {
        return atomicBombPic;
    }

    public Image getBombScorePic() {
        return bombScorePic;
    }

    public Image getLifePic() {
        return lifePic;
    }

    public Image getTankPicUp() {
        return tankPicUp;
    }

    public Image getTankPicRight() {
        return tankPicRight;
    }

    public Image getTruckPicRight() {
        return truckPicRight;
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
}
