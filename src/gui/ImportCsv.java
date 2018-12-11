package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;

import convertor.Csv2Game;

public class ImportCsv implements ActionListener {

	private MainWindow gui;
	private Csv2Game convertor = new Csv2Game();
	private final JFileChooser fc = new JFileChooser();

	public ImportCsv(MainWindow gui) {
		super();
		this.gui = gui;
		gui.addFruit = false;
		gui.addPackman = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
		fc.setFileFilter(filter);
		int returnVal = fc.showOpenDialog(gui);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			gui.game = convertor.convert(file);
			gui.repaint();
		}
	}

}
