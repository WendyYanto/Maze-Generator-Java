package ui;

import contract.GameContract;
import contract.MainContract;
import entity.Tile;
import timer.GameTimer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener, GameContract {

    private int rows;
    private int cols;
    private int lifePoints = 3;
    private int currLevel = 1;
    private int currTime = 25;
    private int posX = 1;
    private int posY = 1;
    private ArrayList<Tile> tileMatrix = new ArrayList<>();
    private GameTimer timer;
    private MainContract mainContract;

    public GamePanel(MainContract mainContract) {
        this.mainContract = mainContract;

        generatePacket(21, 21);
        Thread playThread = new Thread(this);
        addKeyListener(this);
        playThread.start();

        setFocusable(true);
        setSize(400, 400);

        timer = new GameTimer(mainContract, this);
        timer.setPause();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        tileMatrix.get(getIndex(this.rows - 2, this.cols - 2)).setStatus("TARGET");
        for (Tile curr : tileMatrix) {
            switch (curr.getStatus()) {
                case "WALL":
                    g2d.setColor(Color.BLACK);
                    break;
                case "PATH":
                case "START":
                    g2d.setColor(Color.WHITE);
                    break;
                case "TARGET":
                    g2d.setColor(Color.decode("#00C3E5"));
                    break;
                case "TRAP":
                    g2d.setColor(Color.decode("#ED020A"));
                    break;
                case "COIN":
                    g2d.setColor(Color.decode("#FBC02D"));
                    break;
            }

            g2d.fillRect(curr.getCol() + (curr.getCol() * curr.getWidth()),
                    curr.getRow() + (curr.getRow() * curr.getWidth()),
                    curr.getWidth(), curr.getWidth());
            g2d.drawRect(curr.getCol() + (curr.getCol() * curr.getWidth()),
                    curr.getRow() + (curr.getRow() * curr.getWidth()),
                    curr.getWidth(), curr.getWidth());
        }

        g2d.setColor(Color.decode("#07F723"));
        g2d.fillRect(posX + (posX * 20), posY + (posY * 20), 20, 20);
        g2d.drawRect(posX + (posX * 20), posY + (posY * 20), 20, 20);
    }

    private void generatePacket(int row, int col) {

        this.rows = row;
        this.cols = col;
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                Tile myTile = new Tile(j, i, 20);
                tileMatrix.add(myTile);
            }
        }

        generateMaze();
        generateTrap();
        generateCoin();
    }

    private void generateTrap() {
        int counter = 0;
        Tile temp;

        do {
            int t = new Random().nextInt(tileMatrix.size());
            temp = tileMatrix.get(t);

            if (temp.getStatus().equals("PATH")) {
                counter++;
                temp.setStatus("TRAP");
            }

        } while (counter != 3);
    }

    private void generateCoin() {
        int counter = 0;
        Tile temp;

        do {
            int t = new Random().nextInt(tileMatrix.size());
            temp = tileMatrix.get(t);

            if (temp.getStatus().equals("PATH")) {
                counter++;
                temp.setStatus("COIN");
            }

        } while (counter != 5);
    }

    private void generateMaze() {
        Stack<Tile> cellStack = new Stack<>();
        Tile curr = tileMatrix.get(getIndex(posX, posY));
        curr.setVisited();
        curr.setStatus("START");
        cellStack.push(curr);

        while (!cellStack.empty()) {

            Tile currTile = cellStack.peek();
            cellStack.pop();
            Tile neighbor = getNeighbors(currTile.getRow(), currTile.getCol());

            while (neighbor != null) {
                neighbor.setStatus("PATH");
                neighbor = getNeighbors(neighbor.getRow(), neighbor.getCol());
                if (neighbor != null) {
                    cellStack.push(neighbor);
                    neighbor.setStatus("PATH");
                    neighbor.setVisited();
                }
            }
        }
    }

    private int getIndex(int i, int j) {
        if (i <= 0 || i >= (this.rows - 1) || j <= 0 || j >= (this.cols - 1)) {
            return -1;
        }
        return i * (this.rows) + j;
    }

    private Tile getNeighbors(int myRow, int myCol) {

        ArrayList<Tile> myNeighbors = new ArrayList<>();
        Tile upNeighbor, downNeighbor, leftNeighbor, rightNeighbor, temp;

        if (getIndex(myRow - 1, myCol) != -1 && getIndex(myRow - 2, myCol) != -1) {
            upNeighbor = tileMatrix.get(getIndex(myRow - 2, myCol));
            if (upNeighbor != null && !upNeighbor.isVisited()) {
                myNeighbors.add(upNeighbor);
            }
        }

        if (getIndex(myRow + 1, myCol) != -1 && getIndex(myRow + 2, myCol) != -1) {
            downNeighbor = tileMatrix.get(getIndex(myRow + 2, myCol));
            if (downNeighbor != null && !downNeighbor.isVisited()) {
                myNeighbors.add(downNeighbor);
            }
        }

        if (getIndex(myRow, myCol - 1) != -1 && getIndex(myRow, myCol - 2) != -1) {
            leftNeighbor = tileMatrix.get(getIndex(myRow, myCol - 2));
            if (leftNeighbor != null && !leftNeighbor.isVisited()) {
                myNeighbors.add(leftNeighbor);
            }

        }

        if (getIndex(myRow, myCol + 1) != -1 && getIndex(myRow, myCol + 2) != -1) {
            rightNeighbor = tileMatrix.get(getIndex(myRow, myCol + 2));
            if (rightNeighbor != null && !rightNeighbor.isVisited()) {
                myNeighbors.add(rightNeighbor);
            }
        }

        if (myNeighbors.isEmpty()) {
            return null;
        }

        int r = new Random().nextInt(myNeighbors.size());
        temp = myNeighbors.get(r);

        if (myRow - temp.getRow() == 0 && temp.getCol() - myCol == 2) {
            Tile mid = tileMatrix.get(getIndex(myRow, myCol + 1));
            mid.setStatus("PATH");
        } else if (myRow - temp.getRow() == 0 && temp.getCol() - myCol == -2) {
            Tile mid = tileMatrix.get(getIndex(myRow, myCol - 1));
            mid.setStatus("PATH");
        } else if (myCol - temp.getCol() == 0 && temp.getRow() - myRow == 2) {
            Tile mid = tileMatrix.get(getIndex(myRow + 1, myCol));
            mid.setStatus("PATH");
        } else if (myCol - temp.getCol() == 0 && temp.getRow() - myRow == -2) {
            Tile mid = tileMatrix.get(getIndex(myRow - 1, myCol));
            mid.setStatus("PATH");
        }

        myNeighbors.clear();
        return temp;
    }

    private void reset() {
        tileMatrix.clear();
        timer.setPause();
        this.removeAll();
        posX = 1;
        posY = 1;
        this.lifePoints = 3;
        this.currLevel += 1;
        currTime = currTime - 3;
        generatePacket(this.rows, this.cols);
        this.validate();
        mainContract.setTotalLife(this.lifePoints);
        mainContract.setLevel(this.currLevel);
    }

    private void resetTotal() {
        tileMatrix.clear();
        timer.setPause();
        this.removeAll();
        posX = 1;
        posY = 1;
        this.lifePoints = 3;
        this.currLevel = 1;
        currTime = 25;
        generatePacket(this.rows, this.cols);
        this.validate();
        mainContract.setTotalLife(this.lifePoints);
        mainContract.setLevel(this.currLevel);
        mainContract.setTime(this.lifePoints);
    }

    private void getToTrap() {
        timer.setPause();
        new DialogBox("LOSE_LIFE");
        this.lifePoints--;
        mainContract.setTotalLife(this.lifePoints);
    }

    private void addTime() {
        timer.setPause();
        currTime += 5;
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            if (currTime == 0) {
                new DialogBox("LOSE");
                resetTotal();
            }
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                // Do Nothing
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (timer.getPauseStatus()) {
            timer.setResume();
        }

        if (key == KeyEvent.VK_UP && isValid(getIndex(posY - 1, posX))) {
            posY -= 1;
        } else if (key == KeyEvent.VK_DOWN && isValid(getIndex(posY + 1, posX))) {
            posY += 1;
        } else if (key == KeyEvent.VK_LEFT && isValid(getIndex(posY, posX - 1))) {
            posX -= 1;
        } else if (key == KeyEvent.VK_RIGHT && isValid(getIndex(posY, posX + 1))) {
            posX += 1;
        } else if (key == KeyEvent.VK_SPACE) {
            timer.setPause();
        }

        if (lifePoints == 0) {
            new DialogBox("LOSE");
            resetTotal();
        }

        if (this.currLevel == 7) {
            timer.setPause();
            new DialogBox("WIN");
            resetTotal();
        }
    }

    private boolean isValid(int index) {
        if (index != -1) {
            Tile curr = tileMatrix.get(index);
            if (!curr.getStatus().equals("WALL")) {
                boolean valid = true;
                if (curr.getStatus().equals("TRAP")) {
                    getToTrap();
                } else if (curr.getStatus().equals("COIN")) {
                    addTime();
                    curr.setStatus("PATH");
                } else if (curr.getStatus().equals("TARGET") && this.currLevel < 7) {
                    valid = false;
                    reset();
                }
                return valid;
            }
        }
        return false;
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // Do Nothing
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // Do Nothing
    }

    @Override
    public int getTime() {
        return currTime;
    }

    @Override
    public void setTime(int time) {
        this.currTime = time;
    }
}
