package gui;

import java.util.ListIterator;

import GeoObjects.Packman;
import GeoObjects.Point3D;
import gameObjects.PathPoint;

public class RealTime implements Runnable {

	MainWindow gui;
	
	public RealTime(MainWindow gui) {
		this.gui = gui;
	}

	@Override
	public void run() {
		if (!gui.game.calculated) {
			gui.lines.clear();
			gui.game.findShortestPath();
		}

		gui.totalWeight = 0;
		double endTime = gui.game.getSeconds();
		gui.addAllLines();
		gui.repaint();
		System.out.println("total time: " + gui.game.timetoString());


		for (int time=0; time < endTime+1; time++ ) {
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
							//						gui.totalWeight += next.getWeight();
							double ratio = (time - current.getSeconds()) / (next.getSeconds() - current.getSeconds());
							Point3D mid = gui.mc.midPoint(current.getLocation(), next.getLocation(), ratio);
							packman.setLocation(mid);
							break;
						}
					}
				}
			}
			System.out.println("time: "+time);
			//			gui.repaint();
			gui.paint(gui.getGraphics());
			long processTime = 1000 - (System.currentTimeMillis() - processingStart);
			try {
				Thread.sleep(processTime);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		for (Packman packman: gui.game.packmans) 
			packman.setLocation(packman.path.getLast().getLocation());

	}

}
