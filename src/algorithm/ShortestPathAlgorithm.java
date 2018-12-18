
package algorithm;
import java.util.ArrayList;
import java.util.HashSet;

import GeoObjects.Fruit;
import GeoObjects.Packman;
import GeoObjects.Point3D;
import gameObjects.Game;
import gameObjects.Path;
import gameObjects.PathComperator;
import gameObjects.PathPoint;

/**
 * This is a class that gets a game, which is a collection of (Packmans and Fruits).
 *  And calculates the optimal path so that all the fruits will be "eaten" as quickly as possible. 
 *  This is an algorithmic class and includes calculating "fruit paths" for each of the packmans.
 *  We created Auxiliary class called GpsAlgorithms, their we made Geophysical calculations. 
 * 
 * @author Yoav and Elad.
 */
public class ShortestPathAlgorithm {
	private Game game;

	////////////////////***Constructors****///////////////////////////////////

	public ShortestPathAlgorithm(Game _game) {
		game = _game;
	}
	
	///////////////////////////////////////////////////////////////////////////
	////////////////////////////       Methods        /////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * This function find the closest fruit to one packman
	 * @param packman The packman we check.
	 * @return The closest fruit.
	 */
	private Fruit findCloseFruit(Packman packman) {
		double minPath = Double.MAX_VALUE;
		Fruit closest = null;
		for (Fruit fruit : game.fruitsAlive) {
			if (packman.eatDistance(fruit) < minPath) {
				minPath  = packman.eatDistance(fruit);
				closest = fruit;
			}
		}
		return closest;
	}
/**
 * This is the Algorithm!
 * We divided it into two situations:
 * 1. Only one packman.
 * 2. Multi Packmans.
 * The algorithm checks for all the fruits Who is the one of all the 
 * packmans that in the game, will reach to him first.
 * (We consider speed and the time given for each Packman means that one Packman can be closer,
 *  But in terms of time will take him more than one farther)
 * when he find the one, the algorithm add the location of the fruit to the path of this packman,
 * and remove this fruit from the "Alive" list of the fruits.
 * The algorithm runs until the list of the "Alive fruits"
 * is Empty.
 * 
 */
	public void multiPackmans() {	
		for (Packman packman: game.packmans) //reset paths to the packmans.
			packman.reset();
		
		Packman nextPackman = null;
		Fruit nextFruit = null;
		game.fruitsAlive = new HashSet<Fruit>(game.fruits);
		double minTime;
		int weight=0, id=-1;
		while (!game.fruitsAlive.isEmpty()) {
			minTime = Double.MAX_VALUE;
			for (Packman packman: game.packmans) {			
				Fruit closestFruit = findCloseFruit(packman);
				double pathTime = GpsAlgorithms.eatingTime(packman, closestFruit); 
				if (packman.getSeconds() + pathTime < minTime) {
					nextPackman = packman;
					nextFruit = closestFruit;
					minTime = packman.getSeconds() + pathTime;
					weight = closestFruit.getWeight();
					id = closestFruit.getId();
				}
			}
			Point3D eatingPoint = GpsAlgorithms.eatingPoint(nextPackman, nextFruit);
			PathPoint nextPoint = new PathPoint(eatingPoint, minTime, weight);
			nextPoint.setFruitEating(id);
			nextPackman.path.add(nextPoint);
			nextPackman.setLocation(eatingPoint);
			nextPackman.setSeconds(minTime);
			nextPackman.setScore(nextPackman.getScore() + weight);
			game.fruitsAlive.remove(nextFruit);
		}
		for (Packman packman: game.packmans)
			packman.setLocation(packman.startLocation);
		game.sort();
	}
/**
 * The same Idea for One packman.
 * Runs until we do not have more fruits to eat.
 * @param packman
 */
	public void onePackman(Packman packman) {
		game.fruitsAlive = new HashSet<Fruit>(game.fruits);
		while (!game.fruitsAlive.isEmpty()) {
			Fruit closest = findCloseFruit(packman);
			PathPoint nextPoint = new PathPoint(GpsAlgorithms.eatingPoint(packman, closest),
					GpsAlgorithms.eatingTime(packman, closest), closest.getWeight());
			packman.path.add(nextPoint);
			game.fruitsAlive.remove(closest);
		}
	}

}
