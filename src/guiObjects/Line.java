package guiObjects;

import GeoObjects.Point3D;

public class Line {

	Point3D head;
	Point3D tail;
	
	public Line(Point3D head, Point3D tail) {
		this.head = head;
		this.tail = tail;
	}

	public Point3D getHead() {
		return head;
	}


	public Point3D getTail() {
		return tail;
	}
	
	
}
