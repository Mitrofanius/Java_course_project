package cz.cvut.fel.pjv.bomberplane;


import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {

            Controller ex = new Controller();
            ex.setVisible(true);
            String filepath = "Music\\explosion.wav";
        });
    }
}

