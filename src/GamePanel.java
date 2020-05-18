import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener{

	private int rows;
	private int cols;
	private int lifePoints = 3;
	private int currLevel = 1;
	public static int currTime = 25;
	int posX = 1;
	int posY = 1;
	public ArrayList<Tile> tileMatrix = new ArrayList<Tile>();
	public static MyTimer TimerStart;

	public GamePanel() {
		generatePacket(21,21);

		Thread playThread = new Thread(this);
		addKeyListener(this);
		playThread.start();

		setFocusable(true);
		setSize(400, 400);

		TimerStart = new MyTimer();
		TimerStart.setPause();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;

		tileMatrix.get(getIndex(this.rows-2,this.cols-2)).setStatus("TARGET");
		for(int i=0;i<tileMatrix.size();i++) {
			Tile curr = tileMatrix.get(i);
			if(curr.getStatus() == "WALL") {
				g2d.setColor(Color.BLACK);
			}else if(curr.getStatus() == "PATH" || curr.getStatus() == "START") {
				g2d.setColor(Color.WHITE);
			}else if(curr.getStatus() == "TARGET") {
				g2d.setColor(Color.decode("#00C3E5"));
			}else if(curr.getStatus() == "TRAP") {
				g2d.setColor(Color.decode("#ED020A"));
			}else if(curr.getStatus() == "COIN") {
				g2d.setColor(Color.decode("#FBC02D"));
			}

			g2d.fillRect(curr.getCol() + (curr.getCol()*curr.getWidth()), curr.getRow() + (curr.getRow()*curr.getWidth()), curr.getWidth(), curr.getWidth());
			g2d.drawRect(curr.getCol() + (curr.getCol()*curr.getWidth()), curr.getRow() + (curr.getRow()*curr.getWidth()), curr.getWidth(), curr.getWidth());
		}

		g2d.setColor(Color.decode("#07F723"));
		g2d.fillRect(posX + (posX * 20) , posY + (posY * 20), 20, 20);
		g2d.drawRect(posX + (posX * 20) , posY + (posY * 20), 20, 20);
	}

	public void generatePacket(int row,int col) {

		this.rows = row;
		this.cols = col;
		for(int i=0;i<this.rows;i++) {
			for(int j=0;j<this.cols;j++) {
				Tile myTile = new Tile(j,i,20);
				tileMatrix.add(myTile);
			}
		}

		generate();
		generateTrap();
		generateCoin();
	}

	public void generateTrap() {
		int counter = 0;
		Tile temp;

		while(true) {
			int t = new Random().nextInt(tileMatrix.size());
			temp = tileMatrix.get(t);

			if(temp.getStatus() == "PATH") {
				counter++;
				temp.setStatus("TRAP");
			}

			if(counter==3) {
				break;
			}
		}
	}

	public void generateCoin() {
		int counter = 0;
		Tile temp;

		while(true) {
			int t = new Random().nextInt(tileMatrix.size());
			temp = tileMatrix.get(t);

			if(temp.getStatus() == "PATH") {
				counter++;
				temp.setStatus("COIN");
			}

			if(counter==5) {
				break;
			}
		}
	}

	public void generate() {
		Stack<Tile> cellStack = new Stack<Tile>();
		Tile curr = tileMatrix.get(getIndex(posX,posY));
		curr.setVisited();
		curr.setStatus("START");
		cellStack.push(curr);

		while(!cellStack.empty()) {

			Tile currTile = cellStack.peek();
			cellStack.pop();
			Tile neighbor = getNeighbors(currTile.getRow(),currTile.getCol());

			if(neighbor == null) {
				continue;
			}else {
				while(neighbor != null) {
					neighbor.setStatus("PATH");
					neighbor = getNeighbors(neighbor.getRow(), neighbor.getCol());
					if(neighbor != null) {
						cellStack.push(neighbor);
						neighbor.setStatus("PATH");
						neighbor.setVisited();
					}
				}
			}
		}
	}

	public int getIndex(int i,int j) {
		if(i<=0 || i>=(this.rows-1) || j<=0 || j>=(this.cols-1)) {
			return -1;
		}

		return i*(this.rows) + j;
	}

	public Tile getNeighbors(int myRow,int myCol) {

		ArrayList<Tile> myNeighbors = new ArrayList<Tile>();
		Tile upNeighbor,downNeighbor,leftNeighbor,rightNeighbor,temp;

		if(getIndex(myRow-1,myCol) != -1 && getIndex(myRow-2,myCol) != -1) {
			upNeighbor = tileMatrix.get(getIndex(myRow-2,myCol));
			if(upNeighbor != null && !upNeighbor.isVisited()) {
				myNeighbors.add(upNeighbor);
			}
		}

		if(getIndex(myRow+1,myCol) != -1 && getIndex(myRow+2,myCol) != -1) {
			downNeighbor = tileMatrix.get(getIndex(myRow+2,myCol));
			if(downNeighbor != null &&!downNeighbor.isVisited()) {
				myNeighbors.add(downNeighbor);
			}
		}

		if(getIndex(myRow,myCol-1) != -1 && getIndex(myRow,myCol-2) != -1) {
			leftNeighbor = tileMatrix.get(getIndex(myRow,myCol-2));
			if(leftNeighbor != null && !leftNeighbor.isVisited()) {
				myNeighbors.add(leftNeighbor);
			}

		}

		if(getIndex(myRow,myCol+1) != -1 && getIndex(myRow,myCol+2) != -1) {
			rightNeighbor = tileMatrix.get(getIndex(myRow,myCol+2));
			if(rightNeighbor != null && !rightNeighbor.isVisited()) {
				myNeighbors.add(rightNeighbor);
			}
		}

		if(myNeighbors.isEmpty()) {
			return null;
		}

		int r = new Random().nextInt(myNeighbors.size());
		temp = myNeighbors.get(r);

		if(myRow - temp.getRow() == 0 && temp.getCol() - myCol == 2) {
			Tile mid = tileMatrix.get(getIndex(myRow,myCol+1));
			mid.setStatus("PATH");
		}else if(myRow - temp.getRow() == 0 && temp.getCol() - myCol == -2) {
			Tile mid = tileMatrix.get(getIndex(myRow,myCol-1));
			mid.setStatus("PATH");
		}else if(myCol - temp.getCol() == 0 && temp.getRow() - myRow == 2) {
			Tile mid = tileMatrix.get(getIndex(myRow+1,myCol));
			mid.setStatus("PATH");
		}else if(myCol - temp.getCol() == 0 && temp.getRow() - myRow == -2) {
			Tile mid = tileMatrix.get(getIndex(myRow-1,myCol));
			mid.setStatus("PATH");
		}

		myNeighbors.clear();
		return temp;
	}

	public void reset() {
		tileMatrix.clear();
		TimerStart.setPause();
		this.removeAll();
		posX = 1;
		posY = 1;
		this.lifePoints = 3;
		this.currLevel += 1;
		currTime = currTime - 3;
		generatePacket(this.rows,this.cols);
		this.validate();
		Main.totalLife.setText("Life : "+this.lifePoints);
		Main.currLevel.setText("Level : "+this.currLevel);
	}

	public void resetTotal() {
		tileMatrix.clear();
		TimerStart.setPause();
		this.removeAll();
		posX = 1;
		posY = 1;
		this.lifePoints = 3;
		this.currLevel = 1;
		currTime = 25;
		generatePacket(this.rows,this.cols);
		this.validate();
		Main.totalLife.setText("Life : "+this.lifePoints);
		Main.currLevel.setText("Level : "+this.currLevel);
		Main.timeLeft.setText("Time Left : "+currTime);
	}

	public void getToTrap() {
		TimerStart.setPause();
		new DialogBox("LOSE_LIFE");
		this.lifePoints--;
		Main.totalLife.setText("Life : "+this.lifePoints);
	}

	public void addTime() {
		TimerStart.setPause();
		currTime += 5;
	}

	@Override
	public void run() {
		while(true){

			repaint();

			if(currTime == 0) {
				new DialogBox("LOSE");
				resetTotal();
			}

			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if(TimerStart.getPauseStatus()) {
			TimerStart.setResume();
		}

		if(key == KeyEvent.VK_UP) {
			int index = getIndex(posY-1,posX);
			if(index != -1) {
				Tile curr = tileMatrix.get(index);
				if(curr.getStatus() != "WALL") {
					boolean valid = true;
					if(curr.getStatus() == "TRAP") {
						getToTrap();
					}else if(curr.getStatus() == "COIN") {
						addTime();
						curr.setStatus("PATH");
					}else if(curr.getStatus() == "TARGET" && this.currLevel < 7){
						valid = false;
						reset();
					}

					if(valid){
						posY-=1;
					}
				}
			}
		}else if(key == KeyEvent.VK_DOWN) {
			int index = getIndex(posY+1,posX);

			if(index != -1) {
				Tile curr = tileMatrix.get(index);
				if(curr.getStatus() != "WALL") {
					boolean valid = true;

					if(curr.getStatus() == "TRAP") {
						getToTrap();
					}else if(curr.getStatus() == "COIN") {
						addTime();
						curr.setStatus("PATH");
					}else if(curr.getStatus() == "TARGET" && this.currLevel < 7){
						valid = false;
						reset();
					}

					if(valid){
						posY += 1;
					}
				}
			}

		}else if(key == KeyEvent.VK_LEFT) {
			int index = getIndex(posY,posX-1);

			if(index != -1) {
				Tile curr = tileMatrix.get(index);
				if(curr.getStatus() != "WALL") {
					boolean valid = true;

					if(curr.getStatus() == "TRAP") {
						getToTrap();
					}else if(curr.getStatus() == "COIN") {
						addTime();
						curr.setStatus("PATH");
					}else if(curr.getStatus() == "TARGET" && this.currLevel < 7){
						valid = false;
						reset();
					}

					if(valid){
						posX -= 1;
					}

				}
			}
		}else if(key == KeyEvent.VK_RIGHT) {
			int index = getIndex(posY,posX+1);

			if(index != -1) {
				Tile curr = tileMatrix.get(index);
				if(curr.getStatus() != "WALL") {
					boolean valid = true;

					if(curr.getStatus() == "TRAP") {
						getToTrap();
					}else if(curr.getStatus() == "COIN") {
						addTime();
						curr.setStatus("PATH");
					}else if(curr.getStatus() == "TARGET" && this.currLevel < 7){
						valid = false;
						reset();
					}

					if(valid){
						posX += 1;
					}

				}
			}

		}else if(key == KeyEvent.VK_SPACE) {
			TimerStart.setPause();
		}

		if(lifePoints == 0) {
			new DialogBox("LOSE");
			resetTotal();
		}

		if(this.currLevel == 7) {
			TimerStart.setPause();
			new DialogBox("WIN");
			resetTotal();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
