package gui;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;

import convertor.Csv2Game;
/**
 * When the user wants to import a csv file with data of a new game.
 * So when the button on the main window clicked. this class will make it happen.
 * @author Yoav and Elad.
 *
 */
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
			gui.clear();
			gui.game = convertor.convert(file);
			gui.game.calculated = false;
			gui.repaint();
		}
	}

}
