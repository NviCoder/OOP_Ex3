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

	public double seconds = 0;
	public int totalWeight = 0;

	private BufferedImage[] fruitsImages;

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

		Menu menuAddObject = new Menu("add");
		MenuItem addFruitItem = new MenuItem("fruit");
		MenuItem addPackmanItem = new MenuItem("packman");
		menuBar.add(menuAddObject);
		menuAddObject.add(addFruitItem);
		menuAddObject.add(addPackmanItem);

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
				for (Packman packman: game.packmans)
					packman.setLocation(packman.getStartLocation());
				lines.clear();
				repaint();
			}
		});

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
//		proportionW = this.getWidth()/map.widht();
//		proportionH = this.getHeight()/map.height();
		
		g.drawImage(map.myImage, 0, 0, this.getWidth(), this.getHeight(), this);


		//draw fruits
		for (Fruit fruit: game.fruits) {
			Pixel bit = map.gps2pixel(fruit.getLocation());
			bit.setProportion(proportionW, proportionH);

			g.drawImage(fruitsImages[fruit.getRandImage()], bit.x(), bit.y(), this );
		}
		//			g.setColor(Color.red);
		//			g.fillOval(bit.x(), bit.y(), 10, 10); //check it! change to image of fruit??

		//draw packmans
		for (Packman packman: game.packmans) {
			Pixel bit = map.gps2pixel(packman.getLocation());
			bit.setProportion(proportionW, proportionH);// With tzvi code we don't need this.
			try
			{
				BufferedImage img = ImageIO.read( new File("ImagesforGui\\thePackman2.png" ));
				g.drawImage( img, bit.x(), bit.y(), this );
			}
			catch ( IOException exc )
			{
				System.out.println(exc.toString());
			}
			//			g.setColor(Color.yellow);
			//          g.fillOval(bit.x(), bit.y(), 10, 10); //check it! change to image of packman??
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

	public static void main(String[] args) {
		Map m = new Map("E:\\yoav\\מדעי המחשב\\סמסטר א\\מונחה עצמים\\מטלה3\\Ex3 (2)\\Ex3\\Ariel1.png");
		System.out.println("height:" + m.height() + " widht:" + m.widht());

		MainWindow window = new MainWindow(m);
		window.setVisible(true);
		window.setSize(window.map.widht(),window.map.height());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

