package GeoObjects;
import java.util.Date;
import java.util.Iterator;

import Coords.MyCoords;
import gameObjects.Path;
import gameObjects.PathPoint;


/**
 * A class that represents a "robot" with a location, 
 * orientation and ability to move (at a defined speed).
 * @author Yoav and elad.
 *
 */
public class Packman extends GenericGeoObject 
{

	//	public Path path; /// After having the path class.

	public int score;
	public double speed,id,radius;
	public Path path = new Path();

	public Packman(Point3D location,double speed, double radius,double id) 
	{
		super(location);
		this.speed = speed;
		this.radius= radius;
		this.id = id;
		path.add(new PathPoint(location));

	}
	
	public double getSpeed() {
		return speed;
	}

	public double getradius() {
		return radius;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Packman [score=" + score + ", speed=" + speed + ", id=" + id + ", radius=" + radius + ", "
				+ super.toString() + "]";
	}

	public double eatDistance(Fruit other) {
		return Math.max(0, distance(other) - radius);
	}
	
	public Point3D locationAtTime(double time) {
		if (path.size() == 1)
			return path.getFirst().getLocation();
		
		PathPoint lastPoint = null;
		PathPoint nextPoint = null;
		Iterator<PathPoint> pointIterator = path.iterator();
		while (pointIterator.hasNext()) {
			nextPoint = pointIterator.next();
			if (nextPoint.getSeconds() == time)
				return nextPoint.getLocation();
			else if (nextPoint.getSeconds() < time)
				lastPoint = nextPoint;
			else {
				double ratio = (time - lastPoint.getSeconds()) / (nextPoint.getSeconds() - lastPoint.getSeconds());
				return new MyCoords().midPoint(lastPoint.getLocation(), nextPoint.getLocation(), ratio);
			}
		}
		return path.getLast().getLocation();
	}

}
