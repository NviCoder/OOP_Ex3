package guiObjects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Coords.MyCoords;
import GeoObjects.Point3D;

public class Map {

	public BufferedImage myImage;

	public final Point3D nw;
	public final Point3D ne;
	public final Point3D sw;
	public final Point3D se;
	MyCoords mc = new MyCoords();

	public Map(String imagePath) { 
		try {
			myImage = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		nw = new Point3D(32.10575, 35.202377777777784, 0);
		ne = new Point3D(32.10575, 35.212561111111114, 0);
		sw = new Point3D(32.10280, 35.20266388888889, 0);
		se = new Point3D(32.10280, 35.21176944444445, 0);
	}
	
	public int height() {
		return myImage.getHeight();
	}
	
	public int widht() {
		return myImage.getWidth();
	}
	
	public Pixel gps2pixel(Point3D point) { //very bad function!!
		double imageLatD = nw.x() - se.x();
		double currentLatD = nw.x() - point.x();
		double fractionNorth = currentLatD / imageLatD; 
		double latpixel = fractionNorth * height();
		
		Point3D leftMergin = mc.midPoint(nw, sw, fractionNorth);
		Point3D rightMergin = mc.midPoint(ne, se, fractionNorth);
		
		double currentImageLonD = rightMergin.y() - leftMergin.y();
		double currentLonD = point.y() - leftMergin.y();
		double fractionWest = currentLonD / currentImageLonD;
		double lonpixel = fractionWest * widht();

		return new Pixel((int)lonpixel, (int)latpixel);
	}

	public Point3D pixel2gps (Pixel pixel) {
		//TODO
		double ratioH = (double)pixel.y() / height();
		double ratioW = (double)pixel.x() / widht();
		double imageLatD = nw.x() - se.x();
		double lat = nw.x() - ratioH * imageLatD;
		
		Point3D leftMergin = mc.midPoint(nw, sw, ratioH);
		Point3D rightMergin = mc.midPoint(ne, se, ratioH);
		
		double imageLonD = rightMergin.y() - leftMergin.y();
		double lon = leftMergin.y() + ratioW * imageLonD;
		
		return new Point3D(lat, lon, 0);
	}

	
	public static void main(String[] args) {
		Map m = new Map("E:\\yoav\\���� �����\\����� �\\����� �����\\����3\\Ex3 (2)\\Ex3\\Ariel1.png");
		System.out.println("height:" + m.height() + " widht:" + m.widht());
		Pixel b = m.gps2pixel(new Point3D(32.10300, 35.205));
		System.out.println(b);
		for (int i=0; i<5; i++)
			for (int j=0; j<5; j++)
				m.myImage.setRGB(b.x()+i, b.y()+j, 0);
		File outputfile = new File("image.jpg");
		try {
			ImageIO.write(m.myImage, "jpg", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}