package Coords;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import GeoObjects.Point3D;

/**
 * This class consist a tests for the MyCoords class, that implements the coords_converter interface.
 * We made a test for any method that appears in the MyCoords class.
 * 
 * @author Yoav and Elad.
 *
 */

class coords_converterTest {

	private MyCoords coords = new MyCoords();
	private final double aprox = 0.01;

	@Test
	void vector3DTest() {
		Point3D gps0 = new Point3D(32.103315,35.209039,670);
		Point3D gps1 = new Point3D(32.106352,35.205225,650);
		Point3D actual = coords.vector3D(gps0, gps1);

		Point3D expected = new Point3D(337.6989921,-359.2492069,-20);
		assertTrue(actual.close2equals(expected, 0.01));	
	}

	@Test
	void addTest() {
		Point3D gps0 = new Point3D(32.103315,35.209039,670);
		Point3D vector = new Point3D(337.6989921,-359.2492069,-20);
		Point3D actual = coords.add(gps0, vector);

		Point3D expected = new Point3D(32.106352,35.205225,650);
		assertTrue(actual.close2equals(expected, aprox));
	}

	@Test
	void distance3DTest() {
		Point3D gps0 = new Point3D(32.103315,35.209039,670);
		Point3D gps1 = new Point3D(32.106352,35.205225,650);
		double actual = coords.distance3d(gps0, gps1);

		double expected = 493.4578016;
		double dis = Math.abs(expected - actual);

		assertTrue(dis <= aprox);
	}

	@Test
	void azimuth_elevation_dist() {
		Point3D gps0 = new Point3D(32.103315,35.209039,670);
		Point3D gps1 = new Point3D(32.106352,35.205225,650);
		double actual[] = coords.azimuth_elevation_dist(gps0, gps1);
		
		double[] expected = {313.16, -2.322852232927616, 493.0523318324134};
		double dis = Math.abs(actual[0] - expected[0]);
		assertTrue(dis <= aprox*500);
		
		dis = Math.abs(actual[1] - expected[1]);
		assertTrue(dis <= aprox);
		
		dis = Math.abs(actual[2] - expected[2]);
		assertTrue(dis <= aprox);

	}

	private void assertTrue(boolean b) {
		// TODO Auto-generated method stub
		
	}
}