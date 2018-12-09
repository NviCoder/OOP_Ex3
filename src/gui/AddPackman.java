package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Popup;

import GeoObjects.Packman;
import GeoObjects.Point3D;

public class AddPackman extends Popup {

	MainWindow gui;
	public Point3D locatoin;
	public int id;

	public AddPackman(MainWindow gui, Point3D locatoin, int id) {
		this.gui = gui;
		this.id = id;
		this.locatoin = locatoin;
		InitPopUp();
	}

	private void InitPopUp() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(400, 300);

		frame.getContentPane().setLayout(new FlowLayout());
		JTextField speedText = new JTextField("Enter speed");
		JTextField radiusText = new JTextField("Enter radius");
		frame.getContentPane().add(speedText);
		frame.getContentPane().add(radiusText);



		//submit button
		JButton b=new JButton("Submit");    
		    
		JLabel labelSpeed = new JLabel();		
		labelSpeed.setText("Enter Speed:");
		labelSpeed.setBounds(10, 10, 100, 100);
		
		JLabel labelRadius = new JLabel();		
		labelRadius.setText("Enter Radius:");
		labelRadius.setBounds(10, 10, 100, 100);

		//empty label which will show event after button clicked
		JLabel labelEvent = new JLabel();
		labelSpeed.setBounds(10, 110, 200, 100);

		
		//textfield to enter speed
		JTextField speedField= new JTextField();
		speedField.setBounds(110, 50, 130, 30);
		
		//textfield to enter radius
		JTextField radiusField= new JTextField();
		radiusField.setBounds(110, 50, 130, 30);

		//add to frame
		frame.add(labelSpeed);
		frame.add(labelRadius); 
		frame.add(labelEvent);
		
		
		frame.add(speedField);
		frame.add(radiusField);
		frame.setSize(300,300);    

		JButton add = new JButton("add");
		add.setBounds(100,100,140, 40);
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean goodInput = true;
				double speed = -1, radius = -1;
				try {
					String speedStr = speedField.getText();
					speed = Double.parseDouble(speedStr);
					String radiusStr = radiusField.getText();
					radius = Double.parseDouble(radiusStr);
				}
				catch (Exception ex) {
					//do nothing
				}
				if (speed < 0 || radius < 0)
					labelEvent.setText("try again");
				else {
					gui.game.packmans.add(new Packman(locatoin, speed, radius, id));
					frame.setVisible(false);
				}
			}
		});

		frame.add(add);
		frame.setLayout(null);   
		frame.setVisible(true);
	}


}
