package Coords;
import java.util.Arrays;

import guiObjects.Bit;
import GeoObjects.Point3D;


/**
 * This class implements coords_converter , Which help in building the geographical
 *  infrastructure of points in space, finding distances computation degrees of the points on the earth 
 *  and so on...
 * 
 * @author Yoav & elad
 *
 */
public class MyCoords implements coords_converter {

	private final double EarthRadius = 6371000;

	@Override
	public Point3D add(Point3D gps, Point3D local_vector_in_meter) {
		double diffLatRadian = Math.asin(local_vector_in_meter.x()/EarthRadius);
		double Lat = gps.x() + diffLatRadian*180 / Math.PI;

		double lonNormal = Math.cos(gps.x()*Math.PI/180);
		double diffLonRadian = Math.asin(local_vector_in_meter.y()/(EarthRadius*lonNormal));
		double Lon = gps.y() + diffLonRadian*180 / Math.PI;

		double Alt = gps.z() + local_vector_in_meter.z();

		return new Point3D(Lat, Lon, Alt);
	}

	@Override
	public double distance3d(Point3D gps0, Point3D gps1) {
		Point3D vector = vector3D(gps0, gps1);
		return vector.distance3D(0, 0, 0);	
	}
	
	public double distance2D(Point3D gps0, Point3D gps1) {
		Point3D vector = vector3D(gps0, gps1);
		return vector.distance2D(new Point3D(0,0,0));	
	}

	@Override
	public Point3D vector3D(Point3D gps0, Point3D gps1) {
		double diffLat = gps1.x()-gps0.x();
		double diffLon = gps1.y()-gps0.y();
		double diffAlt = gps1.z()-gps0.z();
		double lonNormal = Math.cos(gps0.x()*Math.PI/180);

		double diffLatRadian = diffLat*Math.PI/180;
		double diffLonRadian = diffLon*Math.PI/180;
		double diffLatMeters = Math.sin(diffLatRadian)*EarthRadius;
		double diffLonMeters = Math.sin(diffLonRadian)*EarthRadius*lonNormal;

		return new Point3D(diffLatMeters, diffLonMeters, diffAlt);
	}

	@Override
	public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) 
	{	
		 
		double azimuth;
		
		double lat0Radian = Math.toRadians(gps0.x()); //teta1
		double lat1Radian = Math.toRadians(gps1.x()); //teta2
//		double diffLat = gps1.x()-gps0.x();
		double diffLon = gps1.y()-gps0.y();
//		double diffLatRadian = Math.toRadians(diffLat);
		double diffLonRadian = Math.toRadians(diffLon);  //delta2
		
		double numerator = Math.sin(diffLonRadian) * Math.cos(lat1Radian);
		double denominator = Math.cos(lat0Radian)*Math.sin(lat1Radian) - Math.sin(lat0Radian)*Math.cos(lat1Radian)*Math.cos(diffLonRadian);
		azimuth = (Math.toDegrees(Math.atan2(numerator,denominator))+360) % 360;
		
		double diffAlt = gps1.z()-gps0.z();
		
		double distance3D = this.distance3d(gps0, gps1);
		double elevation = Math.toDegrees(Math.asin(diffAlt/distance3D));
		
		double distance2D = this.distance2D(gps0, gps1);
		return new double[]{azimuth, elevation, distance2D}; 
	}

	@Override
	public boolean isValid_GPS_Point(Point3D p) {
		return (p.x()<=90 && p.x()>=-90 &&
				p.y()<=1800 && p.y()>=-1800 &&
				p.z()>=-450);
	}
	
	/**
	 * 
	 * @param other
	 * @param ratio 0<= ratio <= 1
	 * @return point on the vector between this point to other, such that the vector split by the ratio   
	 */
	public Point3D midPoint(Point3D gps0, Point3D gps1, double ratio) {
		//TODO
	}
	
	public Bit gps2Bit (Point3D gps, int height, int widht) {
		//TODO
		
	}

	public Point3D Bit2gps (Bit bit, int height, int widht) {
		//TODO
	}

}
