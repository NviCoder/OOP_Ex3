package GeoObjects;
import Coords.MyCoords;
import guiObjects.Pixel;

/**
 * This class is a super class of Generic Geographic Object.
 * In this project we have:
 * 1.Packman
 * 2.Fruit
 * We wanted to use the inheritance and the packman and fruit have some properties in common.
 * So this class is a simple solution to this situation
 * @author Yoav and Elad.
 *
 */
public class GenericGeoObject {

	private Point3D location;
	public boolean alive;
	private double seconds = 0;
	
	////////////////// ***Constructors****///////////////////////////////////

	public GenericGeoObject (Point3D location) {
		this.location= location;
		this.alive =true;
	}
	
	///////////////*** Getters and Setters**/////////////////////
	
	public double getSeconds() {
		return seconds;
	}

	public void setSeconds(double seconds) {
		this.seconds = seconds;
	}


	public void setLocation(Point3D gps) {
		location = gps;
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
	///////////////*** Methods ***/////////////////////

	/**
	 * This function compute the distance between two GenericGeoObject
	 * 
	 * @param other The other GenericGeoObject to check with.
	 * @return The distance in double type.
	 */
	public double distance(GenericGeoObject other) {
		MyCoords mc = new MyCoords();
		return mc.distance3d(location, other.location);
	}

}
