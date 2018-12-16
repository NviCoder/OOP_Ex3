package gameObjects;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.AlgorithmParameterGenerator;
import java.util.HashSet;
import java.util.Set;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister.Pack;

import GeoObjects.Fruit;
import GeoObjects.Packman;
import GeoObjects.Point3D;
import algorithm.ShortestPathAlgorithm;


/**
 * A class that includes a collection of fruit and a collection of robots, 
 * the department has the ability to be built
 * From the csv file, see the metadata format, and save its information to such a file.
 */
public class Game {

	public Set <Fruit> fruits = new HashSet<Fruit>();
	public Set <Fruit> fruitsAlive;
	public Set <Packman> packmans = new HashSet<Packman>();
	public boolean calculated = true;
	private double seconds = 0;
	
	public double findShortestPath() {
		ShortestPathAlgorithm algorithm = new ShortestPathAlgorithm(this);
		algorithm.multiPackmans();
		calculated = true;
		seconds = timeForGame();
		return seconds;
	}

	public double timeForGame() {
		double maxTime = -1;
		for (Packman packman: packmans) {
			double currentTime = packman.path.getLast().getSeconds();
			if (currentTime > maxTime)
				maxTime = currentTime;
		}
		return maxTime;
	}
	
	public int getNextPackmanID() {
		int maxID = -1;
		for (Packman packman: packmans)
			if (packman.getID() > maxID)
				maxID = packman.getID();
		return maxID + 1;
	}
	
	public void clear() {
		seconds = 0;
		fruits.clear();
		if (fruitsAlive != null)
			fruitsAlive.clear();
		packmans.clear();
	}
	
	public String timetoString() {
		if (seconds < 60)
			return ("seconds: "+seconds);
		else if (seconds%60<10)
			return ("minutes: "+(int)seconds/60 +":0" + (int)seconds%60);
		else return ("minutes: "+(int)seconds/60 +":" + (int)seconds%60);
	}
	
	public double getSeconds() {
		return seconds;
	}
}

