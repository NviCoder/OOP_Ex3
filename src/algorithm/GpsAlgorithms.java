package algorithm;
import Coords.MyCoords;
import GeoObjects.Fruit;
import GeoObjects.Packman;
import GeoObjects.Point3D;
/**
 * This class compute to the algorithm some Geographic calculations.
 *
 */
public class GpsAlgorithms {
	
	/**calculate the vector between two points
	 * reduce the length of the vector by the packman's radius
	 */
	public static Point3D eatingPoint(Packman packman, Fruit fruit) {
		MyCoords mc = new MyCoords();
		Point3D vector = mc.vector3D(packman.getLocation(), fruit.getLocation());
		if (vector.length() <= packman.radius)
			return packman.getLocation();
		double ratio = (vector.length() - packman.radius) / vector.length();
		Point3D ratioVector = mc.ratioVector(vector, ratio);
		return mc.add(packman.getLocation(), ratioVector);
	}
	/**
	 * Compute the distance between some packman to a fruit that will be eaten by the packman.
	 * @param packman
	 * @param fruit
	 * @return The distance in double type.
	 */
	public static double eatingDistance(Packman packman, Fruit fruit) {
		Point3D eatingPoint = eatingPoint(packman, fruit);
		MyCoords mc = new MyCoords();
		return mc.distance3d(packman.getLocation(), eatingPoint);
	}
	/**
	 * Compute the time that will take to a packman to eat some fruit.
	 * @param packman
	 * @param fruit
	 * @return The time in double type.
	 */
	public static double eatingTime(Packman packman, Fruit fruit) {
		double pathTime = eatingDistance(packman, fruit) / packman.speed;
		return pathTime;
	}
	
}
