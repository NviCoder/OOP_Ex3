package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import GeoObjects.Packman;
import GeoObjects.Point3D;
import gameObjects.PathPoint;

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
		runPackmans = new HashSet<>(gui.game.packmans);
		gui.lines.clear();
		gui.seconds = 0;
		gui.totalWeight = 0;
		double endTime = gui.game.findShortestPath();

		for (int time=0; time < endTime+1; time++ ) {
			long processingStart = System.currentTimeMillis();
			for (Packman packman: runPackmans) {
				PathPoint first = packman.path.pollFirst();
				while (first != null && !packman.path.isEmpty()) {
					PathPoint next = packman.path.getFirst();
					if (next.getSeconds() <= time) {
						gui.lines.add(gui.addLine(first.getLocation(), next.getLocation()));
						gui.totalWeight += next.getWeight();
						first = next;
						packman.path.removeFirst();
						packman.setLocation(next.getLocation());
					}
					else {
						double ratio = (time - first.getSeconds()) / (next.getSeconds() - first.getSeconds());
						Point3D mid = gui.mc.midPoint(first.getLocation(), next.getLocation(), ratio);
						packman.setLocation(mid);
						packman.path.addFirst(first);
						break;
					}
				}
			}
			gui.seconds++;
			System.out.println("time: "+time);
			long processTime = 1000 - (System.currentTimeMillis() - processingStart);
			try {
				Thread.sleep(processTime);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			gui.repaint();
		}
	}

}
