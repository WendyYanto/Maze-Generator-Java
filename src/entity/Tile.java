package entity;

public class Tile {

	private int row;
	private int col;
	private boolean visited;
	private String status;
	private int width;

	public Tile(int col, int row, int width) {
		this.row = row;
		this.col = col;
		this.width = width;
		this.visited = false;	
		this.status = "WALL";
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public boolean isVisited() {
		return this.visited;
	}

	public void setVisited() {
		this.visited = true;
	}

	public int getWidth() {
		return width;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
