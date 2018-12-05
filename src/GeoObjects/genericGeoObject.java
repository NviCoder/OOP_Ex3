package GeoObjects;


public class genericGeoObject {
	
	private Point3D location;
	public boolean alive;
	int seconds = 0;
	
	public genericGeoObject (Point3D location) {
		this.location= location;
		this.alive =true;
	}

	public boolean isAlive() {
		return alive;
	}

	public Point3D getLocation() {
		return location;
	}

	@Override
	public String toString() {
		return " [location=" + location + ", alive=" + alive + "]";
	}
		
	}
