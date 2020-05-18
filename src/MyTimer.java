
public class MyTimer implements Runnable {

    private Thread thread = new Thread(this);
    private boolean pause = false;

    MyTimer() {
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                if (!pause) {
                    if (GamePanel.currTime <= 0) {
                        GamePanel.currTime = 0;
                    } else {
                        GamePanel.currTime -= 1;
                    }
                }
                Main.timeLeft.setText("Time Left : " + GamePanel.currTime);
            } catch (Exception e) {
                thread.interrupt();
            }
        }
    }

    void setPause() {
        this.pause = true;
    }

    void setResume() {
        this.pause = false;
    }

    boolean getPauseStatus() {
        return this.pause;
    }
}
