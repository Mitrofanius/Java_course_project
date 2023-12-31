package cz.cvut.fel.pjv.bomberplane;

import cz.cvut.fel.pjv.bomberplane.gameobjects.*;

import javax.swing.*;
import java.awt.*;

/**
 * All drawing of graphics is done here.
 * And nothing more.
 * Ask model about the states of the game objects ->
 * draw them on the map
 *
 *      Hierarchy:
 * Controller -> View.drawView() -> repaint() (awt method) ->
 * paintComponent(awt method) -> this.doDrawing() ->
 * asks the model about the state and draws one of the views:
 * either game or one of menus
 */
public class View extends JPanel {
    private Color myColor = new Color(16, 174, 187, 129);
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);
    private final Font bigFont = new Font("Helvetica", Font.BOLD, 24);
    private int menuCoordX = 45;

    Model gameModel;

    public View(Model model) {
        gameModel = model;

    }

    public void drawView() {
        repaint();
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(myColor);
        g2d.fillRect(0, 0, gameModel.width, gameModel.height);
        g2d.setColor(new Color(19, 177, 19));
        g2d.fillRect(0, 320, gameModel.width, gameModel.height);

        if (gameModel.getController().isInGame()) {
            drawGame(g2d);
        } else if (gameModel.getController().isInIntro()) {
            showIntroScreen(g2d);
        } else if (gameModel.getController().isInPause()) {
            drawGame(g2d);
            showPauseScreen(g2d);
        } else if (gameModel.getController().isInOutro()) {
            drawGame(g2d);
            showOutroScreen(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    private void drawGame(Graphics2D g2d) {

        if (gameModel.getPlane().isDying()) {
            g2d.drawImage(gameModel.getExplosionPic(), gameModel.getPlane().getPositionX(), gameModel.getPlane().getPositionY(), this);
        }

        if (gameModel.getAtomicCounter() < 50 && gameModel.getAtomicCounter() != 0) {
            gameModel.setAtomicCounter(gameModel.getAtomicCounter() + 1);
            drawAtomicExplosion(g2d, 100, -100);
            drawObj(g2d, gameModel.getPlane());
            return;
        }

        drawHills(g2d);
        if (gameModel.getLevelCounterTimeShow() < 50) {
            drawLevelNumber(g2d);
        }


        drawKillers(g2d);

        drawBuildings(g2d);

        drawRewards(g2d);

        drawVehicles(g2d);

        drawBullets(g2d);
        drawScore(g2d);
        drawDottedLine(g2d);
        drawObj(g2d, gameModel.getPlane());

        for (Missile bomb : gameModel.getPlane().getBombs()) {
            drawObj(g2d, bomb);
            if (bomb.isExplosion()) {
                g2d.drawImage(gameModel.getExplosionPic(), bomb.getPositionX(), bomb.getPositionY(), this);
            }
        }
    }

    private void drawKillers(Graphics2D g2d) {
        for (int i = gameModel.getKillers().size() - 1; i > -1; i--) {
            drawObj(g2d, gameModel.getKillers().get(i));
        }
    }

    private void drawBuildings(Graphics2D g2d) {
        for (int i = gameModel.getBuildings().size() - 1; i > -1; i--) {
            drawObj(g2d, gameModel.getBuildings().get(i));
        }
    }

    private void drawRewards(Graphics2D g2d) {
        for (int i = gameModel.getRewards().size() - 1; i > -1; i--) {
            drawObj(g2d, gameModel.getRewards().get(i));
        }
    }

    private void drawVehicles(Graphics2D g2d) {
        for (int i = gameModel.getVehicles().size() - 1; i > -1; i--) {
            drawObj(g2d, gameModel.getVehicles().get(i));
        }
    }

    private void drawHills(Graphics2D g2d) {
        g2d.fillOval(0, 315, 70, 50);
        g2d.fillOval(50, 310, 70, 30);
        g2d.fillOval(150, 310, 100, 30);
        g2d.fillOval(350, 310, 80, 50);
        g2d.fillOval(500, 300, 70, 50);
    }

    private void showIntroScreen(Graphics2D g2d) {
        g2d.setColor(new Color(16, 92, 130));
        g2d.fillRect(0, 0, gameModel.width, gameModel.height);
        g2d.setColor(new Color(16, 92, 130));
        g2d.fillRect(menuCoordX, gameModel.height / 2 - 30, gameModel.width - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(menuCoordX, gameModel.height / 2 - 30, gameModel.width - 100, 50);

        String s = "Press \"Space\" to start a new Game";
        FontMetrics metr = this.getFontMetrics(bigFont);

        g2d.setColor(Color.white);
        g2d.setFont(bigFont);
        g2d.drawString(s, gameModel.width / 2 - 210, (gameModel.height) / 2);

        g2d.setColor(new Color(16, 92, 130));
        g2d.fillRect(menuCoordX, gameModel.height / 2 + 20, gameModel.width - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(menuCoordX, gameModel.height / 2 + 20, gameModel.width - 100, 50);
        if (gameModel.isSavedGameExists()) {
            s = "Press \"C\" to continue your previous Game";
        }
        else{
            s = "Currently you have no saved game to load";
        }
        g2d.drawString(s, gameModel.width / 2 - 245, (gameModel.height) / 2 + 50);
    }

    private void showPauseScreen(Graphics2D g2d) {

        String s = "Game Paused";

        g2d.setColor(Color.white);
        g2d.setFont(bigFont);
        g2d.drawString(s, gameModel.width / 2 - 90, (gameModel.height) / 2 - 50);

        g2d.setColor(new Color(16, 92, 130));
        g2d.fillRect(50, gameModel.height / 2 - 30, gameModel.width - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, gameModel.height / 2 - 30, gameModel.width - 100, 50);

        s = "Press \"Esc\" to exit";
        g2d.setFont(bigFont);
        g2d.drawString(s, gameModel.width / 2 - 120, (gameModel.height) / 2);

        g2d.setColor(new Color(16, 92, 130));
        g2d.fillRect(50, gameModel.height / 2 + 20, gameModel.width - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, gameModel.height / 2 + 20, gameModel.width - 100, 50);
        s = "Press \"Space\" to continue";
        g2d.drawString(s, gameModel.width / 2 - 150, (gameModel.height) / 2 + 50);
    }

    private void showOutroScreen(Graphics2D g2d) {
        gameModel.getController().setInGame(false);
        g2d.setColor(new Color(16, 92, 130));
        g2d.fillRect(50, gameModel.height / 2 - 30, gameModel.width - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, gameModel.height / 2 - 30, gameModel.width - 100, 50);

        String s = "Enough, you are too good";
        Font small = new Font("Helvetica", Font.BOLD, 24);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, gameModel.width / 2 - 150, (gameModel.height) / 2);
    }

    private void drawScore(Graphics2D g2d) {
        int i;
        String s;
        g2d.setFont(smallFont);
        g2d.setColor(Color.white);

        g2d.drawImage(gameModel.getBombScorePic(), 0, 2, this);
        s = "x" + gameModel.getPlane().getNumOfConcurrentBombsToDrop();
        g2d.drawString(s, 14, 16);

        g2d.drawImage(gameModel.getAtomicBombPic(), 30, 0, this);
        s = "x" + gameModel.getPlane().getNumOfAtomicBombs();
        g2d.drawString(s, 50, 16);

        g2d.drawImage(gameModel.getLifePic(), 66, 0, this);
        s = "x" + gameModel.getPlane().getNumberOfLives();
        g2d.drawString(s, 92, 16);

        g2d.drawImage(gameModel.getSkullPic(), 108, 4, this);
        s = "x" + gameModel.getPlane().getNumberOfKills();
        g2d.drawString(s, 122, 16);

    }

    private void drawAtomicExplosion(Graphics2D g2d, int x, int y) {
        g2d.drawImage(gameModel.getAtomicExplosionPic(), x, y, this);
    }

    private void drawObj(Graphics2D g2d, MainGameObj obj) {
        g2d.drawImage(obj.getPicture(), obj.getPositionX(), obj.getPositionY(), this);
    }

    private void drawObj(Graphics2D g2d, Building obj) {
        g2d.drawImage(obj.getPicture(), obj.getPositionX(), obj.getPositionY(),
                this);
    }

    private void drawObj(Graphics2D g2d, FloatingReward obj) {
        g2d.drawImage(obj.getPicture(), obj.getPositionX(), obj.getPositionY(),
                this);
    }

    private void drawObj(Graphics2D g2d, Bullet obj) {
        g2d.drawImage(obj.getPicture(), obj.getPositionX(), obj.getPositionY(), this);
    }


    void drawDottedLine(Graphics2D g2d) {
        g2d.setColor(new Color(0x70F1DBDB, true));
        for (int i = 0; i < gameModel.width; i += 6) {
            g2d.drawLine(i, gameModel.getReachLineHeight(), i + 2, gameModel.getReachLineHeight());
        }
    }

    private void drawBullets(Graphics2D g2d) {
        for (int i = 0; i < gameModel.getBullets().size(); i++) {
            drawObj(g2d, gameModel.getBullets().get(i));
        }
    }

    private void drawLevelNumber(Graphics2D g2d) {
        String s;
        g2d.setFont(bigFont);
        g2d.setColor(Color.white);
        s = "Level " + (gameModel.getLevel());
        g2d.drawString(s, gameModel.width / 2 - 50, 100);
        gameModel.setLevelCounterTimeShow(gameModel.getLevelCounterTimeShow() + 1);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
}