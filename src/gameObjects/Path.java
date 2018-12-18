package gameObjects;
import java.util.LinkedList;

//import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;

import Coords.MyCoords;
import GeoObjects.Point3D;
/**
 * A class of a path that consisting of a collection of gps points. 
 * Possesses methods of length and additional geographic information.
 * we chose this class to be a extends to linkedlist from the path point type, which we also made.
 * Which allows as to do path that consist a path points.
 */
public class Path extends LinkedList<PathPoint> {
	private MyCoords mc = new MyCoords();
	
	public double length() { /// The length of the path.
		double length = 0;
		if (isEmpty())
			return 0;
		java.util.ListIterator<PathPoint> it = listIterator();
		PathPoint current = it.next();
		while (it.hasNext()) {
			PathPoint next = it.next();
			length += mc.distance3d(current.getLocation(), next.getLocation());
			current = next;
		}
		return length;
	}
	
}
