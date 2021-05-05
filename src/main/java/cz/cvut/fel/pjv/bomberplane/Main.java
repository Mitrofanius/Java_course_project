package cz.cvut.fel.pjv.bomberplane;

import javax.swing.JFrame;
import java.awt.*;

/**
 * Create a Frame, start the game
 * */
public class Main extends JFrame{
    public static int panelWidth = 650;
    public static int panelHeight = 400;

    public Main() {
        initUI();
    }

    private void initUI() {
        add(new Board());
        setTitle("Atomic Bomber");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(panelWidth, panelHeight);
        setLocationRelativeTo(null);

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {

            Main ex = new Main();
            ex.setVisible(true);

        });
    }
}
