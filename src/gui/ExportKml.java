package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

public class ExportKml implements ActionListener {
	
	private MainWindow gui;
	private final JFileChooser fc = new JFileChooser();

	public ExportKml(MainWindow gui) {
		super();
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		return null;
	}

}
