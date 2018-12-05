package gameObjects;

import GeoObjects.Point3D;

public class PathPoint {

	private Point3D location;
	private double seconds = 0;
	
	public PathPoint(Point3D _location, double _seconds) {
		location = _location;
		seconds = _seconds;
	}
	
	public PathPoint(Point3D _location) {
		this(_location, 0);
	}
	
	public double getSeconds() {
		return seconds;
	}
	
	public Point3D getLocation() {
		return location;
	}

}
