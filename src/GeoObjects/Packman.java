package GeoObjects;
import java.util.Date;


/**
 * A class that represents a "robot" with a location, 
 * orientation and ability to move (at a defined speed).
 * @author Yoav and elad.
 *
 */
public class Packman extends genericGeoObject 
{

	//	public Path path; /// After having the path class.

	public int score;
	public double speed,id,radius;


	public Packman(Point3D location,double speed, double radius,double id) 
	{
		super(location);
		this.speed = speed;
		this.radius= radius;
		this.id = id;

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

	
	



}
