package gameObjects;

import java.util.LinkedList;

import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;

import Coords.MyCoords;
import GeoObjects.Point3D;

public class Path extends LinkedList<PathPoint> {
	private MyCoords mc = new MyCoords();
	
	public double length() {
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
	
	public double seconds() {
		return getLast().getSeconds();
	}
	
}
