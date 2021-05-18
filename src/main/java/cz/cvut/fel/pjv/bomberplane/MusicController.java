package cz.cvut.fel.pjv.bomberplane;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MusicController {
    static String filepath = "Music\\explosion.wav";
    static String musicLocation = "Music\\explosion.wav";
    static Clip clip;

    private static final Logger LOGGER = Logger.getLogger(FileHandler.class.getName());

    public static Clip getClip() {
        return clip;
    }

    public static void playMusic(){
        try{
            File musicPath = new File(musicLocation);
            if (musicPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                System.out.println(Thread.currentThread().getName());
                clip.start();
            }else{
                LOGGER.log(Level.WARNING, "Cannot find the Audio File");
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void stopMusic(){
        clip.stop();
    }
    }
