package gameObjects;

import GeoObjects.Point3D;

public class PathPoint {

	private Point3D location;
	private double seconds = 0;
	private int weight = 0;
	
	public PathPoint(Point3D _location, double _seconds, int _weight) {
		location = _location;
		seconds = _seconds;
		weight = _weight;
	}
	
	public PathPoint(Point3D _location) {
		this(_location, 0, 0);
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

}