package guiObjects;
/**
 * This class is Represents a pixel that on the screen.
 * @author Yoav and Elad.
 *
 */
public class Pixel {
	
	private int x;
	private int y;

	////////////////////***Constructor****///////////////////////////////////

	public Pixel(int x, int y) {
		if (x<0 || y<0) {
			x = Math.max(0, x);
			y = Math.max(0, y);
		}
		this.x = x;
		this.y = y;
	}

	///////////////*** Getters and Setters**/////////////////////

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
