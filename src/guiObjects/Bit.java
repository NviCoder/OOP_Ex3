package guiObjects;

public class Bit {
	
	private int x;
	private int y;
	
	public Bit(int x, int y) {
		if (x<0 || y<0)
			throw new RuntimeException("Bit couldn't be negative");
		this.x = x;
		this.y = y;
	}
	
	public int x() {
		return x;
	}
	
	public int y() {
		return y;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}
