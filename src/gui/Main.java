package gui;
import javax.swing.JFrame;

import guiObjects.Map;

/**
 * This class runs the Game!!
 * Enjoy!!
 * 
 * Yoav and Elad.
 * @version 1.0
 * GitHub 
 * https://github.com/NviCoder/OOP_Ex3
 * Commit:  
 */
public class Main 
{
	public static void main(String[] args)
	{
	    Map map = new Map("ImagesforGui\\Ariel1.png");
		MainWindow window = new MainWindow(map);
		window.setVisible(true);
		window.setSize(map.getOriginWidht()+16,map.getOriginHeight()+59);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
