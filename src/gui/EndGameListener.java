package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

//import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;

import GeoObjects.Fruit;
import GeoObjects.Packman;
import gameObjects.PathPoint;
import guiObjects.Line;
/**
 * When the user wants to see the end of the game.
 * This EndGameListener will make it happend.
 * @author Yoav and Elad.
 *
 */
public class EndGameListener implements ActionListener {

	private MainWindow gui;
	private Set <Packman> runPackmans;

	public EndGameListener(MainWindow gui) {
		super();
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gui.stopRunning();
		if (gui.game.packmans.isEmpty())
			return;
		if (!gui.game.calculated) { 
			gui.lines.clear();
			gui.game.findShortestPath();			
		}

		for (Packman packman: gui.game.packmans) {
			packman.setSeconds(0);
			packman.setLocation(packman.path.getLast().getLocation());
		}
		gui.addAllLines();

		//calculate total weight
		gui.totalWeight = 0;
		for (Fruit fruit: gui.game.fruits)
			gui.totalWeight += fruit.getWeight();

		gui.game.calculated = true;

		gui.repaint();
		PopUp pop = new PopUp(gui.game);
		pop.endPosition();
		System.out.println(gui.game.timetoString());
	}

}
