package algorithm;

import GeoObjects.Fruit;
import GeoObjects.Packman;
import GeoObjects.Point3D;

public class GpsAlgorithms {
	
	public static Point3D eatingPoint(Packman packman, Fruit fruit) {
		/**calculate the vector between two points
		 * reduce the length of the vector by the packman's radius
		 */
		
	}
	
	public static double eatingDistance(Packman packman, Fruit fruit) {
		Point3D eatingPoint = eatingPoint(packman, fruit);
		return packman.getLocation().distance3D(eatingPoint);
	}
	
	public static double eatingTime(Packman packman, Fruit fruit) {
		double pathTime = eatingDistance(packman, fruit) / packman.speed;
		return pathTime + packman.getSeconds();
	}
	
}
