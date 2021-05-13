package cz.cvut.fel.pjv.bomberplane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileReader {
    public static String read_file(String arg) {
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
}
