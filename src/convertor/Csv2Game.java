package convertor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import GeoObjects.Fruit;
import GeoObjects.Packman;
import GeoObjects.Point3D;
import gameObjects.Game;

public class Csv2Game {
	
	private Game game;
	private File file;
	private String csvName;
	private String[] title;
	private int Type, id, Lat, Lon, Alt, speed, radius; ///What about the two numbers in the end ??
	
	public Game convert(String fileName) {
		file = new File(fileName);
		csvName = fileName;
		createGame();
		return game;
	}
	public Game convert(File f) {
		file = f;
		csvName = f.getName();
		createGame();
		return game;
	}

	private void createGame() 
	{
		game =  new Game();
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
			game.packmans.add(new Packman(p,Integer.parseInt(csvRow[speed]),Double.parseDouble(csvRow[radius]),Integer.parseInt(csvRow[id])));
		else
			game.fruits.add(new Fruit(p,Integer.parseInt(csvRow[speed]),Integer.parseInt(csvRow[id])));
	}

}
