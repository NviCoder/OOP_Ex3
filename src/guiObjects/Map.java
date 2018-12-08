package guiObjects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import GeoObjects.Point3D;

public class Map {

	public BufferedImage myImage;

	public final Point3D nw;
	public final Point3D ne;
	public final Point3D sw;
	public final Point3D se;

	public Map(String imagePath) { 
		try {
			myImage = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		nw = new Point3D(35.202377777777784, 32.10575, 0);
		ne = new Point3D(35.212561111111114, 32.10575, 0);
		sw = new Point3D(35.20266388888889, 32.1028, 0);
		se = new Point3D(35.21176944444445, 32.1028, 0);
	}
	
	public int height() {
		return myImage.getHeight();
	}
	
	public int widht() {
		return myImage.getWidth();
	}
	
	public Bit gps2Bit(Point3D point) { //very bad function!!
		double heightGPS = nw.y() - sw.y();
		double disY = nw.y() - point.y();
		double fractionNorth = disY / heightGPS; 
		double y = fractionNorth * height();
		
		double eastLen = se.x() - ne.x();
		double westLen = sw.x() - nw.x();
		
		double east = ne.x()+ eastLen*fractionNorth;
		double west = nw.x()+ westLen*fractionNorth;
		double fractionEast = (point.x()-east)/(west - east);
		double x = fractionEast * widht();

		return new Bit((int)x,(int)y);
	}
	
	public static void main(String[] args) {
		Map m = new Map("E:\\yoav\\מדעי המחשב\\סמסטר א\\מונחה עצמים\\מטלה3\\Ex3 (2)\\Ex3\\Ariel1.png");
		System.out.println("height:" + m.height() + " widht:" + m.widht());
		Bit b = m.gps2Bit(new Point3D(35.21, 32.104));
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
