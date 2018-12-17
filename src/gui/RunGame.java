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

	MainWindow gui;
	
	public RunGame(MainWindow gui) {
		super();
		this.gui = gui;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		RealTime run = new RealTime(gui);
		gui.addFruit = false;
		gui.addPackman = false;
		gui.thread = new Thread(run);
		gui.thread.start();
		
	}

}
