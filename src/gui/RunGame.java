package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;

//import com.sun.prism.Graphics;

import GeoObjects.Fruit;
import GeoObjects.Packman;
import GeoObjects.Point3D;
import gameObjects.PathPoint;
import guiObjects.Line;

/**
 * When the user wants to run the game.
 * This class create a thread for a each game.
 * 
 * @author Yoav and Elad.
 *
 */
public class RunGame implements ActionListener {

	MainWindow gui;

////////////////////***Constructor****///////////////////////////////////

	public RunGame(MainWindow gui) {
		super();
		this.gui = gui;
		
	}
	
	///////////////*** Methods ***/////////////////////

	@Override
	public void actionPerformed(ActionEvent e) {
		gui.stopRunning();
		gui.stopRunning = false;
		RealTime run = new RealTime(gui);
		gui.addFruit = false;
		gui.addPackman = false;
		Thread thread = new Thread(run);
		thread.start();
		
	}

}
