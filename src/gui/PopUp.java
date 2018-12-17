package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
//import com.jgoodies.forms.layout.FormLayout;
//import com.jgoodies.forms.layout.ColumnSpec;
//import com.jgoodies.forms.layout.RowSpec;

import gameObjects.Game;
import gameObjects.PathPoint;

import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;

import GeoObjects.Fruit;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Window.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Point;

public class PopUp {

	private JFrame frmGameDate;
	private Game game;
	private int totalWeight = 0;
	private java.util.ListIterator<PathPoint> iterator;
	private PathPoint next;
	private JLabel timeValue;
	private JLabel weightValue;

	//public popUp(ArrayList<PathPoint> t) { ///sould be like this? 
	public PopUp(Game game) {
		this.game = game;
		iterator = game.allPoints.listIterator();
		initialize();
		frmGameDate.setVisible(true);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGameDate = new JFrame();
		frmGameDate.setType(Type.POPUP);
		frmGameDate.setTitle("Game Data");
		frmGameDate.setResizable(false);
		frmGameDate.setBounds(100, 100, 368, 85);
		frmGameDate.getContentPane().setLayout(null);

		JLabel lbTotalWeight = new JLabel("Total Weight:");
		lbTotalWeight.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbTotalWeight.setForeground(Color.BLACK);
		lbTotalWeight.setBounds(10, 14, 90, 25);
		frmGameDate.getContentPane().add(lbTotalWeight);

		weightValue = new JLabel("0.0");
		weightValue.setForeground(Color.RED);
		weightValue.setFont(new Font("Tahoma", Font.BOLD, 12));
		weightValue.setBounds(103, 14, 54, 25);
		frmGameDate.getContentPane().add(weightValue);

		JLabel lbTime = new JLabel("Time:");
		lbTime.setForeground(Color.BLACK);
		lbTime.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbTime.setBounds(167, 14, 39, 25);
		frmGameDate.getContentPane().add(lbTime);


		timeValue = new JLabel("0");
		timeValue.setForeground(Color.RED);
		timeValue.setFont(new Font("Tahoma", Font.BOLD, 12));
		timeValue.setBounds(214, 14, 54, 25);
		frmGameDate.getContentPane().add(timeValue);



		JLabel lbSeconds = new JLabel("Seconds");
		lbSeconds.setForeground(Color.BLACK);
		lbSeconds.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbSeconds.setBounds(263, 15, 65, 24);
		frmGameDate.getContentPane().add(lbSeconds);
		///////////////////////////////////////
		//		Iterator<PathPoint> itr = t.iterator();
		//	    while (itr.hasNext()) {
		//	      PathPoint element = itr.next();
		//	      int updateWeight = element.getWeight()+ Integer.parseInt(weightValue.getText());
		//	      JLabel update_Weight = new JLabel(Integer.toString(updateWeight));
		//	      weightValue = update_Weight;
	}
	
	public void refresh(int seconds) {		
		int addWeight = 0;
		while (iterator.hasNext()) {
			next = iterator.next();
			if (next.getSeconds() > seconds) {
				iterator.previous();
				break;
			}
			addWeight += next.getWeight(); 
		}
		timeValue.setText("" + seconds);
		if (addWeight > 0 ) {
			totalWeight += addWeight;
			weightValue.setText("" + totalWeight);
		}
	}

	public void endPosition() {
		timeValue.setText("" + (int)(100*game.getSeconds())/100.0);
		for (Fruit fruit: game.fruits)
			totalWeight += fruit.getWeight();
		weightValue.setText("" + totalWeight);
	}
}
//}
