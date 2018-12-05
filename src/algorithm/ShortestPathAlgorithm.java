package algorithm;

import java.util.ArrayList;

import GeoObjects.Fruit;
import GeoObjects.Packman;
import GeoObjects.Point3D;
import gameObjects.Game;
import gameObjects.Path;
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

	public void onePackman(Packman packman) {
		while (!game.fruitsAlive.isEmpty()) {
			Fruit closest = findCloseFruit(packman);
			PathPoint nextPoint = new PathPoint(GpsAlgorithms.eatingPoint(packman, closest), GpsAlgorithms.eatingTime(packman, closest));
			packman.path.add(nextPoint); //must change it
			game.fruitsAlive.remove(closest);
		}
	}

	public void multiPackmans() {
		Packman nextPackman = null;
		Fruit nextFruit = null;
		double minTime;
		while (!game.fruitsAlive.isEmpty()) {
			minTime = Double.MAX_VALUE;
			for (Packman packman: game.packmans) {
				Fruit closestFruit = findCloseFruit(packman);
				double pathTime = GpsAlgorithms.eatingTime(packman, closestFruit); 
				if (packman.getSeconds() + pathTime < minTime) {
					nextPackman = packman;
					nextFruit = closestFruit;
					minTime = packman.getSeconds() + pathTime;
				}
			}
			Point3D eatingPoint = GpsAlgorithms.eatingPoint(nextPackman, nextFruit);
			PathPoint nextPoint = new PathPoint(eatingPoint, minTime);
			nextPackman.path.add(nextPoint);
			nextPackman.setSeconds(minTime + nextPackman.getSeconds());
			game.fruitsAlive.remove(nextFruit);
		}
	}

}
