package gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;

import Coords.MyCoords;
import GeoObjects.Fruit;
import GeoObjects.Packman;
import GeoObjects.Point3D;
import convertor.Csv2Game;
import gameObjects.Game;
import gameObjects.Path;
import gameObjects.PathPoint;
import guiObjects.Bit;
import guiObjects.Line;
import guiObjects.Map;


public class MainWindow extends JFrame implements MouseListener
{
	public Map map;
	public Game game = new Game();
	public double proportionH = 1;
	public double proportionW = 1;

	public boolean addFruit = false;
	public boolean addPackman = false;
	
	public HashSet<Line> lines = new HashSet<>();
	public HashSet<Line> tempLines = new HashSet<>();
	public Set <Packman> runPackmans = game.packmans;


	final JFileChooser fc = new JFileChooser();
	final MyCoords mc = new MyCoords();

	public MainWindow(Map _map) 
	{
		map = _map;
		initGUI();		
		this.addMouseListener(this); 
	}

	private void initGUI() 
	{

		//add menu bar
		MenuBar menuBar = new MenuBar();

		Menu menuFile = new Menu("File");
		MenuItem exportToCsvItem = new MenuItem("export to csv");
		MenuItem importCsvItem = new MenuItem("import csv");
		menuBar.add(menuFile);
		menuFile.add(exportToCsvItem);
		menuFile.add(importCsvItem);

		Menu menuAddObject = new Menu("add");
		MenuItem addFruitItem = new MenuItem("fruit");
		MenuItem addPackmanItem = new MenuItem("packman");
		menuBar.add(menuAddObject);
		menuAddObject.add(addFruitItem);
		menuAddObject.add(addPackmanItem);

		Menu menuRun = new Menu("run");
		MenuItem realtime = new MenuItem("real time");
		MenuItem endPoint = new MenuItem("end point");
		menuBar.add(menuRun);
		menuRun.add(realtime);
		menuRun.add(endPoint);

		this.setMenuBar(menuBar);

		//set listeners for the menu bar
		ImportCsv importer = new ImportCsv(this);
		importCsvItem.addActionListener(importer);
		ExportCsv exporter = new ExportCsv(this);
		exportToCsvItem.addActionListener(exporter);

		addFruitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addFruit = !addFruit;
				addFruitItem.setEnabled(addFruit);
				addPackmanItem.setEnabled(false);
			}
		});
		addPackmanItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addPackman = !addPackman;
				addPackmanItem.setEnabled(addPackman);
				addFruitItem.setEnabled(false);
			}
		});
		
		endPoint.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				game.findShortestPath();
				
				runPackmans = new HashSet<>(game.packmans);
				for (Packman packman: runPackmans) {
					packman.setLocation(packman.path.getLast().getLocation());
					PathPoint last = packman.path.getLast();
					PathPoint current = packman.path.pollFirst();
					while (current != last) {
						PathPoint next = packman.path.pollFirst();
						lines.add(addLine(current.getLocation(), next.getLocation()));
						current = next;
					}
				}
				repaint();
			}
		});
	}

	public void paint(Graphics g)
	{
		g.drawImage(map.myImage, 0, 0, this);

		//draw fruits
		for (Fruit fruit: game.fruits) {
			Bit bit = mc.gps2Bit(fruit.getLocation(), (int)(map.height()*proportionH), (int)(map.widht()*proportionW));
			g.setColor(Color.red);
			g.fillOval(bit.x()+2, bit.y()+2, bit.x()-2, bit.x()-2); //check it! change to image of fruit??
		}
		
		//draw packmans
		for (Fruit fruit: game.fruits) {
			Bit bit = mc.gps2Bit(fruit.getLocation(), (int)(map.height()*proportionH), (int)(map.widht()*proportionW));
			g.setColor(Color.yellow);
			g.fillOval(bit.x()+2, bit.y()+2, bit.x()-2, bit.x()-2); //check it! change to image of packman??
		}
		
		
		//draw pathes
		for (Line line: lines)
			g.drawLine(line.getHead().x(), line.getHead().y(), line.getTail().x(), line.getTail().y());
		for (Line line: tempLines)
			g.drawLine(line.getHead().x(), line.getHead().y(), line.getTail().x(), line.getTail().y());
		
	}


	@Override
	public void mouseClicked(MouseEvent arg) {
		Bit bit = new Bit(arg.getX(), arg.getY());
		if (addFruit) {
			Fruit fruit = new Fruit(mc.Bit2gps(bit, map.height(), map.widht()), game.fruits.size()+1);
			game.fruits.add(fruit);
			repaint();
		}
		if (addPackman)
			//TODO
	}
	
	public Line addLine(Point3D gps0, Point3D gps1) {
		Bit head = mc.gps2Bit(gps0, map.height(), map.widht());
		Bit tail = mc.gps2Bit(gps1, map.height(), map.widht());
		return new Line(head, tail);
	}

}
