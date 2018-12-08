package gui;
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

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import convertor.Csv2Game;
import gameObjects.Game;
import guiObjects.Map;


public class MainWindow extends JFrame implements MouseListener
{
	public Map map;
	public Game game = new Game();
	
	final JFileChooser fc = new JFileChooser();
	
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
		MenuItem exportToCsv = new MenuItem("export to csv");
		MenuItem importCsvItem = new MenuItem("import csv");
		menuBar.add(menuFile);
		menuFile.add(exportToCsv);
		menuFile.add(importCsvItem);
		
		Menu menuAddObject = new Menu("add");
		MenuItem addFruit = new MenuItem("fruit");
		MenuItem addPackman = new MenuItem("packman");
		menuBar.add(menuAddObject);
		menuAddObject.add(addFruit);
		menuAddObject.add(addPackman);
		
		Menu menuRun = new Menu("run");
		MenuItem realtime = new MenuItem("real time");
		MenuItem endPoint = new MenuItem("end point");
		menuBar.add(menuRun);
		menuRun.add(realtime);
		menuRun.add(endPoint);
		
		this.setMenuBar(menuBar);
		
		//set listeners for the menu bar
		ImportCsv importer = new ImportCsv();
		importCsvItem.addActionListener(importer);
		
	}

	public void paint(Graphics g)
	{
	
	}

	@Override
	public void mouseClicked(MouseEvent arg) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
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

}
