package GeoObjects;
/**
 * Class that represents a "target" in a known geographic location (without movement).
 * 
 * @author Yoav and Elad.
 *
 */
public class Fruit extends genericGeoObject {
	public double weight,id;
	
	public Fruit(Point3D location, double weight,double id) 
	{
		super(location);
		this.weight =weight;
		this.id = id;
		
	}

	public double getWeight() {
		return weight;
	}

	public double getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Fruit [weight=" + weight + ", id=" + id + "," + super.toString() + "]";
	}

	
	
	
}
