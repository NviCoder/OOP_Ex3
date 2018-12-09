package guiObjects;

public class Pixel {
	
	private int x;
	private int y;
	
	public Pixel(int x, int y) {
		if (x<0 || y<0)
			throw new RuntimeException("Bit couldn't be negative");
		this.x = x;
		this.y = y;
	}
	
	public void setProportion(double ratioX, double ratioY) {
		x = (int)(x*ratioX);
		y = (int)(y*ratioY);
	}
	
	public void removeProportion(double ratioX, double ratioY) {
		x = (int)(x/ratioX);
		y = (int)(y/ratioY);
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
