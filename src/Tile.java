class Tile {

	private int row;
	private int col;
	private boolean visited;
	private String status;
	private int width;

	Tile(int col, int row, int width) {
		this.row = row;
		this.col = col;
		this.width = width;
		this.visited = false;	
		this.status = "WALL";
	}

	int getRow() {
		return row;
	}

	int getCol() {
		return col;
	}

	boolean isVisited() {
		return this.visited;
	}

	void setVisited() {
		this.visited = true;
	}

	int getWidth() {
		return width;
	}

	String getStatus() {
		return status;
	}

	void setStatus(String status) {
		this.status = status;
	}

}
