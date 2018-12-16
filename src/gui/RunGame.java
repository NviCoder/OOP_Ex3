package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;

import com.sun.prism.Graphics;

import GeoObjects.Fruit;
import GeoObjects.Packman;
import GeoObjects.Point3D;
import gameObjects.PathPoint;
import guiObjects.Line;

public class RunGame implements ActionListener {

	private MainWindow gui;
	private Set <Packman> runPackmans;


	public RunGame(MainWindow gui) {
		super();
		this.gui = gui;
		gui.addFruit = false;
		gui.addPackman = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (!gui.game.calculated) {
			gui.game.findShortestPath();
		}

		gui.totalWeight = 0;
		double endTime = gui.game.getSeconds();
		gui.addAllLines();
		gui.repaint();
		System.out.println("total time: " + gui.game.timetoString());


		for (int time=0; time < endTime+1; time++ ) {
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
