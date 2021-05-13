package cz.cvut.fel.pjv.bomberplane;

import javax.swing.*;
import java.awt.*;

public class MenuView extends JPanel {
    Model gameModel;
    private final Font bigFont = new Font("Helvetica", Font.BOLD, 24);


    public MenuView(Model model){
        gameModel = model;
    }
    private void showIntroScreen(Graphics2D g2d) {
        g2d.setColor(new Color(16, 92, 130));
        g2d.fillRect(50, gameModel.width / 2 - 30, gameModel.width - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, gameModel.width / 2 - 30, gameModel.width - 100, 50);

        String s = "Press space to start a new Game";
        FontMetrics metr = this.getFontMetrics(bigFont);

        g2d.setColor(Color.white);
        g2d.setFont(bigFont);
        g2d.drawString(s, gameModel.width / 2 - 190, (gameModel.width) / 2);

        g2d.setColor(new Color(16, 92, 130));
        g2d.fillRect(50, gameModel.width / 2 + 20, gameModel.width - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, gameModel.width / 2 + 20, gameModel.width - 100, 50);
        s = "Continue your previous Game";
        g2d.drawString(s, gameModel.width / 2 - 190, (gameModel.width) / 2 + 50);

    }
}
