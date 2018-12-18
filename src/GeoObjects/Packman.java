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

	public int weight, id; 
	public double speed,radius;
	public Path path = new Path(); //Each path to each packman 
	public Point3D startLocation;

//////////////////// ***Constructor****///////////////////////////////////

	public Packman(Point3D _startLocation,double speed, double radius,int id) 
	{
		super(_startLocation);
		startLocation = _startLocation;
		this.speed = speed;
		this.radius= radius;
		this.id = id;
		this.weight=0;
		path.add(new PathPoint(startLocation));
		
	}

	///////////////*** Getters and Setters**/////////////////////
	
	public Point3D getStartLocation() {
		return startLocation;
	}

	public double getSpeed() {
		return speed;
	}

	public double getradius() {
		return radius;
	}

	public int getScore() {
		return weight;
	}
	
	public void setScore(int score) {
		this.weight = score;
	}
	public int getID() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Packman [score=" + weight + ", speed=" + speed + ", id=" + id + ", radius=" + radius + ", "
				+ super.toString() + "]";
	}
	///////////////*** Methods ***/////////////////////

	/**
	 * This function reset the values ​​in fields.
	 * Main use for start a new game.
	 */
	public void reset() {
		setLocation(startLocation);
		path.clear();
		path.add(new PathPoint(startLocation));
		weight=0;
		setSeconds(0);
	}
	/**
	 * This function compute the distance between the fruit to a packman
	 * @param other the fruit.
	 * @return The distance.
	 */
	public double eatDistance(Fruit other) {
		return Math.max(0, distance(other) - radius);
	}
	/**
	 * This functions returns the location at specific time.
	 * @param time The time we want to check
	 * @return the location
	 */
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
				double ratio = (time - lastPoint.getSeconds()) / 
						(nextPoint.getSeconds() - lastPoint.getSeconds());
				return new MyCoords().midPoint(lastPoint.getLocation(), nextPoint.getLocation(), ratio);
			}
		}
		return path.getLast().getLocation();
	}

	
}
