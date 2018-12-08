package gui;
import javax.swing.JFrame;

import guiObjects.Map;


public class Main 
{
	public static void main(String[] args)
	{
		Map map = new Map("E:\\yoav\\מדעי המחשב\\סמסטר א\\מונחה עצמים\\מטלה3\\Ex3 (2)\\Ex3\\Ariel1.png");
		MainWindow window = new MainWindow(map);
		window.setVisible(true);
		window.setSize(map.widht(),map.height());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
