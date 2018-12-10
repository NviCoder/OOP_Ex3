package gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.PopupMenu;
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
import guiObjects.Pixel;
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

	public double seconds = 0;
	public int totalWeight = 0;

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
		MenuItem exportToKml = new MenuItem("export game to kml");
		menuBar.add(menuRun);
		menuRun.add(realtime);
		menuRun.add(endPoint);
		menuRun.add(exportToKml);

		this.setMenuBar(menuBar);

		//set listeners for the menu bar
		importCsvItem.addActionListener(new ImportCsv(this));
		exportToCsvItem.addActionListener(new ExportCsv(this));
		exportToKml.addActionListener(new ExportKml(this));
		realtime.addActionListener(new RunGame(this));
		endPoint.addActionListener(new EndGameListener(this));

		addFruitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addFruit = !addFruit;
				addPackman = false;
			}
		});
		addPackmanItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addPackman = !addPackman;
				addFruit = false;
			}
		});
	}

	public void paint(Graphics g)
	{
		g.drawImage(map.myImage, 0, 0, this);

		//draw fruits
		for (Fruit fruit: game.fruits) {
			Pixel bit = map.gps2pixel(fruit.getLocation());
			bit.setProportion(proportionW, proportionH);
			g.setColor(Color.red);
			g.fillOval(bit.x(), bit.y(), 10, 10); //check it! change to image of fruit??
		}

		//draw packmans
		for (Packman packman: game.packmans) {
			Pixel bit = map.gps2pixel(packman.getLocation());
			bit.setProportion(proportionW, proportionH);
			g.setColor(Color.yellow);
			g.fillOval(bit.x(), bit.y(), 10, 10); //check it! change to image of packman??
		}


		//draw pathes
		g.setColor(Color.green);
		for (Line line: lines) {
			Pixel head = line.getHead();
			head.setProportion(proportionW, proportionH);
			Pixel tail= line.getTail();
			tail.setProportion(proportionW, proportionH);
			g.drawLine(head.x(), head.y(), tail.x(), tail.y());
		}
		for (Line line: tempLines) {
			Pixel head = line.getHead();
			head.setProportion(proportionW, proportionH);
			Pixel tail= line.getTail();
			tail.setProportion(proportionW, proportionH);
			g.drawLine(head.x(), tail.x(), head.y(), tail.y());
		}

	}


	@Override
	public void mouseClicked(MouseEvent arg) {
		Pixel pixel = new Pixel(arg.getX(), arg.getY());
		System.out.println(pixel);
		pixel.removeProportion(proportionW, proportionH);
		if (addFruit) {
			Fruit fruit = new Fruit(map.pixel2gps(pixel), 1,  game.fruits.size()+1);
			game.fruits.add(fruit);
			repaint();
		}
		if (addPackman) {
			//TODO
			AddPackman adder = new AddPackman(this, map.pixel2gps(pixel), game.getNextPackmanID());
		}
	}

	public Line addLine(Point3D gps0, Point3D gps1) {
		Pixel head = map.gps2pixel(gps0);
		Pixel tail = map.gps2pixel(gps1);
		return new Line(head, tail);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		Map m = new Map("E:\\yoav\\מדעי המחשב\\סמסטר א\\מונחה עצמים\\מטלה3\\Ex3 (2)\\Ex3\\Ariel1.png");
		System.out.println("height:" + m.height() + " widht:" + m.widht());

		MainWindow window = new MainWindow(m);
		window.setVisible(true);
		window.setSize(window.map.widht(),window.map.height());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
