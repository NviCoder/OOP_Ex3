package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import convertor.Csv2Game;

public class ImportCsv implements ActionListener {
	
	private MainWindow gui;
	private Csv2Game convertor;
	private final JFileChooser fc = new JFileChooser();

	public ImportCsv(MainWindow gui) {
		super();
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//TODO
		return null;
	}

}