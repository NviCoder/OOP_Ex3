package guiObjects;
import GeoObjects.Point3D;
/**
 * This class is an object that will be presented on the GUI screen.
 * The lines will be, in fact, the paths of the packmans.
 * @author eladn
 *
 */
public class Line {

	Point3D head;
	Point3D tail;

	//////////////////***Constructors****///////////////////////////////////
	
	public Line(Point3D head, Point3D tail) {
		this.head = head;
		this.tail = tail;
	}

   ///////////////*** Getters and Setters**/////////////////////

	public Point3D getHead() {
		return head;
	}


	public Point3D getTail() {
		return tail;
	}
	
	
}
