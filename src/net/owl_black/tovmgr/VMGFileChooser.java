package net.owl_black.tovmgr;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * This class is used for opening VMG files. When you instantiate this class, a
 * window will be opened and prompt the user to select VMG files.
 * 
 * @author Louisbob
 * @version 26/06/2012 - v0.3
 */
public class VMGFileChooser {
	// instance variables
	JFileChooser openDiag = new JFileChooser();
	File currentDir;
	int isSelected = 0;

	/***
	 * 
	 * @param windowTitle The title of the opening window.
	 * @param startingDir The directory where we start browsing.
	 */
	public VMGFileChooser(File startingDir) {
		// Define file extensions that can be opened
		FileFilter vmg = new SimpleFileFilter("VMG files (*.vmg)", ".vmg");

		// Allow multiple file selection
		openDiag.setMultiSelectionEnabled(true);

		// Add the VMG extension
		openDiag.addChoosableFileFilter(vmg);

		// Set vmg file to default filter
		openDiag.setFileFilter(vmg);

		// Add name to the titlebar
		openDiag.setDialogTitle("Select VMGs files or a folder containing VMGs...");

		// Open the latest directory
		openDiag.setCurrentDirectory(startingDir);

	}

	

	/**
	 * As smsDB of this object is private & static, this method reset the
	 * database.
	 * 
	 * @return void
	 */
	public void openDiag() {
		isSelected = openDiag.showOpenDialog(null);
	}

	/**
	 * This method allows you to get the list of files that have been opened
	 * with the open window.
	 * 
	 * @return File array
	 */
	public File[] getSelectedFiles() {

		if (isSelected == JFileChooser.APPROVE_OPTION) {
			// Save the current directory path
			currentDir = openDiag.getCurrentDirectory();
			return openDiag.getSelectedFiles();
		}

		return null;
	}

	/**
	 * This method return the latest directory that have been browsed.
	 * 
	 * @return File currentDir
	 */
	public File getLastDir() {
		return currentDir;
	}

	private class SimpleFileFilter extends FileFilter {
		private String description;
		private String extension;

		public SimpleFileFilter(String description, String extension) {
			if (description == null || extension == null) {
				throw new NullPointerException("The description and/or the extension can't be null.");
			}
			this.description = description;
			this.extension = extension;
		}

		@Override
		public boolean accept(File file) {
			if (file.isDirectory())
				return true;

			return file.getName().toLowerCase().endsWith(extension);
		}

		@Override
		public String getDescription() {
			return description;
		}
	}

}
