package gui;
import javax.swing.JFrame;

import guiObjects.Map;


public class Main 
{
	public static void main(String[] args)
	{
	    Map map = new Map("ImagesforGui\\Ariel1.png");
		MainWindow window = new MainWindow(map);
		window.setVisible(true);
		window.setSize(map.widht(),map.height());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
