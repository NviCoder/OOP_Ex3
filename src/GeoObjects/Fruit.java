package GeoObjects;

/**
 * Class that represents a "target" in a known geographic location (without movement).
 * The Packman eats those fruits on the Game map.
 * @author Yoav and Elad.
 *
 */
public class Fruit extends GenericGeoObject {
	private int id;
	private int weight;
	private int randImage; //what kind of fruit image the object will get.
	
////////////////// ***Constructors****///////////////////////////////////

	public Fruit(Point3D location, int weight,int id) 
	{
		super(location);
		this.weight = weight;
		this.id = id;
		randImage = (int)(Math.random()*6);
	}

///////////////*** Getters and Setters**/////////////////////

	public int getRandImage() {
		return randImage;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Fruit [weight=" + weight + "id=" + id + "," + super.toString() + "]";
	}

	
	
	
}

