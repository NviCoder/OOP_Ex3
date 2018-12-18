package gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Popup;

import GeoObjects.Packman;
import GeoObjects.Point3D;
/**
 * This class created for a small window when the user want to enter a packman.
 * The window take from the user, the speed and the radius of the packman. 
 */
public class AddPackman extends JFrame {

	MainWindow gui;
	public Point3D location;
	public int id;

	public JLabel labelEvent = new JLabel();
	public JTextField speedText = new JTextField("");
	public JTextField radiusText = new JTextField("");

	public AddPackman(MainWindow gui, Point3D location, int id) {
		this.gui = gui;
		this.id = id;
		this.location = location;
		Init();
	}

	private void Init() {
//		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(250, 200);
		setLayout(new GridLayout(3,2));


		JLabel labelSpeed = new JLabel();
		labelSpeed.setText("Enter Speed:");
		labelSpeed.setBounds(10, 10, 100, 100);
		speedText.setBounds(10, 10, 100, 100);
		speedText.setText("");

		add(labelSpeed);
		add(speedText);



		JLabel labelRadius = new JLabel();
		labelRadius.setText("Enter radius:");
		labelRadius.setBounds(10, 10, 100, 100);
		radiusText.setBounds(10, 10, 100, 100);
		radiusText.setText("");

		add(labelRadius);
		add(radiusText);



		labelEvent.setText("");
		add(labelEvent);

		JButton submit = new JButton("Submit");    
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gui.stopRunning();
				double speed = -1, radius = -1;
				try {
					String speedStr = speedText.getText();
					speed = Double.parseDouble(speedStr);
					String radiusStr = radiusText.getText();
					radius = Double.parseDouble(radiusStr);
					if (speed < 0 || radius < 0)
						labelEvent.setText("Try again");
					else {
						gui.game.packmans.add(new Packman(location, speed, radius, id));
						gui.game.calculated = false;
						gui.repaint();
						setVisible(false);
					}
				}
				catch (Exception ex) {
					labelEvent.setText("Try again");
				}
			}
		});

		add(submit);

		setVisible(true);
	}

}
