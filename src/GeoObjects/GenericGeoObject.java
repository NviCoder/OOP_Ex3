package GeoObjects;

import Coords.MyCoords;
import guiObjects.Bit;

public class GenericGeoObject {

	private Point3D location;
	public boolean alive;
	private double seconds = 0;
	
	public GenericGeoObject (Point3D location) {
		this.location= location;
		this.alive =true;
	}
	
	public double getSeconds() {
		return seconds;
	}

	public void setSeconds(double seconds) {
		this.seconds = seconds;
	}


	public void setLocation(Point3D gps) {
		location = new Point3D(gps);
	}
	
	public boolean isAlive() {
		return alive;
	}

	public Point3D getLocation() {
		return location;
	}

	@Override
	public String toString() {
		return " [location=" + location + ", alive=" + alive + "]";
	}
	
	public double distance(GenericGeoObject other) {
		return location.distance3D(other.location);
	}

}
