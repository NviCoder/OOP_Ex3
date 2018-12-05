package gameObjects;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import GeoObjects.Fruit;
import GeoObjects.Packman;
import GeoObjects.Point3D;


/**
 * A class that includes a collection of fruit and a collection of robots, 
 * the department has the ability to be built
 * From the csv file, see the metadata format, and save its information to such a file.
 */
public class Game {

	private File file;
	public Game afterPlayed; //this is recursive
	private String csvName;
	private String[] title;
	private int Type, id, Lat, Lon, Alt, speed, radius; ///What about the two numbers in the end ??
	
	public Set <Fruit> fruits = new HashSet<Fruit>();
	public Set <Fruit> fruitsAlive;
	public Set <Packman> packmans = new HashSet<Packman>();

	public void convert(String fileName) {
		file = new File(fileName);
		csvName = fileName;
		createGame();
	}
	public void convert(File f) {
		csvName = f.getName();
		createGame();
	}

	private void createGame() 
	{
		try (BufferedReader br = new BufferedReader(new FileReader(file))) 
		{
			String[] csvRow; //one row from the csv file, separated
			String line = ""; //one row from the csv file				
			line = br.readLine();
			title = line.split(",");

			for (int i=0; i<title.length; i++)
			{
				switch (title[i])
				{
				case "Type":
					Type = i;
					break;

				case "id":
					id = i;
					break;

				case "Lat":
					Lat = i;
					break;

				case "Lon":
					Lon = i;
					break;
				case "Alt":
					Alt = i;
					break;

				case "Speed/Weight":
					speed = i;
					break;

				case "Radius":
					radius = i;
					break;

				default:
					break;
				}
			}

			while ((line = br.readLine()) != null) //add rows
			{
				csvRow = line.split(",");
				addData(csvRow); ///add to the sets
			}

		} catch (IOException e) 

		{
			e.printStackTrace();
		}	

	}
	
	private void addData(String[] csvRow)
	{
		Point3D p = new Point3D(Double.parseDouble(csvRow[Lat]),Double.parseDouble(csvRow[Lon]),Double.parseDouble(csvRow[Alt]));
		if (csvRow[Type].equals("P"))
			packmans.add(new Packman(p,Double.parseDouble(csvRow[speed]),Double.parseDouble(csvRow[radius]),Double.parseDouble(csvRow[id])));
		else
			fruits.add(new Fruit(p,Double.parseDouble(csvRow[speed]),Double.parseDouble(csvRow[id])));
	}
	
	public static void main(String[] args) {
		String s = "D:\\aeladAriel\\secondYear\\Oop\\Ex3\\data\\game_1543685769754.csv";
		Game first = new Game();
		first.convert(s);
		System.out.println(first.packmans);
		System.out.println(first.fruits);
//		System.out.println();
//		System.out.println(first.packmans.toString());
	}
	


}

