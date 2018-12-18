package gui;
import java.util.ListIterator;

import GeoObjects.Packman;
import GeoObjects.Point3D;
import gameObjects.PathPoint;
/**
 * This class allows us to the user to see on live
 * by clicking on the real time button the packmans moving.
 * @author Elad and Yoav.
 *
 */

public class RealTime implements Runnable {

	MainWindow gui;

	////////////////////***Constructor****///////////////////////////////////

	public RealTime(MainWindow gui) {
		this.gui = gui;
	}
	
	///////////////*** Methods ***/////////////////////
	
	@Override
	public void run() {
		if (!gui.game.calculated) {
			gui.lines.clear();
			gui.game.findShortestPath();
		}

		PopUp pop = new PopUp(gui.game); //Treating the Pop Up window
		double endTime = gui.game.getSeconds();
		gui.addAllLines();
		gui.repaint();
		System.out.println("total time: " + gui.game.timetoString());

		for (int time=0; time < endTime+1; time++ ) {
			pop.refresh(time);
			if (gui.stopRunning)
				return;
			long processingStart = System.currentTimeMillis();
			for (Packman packman: gui.game.packmans) {
				if (time > packman.path.getLast().getSeconds())
					packman.setLocation(packman.path.getLast().getLocation());
				else {
					ListIterator<PathPoint> it = packman.path.listIterator();
					PathPoint current = it.next();
					while (it.hasNext()) {
						PathPoint next = it.next();
						if (next.getSeconds() < time)
							current = next;
						else {
							double ratio = (time - current.getSeconds()) / (next.getSeconds() - current.getSeconds());
							Point3D mid = gui.mc.midPoint(current.getLocation(), next.getLocation(), ratio);
							packman.setLocation(mid);
							break;
						}
					}
				}
			}
			System.out.println("time: "+time);
			gui.paint(gui.getGraphics()); //Update the data to the map!!
			long processTime = 1000 - (System.currentTimeMillis() - processingStart);
			try {
				Thread.sleep(processTime);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		for (Packman packman: gui.game.packmans) 
			packman.setLocation(packman.path.getLast().getLocation());
	}

}
