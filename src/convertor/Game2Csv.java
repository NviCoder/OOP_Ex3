package convertor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import GeoObjects.Fruit;
import GeoObjects.Packman;
import GeoObjects.Point3D;
import gameObjects.Game;
/**
 * This class knows to make a csv file to a custom game, that the user decided.
 * 
 * The format is a field that spread by ",".
 *
 */
public class Game2Csv {

	private Game game;
	private String path;
	private String fileName;
	StringBuilder csv;
	
	public void export(Game game, String path, String fileName) {
		this.game = game;
		this.path = path;
		this.fileName = fileName;
		
		create();
		write();
	}
	
	private void create() {
		csv = new StringBuilder();
		//Titles of the file.
		csv.append("Type,id,Lat,Lon,Alt,Speed/Weight,Radius,"+game.packmans.size()+","+game.fruits.size());
		
		for (Packman packman: game.packmans)
			appendRow(true, packman.getID(), packman.startLocation, packman.getSpeed(), packman.getradius());
		for (Fruit fruit: game.fruits)
			appendRow(false, fruit.getId(), fruit.getLocation(), fruit.getWeight(), -1);
	}
	
	private void appendRow(boolean isPackman, int id, Point3D point, double speed_weight, double radius) {
		if (isPackman)
			csv.append("\nP,");
		else
			csv.append("\nF,");
		csv.append(id+","+ point.x()+","+ point.y()+","+ point.z()+","+ speed_weight+",");
		if (isPackman)
			csv.append(radius+",,");
		else
			csv.append(",,,");
	}
	
	private void write() {
		PrintWriter pw = null;
		
		try 
		{
			pw = new PrintWriter(new File(path + "\\" + fileName + ".csv"));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return;
		}
	
		pw.write(csv.toString());
		pw.close();
		System.out.println("done!"); // printing in the console a "done" message.
	}
	
}
