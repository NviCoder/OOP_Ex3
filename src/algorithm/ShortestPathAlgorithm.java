

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

public class ShortestPathAlgorithm {

	private Game game;

	public ShortestPathAlgorithm(Game _game) {
		game = _game;
	}

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

	public void multiPackmans() {	
		for (Packman packman: game.packmans) //reset pathes
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

	public void onePackman(Packman packman) {
		game.fruitsAlive = new HashSet<Fruit>(game.fruits);
		while (!game.fruitsAlive.isEmpty()) {
			Fruit closest = findCloseFruit(packman);
			PathPoint nextPoint = new PathPoint(GpsAlgorithms.eatingPoint(packman, closest), GpsAlgorithms.eatingTime(packman, closest), closest.getWeight());
			packman.path.add(nextPoint);
			game.fruitsAlive.remove(closest);
		}
	}

}
