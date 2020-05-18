package timer;

import contract.GameContract;
import contract.MainContract;

public class GameTimer implements Runnable {

    private boolean pause = false;
    private MainContract mainContract;
    private GameContract gameContract;

    public GameTimer(MainContract mainContract, GameContract gameContract) {
        this.mainContract = mainContract;
        this.gameContract = gameContract;
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                if (!pause) {
                    int time = gameContract.getTime();
                    if (time <= 0) {
                        gameContract.setTime(0);
                    } else {
                        gameContract.setTime(time - 1);
                    }
                }
                mainContract.setTime(gameContract.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setPause() {
        this.pause = true;
    }

    public void setResume() {
        this.pause = false;
    }

    public boolean getPauseStatus() {
        return this.pause;
    }
}
