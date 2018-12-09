package GeoObjects;
/**
 * Class that represents a "target" in a known geographic location (without movement).
 * 
 * @author Yoav and Elad.
 *
 */
public class Fruit extends GenericGeoObject {
	public double id;
	
	public Fruit(Point3D location, double weight,double id) 
	{
		super(location);

		this.id = id;
		
	}

	public double getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Fruit [id=" + id + "," + super.toString() + "]";
	}

	
	
	
}
