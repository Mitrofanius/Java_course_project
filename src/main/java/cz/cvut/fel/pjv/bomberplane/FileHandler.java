package cz.cvut.fel.pjv.bomberplane;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHandler {
    public static String readFile(String arg) {
        String everything = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(arg);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
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
    private static final Logger LOGGER = Logger.getLogger(FileHandler.class.getName());


    public static void writeToFile(String fileName, String content) {
        try {
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(content);

            myWriter.close();
            LOGGER.log(Level.SEVERE, "Game saved.");

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error occured while saving the game");
            e.printStackTrace();
        }
    }
}
