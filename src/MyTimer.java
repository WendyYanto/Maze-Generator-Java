
public class MyTimer implements Runnable{

	public Thread timeTicks = new Thread(this);
	private boolean pause = false;
	
	public MyTimer() {
		// TODO Auto-generated constructor stub
		timeTicks.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				Thread.sleep(1000);
				
				if(!pause) {
					if(GamePanel.currTime <= 0) {
						GamePanel.currTime = 0;
					}else {
						GamePanel.currTime -= 1;
					}	
				}
				Main.timeLeft.setText("Time Left : "+GamePanel.currTime);
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
