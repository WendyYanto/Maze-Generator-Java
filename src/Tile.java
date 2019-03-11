public class Tile {
	
	public Tile(int col, int row, int width) {
		super();
		this.row = row;
		this.col = col;
		this.width = width;
		this.visited = false;	
		this.status = "WALL";
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public boolean visited() {
		return this.visited;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	public void setVisited() {
		this.visited = true;
	}

	private int row;
	private int col;
	private boolean visited;
	private String status;
	private int width;
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Tile() {
		// TODO Auto-generated constructor stub
	}
	
}
