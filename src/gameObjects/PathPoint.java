package gameObjects;
import GeoObjects.Point3D;

/**
 * This class is a single point of list of points that make up a path to packman.
 * The point in fact is a fruit on the map that the packman will eat. 
 */
public class PathPoint {

	private Point3D location;
	private double seconds = 0;
	private int weight = 0;
	int fruitEating = -1;
	
	////////////////// ***Constructors****///////////////////////////////////
	
	public PathPoint(Point3D _location, double _seconds, int _weight) {
		location = _location;
		seconds = _seconds;
		weight = _weight;
	}
	
	public PathPoint(Point3D _location) {
		this(_location, 0, 0);
	}
	
	///////////////*** Getters and Setters**/////////////////////
	
	public void setFruitEating(int id) {
		fruitEating = id;
	}
	
	public double getSeconds() {
		return seconds;
	}
	
	public Point3D getLocation() {
		return location;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public String toString() {
		return "location:" + location.toString() + ", seconds:" + seconds + ", fruitID:" + fruitEating;
	}

}
