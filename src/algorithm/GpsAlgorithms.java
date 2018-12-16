package algorithm;

import Coords.MyCoords;
import GeoObjects.Fruit;
import GeoObjects.Packman;
import GeoObjects.Point3D;

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
	
	public static double eatingDistance(Packman packman, Fruit fruit) {
		Point3D eatingPoint = eatingPoint(packman, fruit);
		MyCoords mc = new MyCoords();
		return mc.distance3d(packman.getLocation(), eatingPoint);
	}
	
	public static double eatingTime(Packman packman, Fruit fruit) {
		double pathTime = eatingDistance(packman, fruit) / packman.speed;
		return pathTime;
	}
	
}
