package cz.cvut.fel.pjv.bomberplane;

import java.io.*;
import java.nio.charset.StandardCharsets;

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

    public static void writeToFile(String fileName, String content) {
        try {
            File file = new File(fileName);
//            if (!file.exists()){
//                file.createNewFile();
//            }
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(content);

            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
