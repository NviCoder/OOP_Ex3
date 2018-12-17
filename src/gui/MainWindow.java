package gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
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

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;


import Coords.MyCoords;
import GeoObjects.Fruit;
import GeoObjects.Packman;
import GeoObjects.Point3D;
import convertor.Game2Csv;
import convertor.Game2kml;
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
	public boolean stopRunning = true;

	public int totalWeight = 0;

	private BufferedImage[] fruitsImages;
	private BufferedImage packmanImage;

	final JFileChooser fc = new JFileChooser();
	final MyCoords mc = new MyCoords();
	

	public MainWindow(Map _map) 
	{		
		fruitsImages = new BufferedImage[6];
		try {
			fruitsImages[0] = ImageIO.read( new File("ImagesforGui\\apple.png" ));
			fruitsImages[1] = ImageIO.read( new File("ImagesforGui\\apple2.png" ));
			fruitsImages[2] = ImageIO.read( new File("ImagesforGui\\banana.png" ));
			fruitsImages[3] = ImageIO.read( new File("ImagesforGui\\orange.png" ));
			fruitsImages[4] = ImageIO.read( new File("ImagesforGui\\peach.png" ));
			fruitsImages[5] = ImageIO.read( new File("ImagesforGui\\watermalon.png" ));
			packmanImage = ImageIO.read( new File("ImagesforGui\\thePackman2.png" ));
		} catch (IOException exc) {
			//			e.printStackTrace();
			System.out.println(exc.toString());
		}

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
		menuFile.add(importCsvItem);
		menuFile.add(exportToCsvItem);

		Menu menuAddObject = new Menu("objects");
		MenuItem addFruitItem = new MenuItem("add fruit");
		MenuItem addPackmanItem = new MenuItem("add packman");
		MenuItem resetItem = new MenuItem("clear");
		menuBar.add(menuAddObject);
		menuAddObject.add(addFruitItem);
		menuAddObject.add(addPackmanItem);
		menuAddObject.add(resetItem);

		Menu menuRun = new Menu("run");
		MenuItem realtime = new MenuItem("real time");
		MenuItem endPoint = new MenuItem("end point");
		MenuItem startPoint = new MenuItem("start point");
		MenuItem exportToKml = new MenuItem("export game to kml");

		menuBar.add(menuRun);
		menuRun.add(realtime);
		menuRun.add(endPoint);
		menuRun.add(startPoint);
		menuRun.add(exportToKml);

		this.setMenuBar(menuBar);

		//set listeners for the menu bar
		importCsvItem.addActionListener(new ImportCsv(this));

		exportToCsvItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String pathName = chooseFolder();
				if (pathName != null) {
					Game2Csv convertor = new Game2Csv();
					convertor.export(game, pathName, "game_" + randNumber());
				}

			}
		});
		exportToKml.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String pathName = chooseFolder();
				if (pathName != null) {
					Game2kml convertor = new Game2kml();
					convertor.export(game, pathName, "game_" + randNumber());
				}
			}
		});

		realtime.addActionListener(new RunGame(this));	
		endPoint.addActionListener(new EndGameListener(this));

		startPoint.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				stopRunning();
				startPoint();
			}
		});

		addFruitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stopRunning();
				addFruit = !addFruit;
				addPackman = false;
			}
		});
		addPackmanItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stopRunning();
				addPackman = !addPackman;
				addFruit = false;
			}
		});

		resetItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				stopRunning();
				clear();
			}
		});
	}

	public void paint(Graphics g)
	{
		g.drawImage(map.myImage,8, 51, this.getWidth()-16, this.getHeight()-59, this);


		//draw fruits
		for (Fruit fruit: game.fruits) {
			Pixel pixel = map.gps2pixel(fruit.getLocation(), this.getWidth()-16, this.getHeight()-59);
			g.drawImage(fruitsImages[fruit.getRandImage()], pixel.x()+8, pixel.y()+51, this );
		}


		//draw packmans
		for (Packman packman: game.packmans) {
			Pixel pixel = map.gps2pixel(packman.getLocation(), this.getWidth()-16, this.getHeight()-59);
			g.drawImage(packmanImage, pixel.x()+8, pixel.y()+51, this);
		}


		//draw pathes
		g.setColor(Color.green);
		for (Line line: lines) {
			Pixel head = map.gps2pixel(line.getHead(), this.getWidth()-16, this.getHeight()-59);
			Pixel tail= map.gps2pixel(line.getTail(), this.getWidth()-16, this.getHeight()-59);
			g.drawLine(head.x()+8+8, head.y()+51+8, tail.x()+8+8, tail.y()+51+8);
		}
	}


	//	public Line addLine(Point3D gps0, Point3D gps1) {
	//		Pixel head = map.gps2pixel(gps0, this.getWidth()-8, this.getHeight()-8);
	//		Pixel tail = map.gps2pixel(gps1, this.getWidth()-8, this.getHeight()-8);
	//		return new Line(head, tail);
	//	}

	@Override
	public void mouseClicked(MouseEvent arg) {
		Pixel pixel = new Pixel(arg.getX()-8, arg.getY()-51);
		System.out.println(pixel);
		pixel.removeProportion(proportionW, proportionH);
		if (addFruit) {
			Fruit fruit = new Fruit(map.pixel2gps(pixel, this.getWidth()-16, this.getHeight()-59), 1,  game.fruits.size()+1);
			game.fruits.add(fruit);
			game.calculated = false;
			repaint();
		}
		if (addPackman) {
			//TODO
			AddPackman adder = new AddPackman(this, map.pixel2gps(pixel, this.getWidth()-16, this.getHeight()-59), game.getNextPackmanID());
		}
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

	public String chooseFolder() {
		JFileChooser chooser = new JFileChooser();
		chooser.setApproveButtonText("Save");
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Folders");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		//    
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File path = chooser.getSelectedFile();
			return path.getAbsolutePath();
		}
		else return null;
	}

	private int randNumber() {
		return (int)(Math.random()*100000000);
	}

	public void clear() {
		// TODO Auto-generated method stub
		game.clear();
		game.calculated = true;
		lines.clear();
		totalWeight = 0;
		repaint();
	}

	public void startPoint() {
		for (Packman packman: game.packmans) {
			packman.setLocation(packman.getStartLocation());
		}
		lines.clear();
		repaint();
	}

	public void addAllLines() {
		for (Packman packman: game.packmans) {
			java.util.ListIterator<PathPoint> it = packman.path.listIterator();
			PathPoint current = it.next();
			PathPoint next;
			while (it.hasNext()) {
				next = it.next();
				lines.add(new Line(current.getLocation(), next.getLocation()));
				current = next;
			}
		}
	}
	
	public void stopRunning() {
		if (stopRunning)
			return;
		stopRunning=true;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}

