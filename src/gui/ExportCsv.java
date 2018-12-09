package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import convertor.Csv2Game;
import convertor.Game2Csv;

public class ExportCsv implements ActionListener {

	private MainWindow gui;
	private Game2Csv convertor;
	private final JFileChooser fc = new JFileChooser();

	public ExportCsv(MainWindow gui) {
		super();
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//TODO
	}

}
