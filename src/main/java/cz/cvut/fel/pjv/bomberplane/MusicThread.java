package cz.cvut.fel.pjv.bomberplane;


public class MusicThread extends Thread {
    int counter = 0;
    Controller controller;

    public MusicThread(Controller controller) {
        this.controller = controller;
    }

    public void run() {
        while (true) {
            if (controller.isInGame() && controller.isIsMusic()) {
//                System.out.println("MUSICTHREAD");
                MusicController.playMusic();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MusicController.getClip().close();
                controller.setIsMusic(false);

            }
            System.out.println(controller.isInGame());
            controller.isInGame();
        }
    }
}
