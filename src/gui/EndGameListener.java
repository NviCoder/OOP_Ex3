package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import GeoObjects.Fruit;
import GeoObjects.Packman;
import gameObjects.PathPoint;

public class EndGameListener implements ActionListener {

	private MainWindow gui;
	private Set <Packman> runPackmans;
	
	public EndGameListener(MainWindow gui) {
		super();
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (gui.game.packmans.isEmpty())
			return;
		
		gui.seconds = gui.game.findShortestPath();
//		runPackmans = new HashSet<>(gui.game.packmans);
		
		for (Packman packman: gui.game.packmans) {
			packman.setLocation(packman.path.getLast().getLocation());
			PathPoint last = packman.path.getLast();
			PathPoint current = packman.path.pollFirst();
			while (current != last) {
				PathPoint next = packman.path.pollFirst();
				gui.lines.add(gui.addLine(current.getLocation(), next.getLocation()));
				current = next;
			}
		}
		
		//calculate total weight
		gui.totalWeight = 0;
		for (Fruit fruit: gui.game.fruits)
			gui.totalWeight += fruit.getWeight();
		gui.repaint();
		System.out.println("seconds: "+gui.seconds);
	}

}
