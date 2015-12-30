package net.owl_black.tovmgr;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.awt.*;
import java.util.*;

import java.io.File;

/**
 * This class is the core of the OwlVMGReader application
 * @author LouisBob
 * @version 0.92 - 16/06/2012
 * 
 */

public class CoreGUI  {
    
	/* Environment */
	
		//User-interface objects declaration
	    JPanel contactPanel, conversPanel;
	    JFrame mainWindow;
	    JMenuItem openAction;
	    JMenuItem backupAction;
	    JMenuItem settingsAction;
	    JMenuItem exitAction;
	    JMenuItem exportAction;
	    JMenuItem cleanCharAction;
     
    /**
     *  Main method of the core. It has to be called to launch application.
     */
    public static void main(String[] args) {
	     new CoreGUI();
	}

	/**
	 * 	Constructor of the coreGUI class: set the look, instantiate all element of the user-interface,
	 *  and initialize the core database.
	 */
	public CoreGUI() {
        
        //To get MS Windows look
        
        /* Main window (frame) configuration */
        mainWindow = new JFrame();
        mainWindow.setTitle("The Owl VMG Reader");

        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        mainWindow.setSize(800, 500);
        mainWindow.setLocationRelativeTo(null); //Center the window on the screen
        
        /* Panel instantiation */ 
        contactPanel = new JPanel();
        conversPanel = new JPanel();
        
        /* Menubar set-up */	
        JMenuBar menuBar = new JMenuBar(); // Creates a menubar for the mainWindow
        
        // Define and add two drop down menu to the menubar
        JMenu fileMenu = new JMenu("File");
        JMenu toolsMenu = new JMenu("Tools");
        JMenu viewMenu = new JMenu("View");
        
        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);
        menuBar.add(viewMenu);
        
        mainWindow.setJMenuBar(menuBar); // Add the menubar to the frame
        
        // Create and add simple menu item to one of the drop down menu
        openAction = new JMenuItem("Open VMG Files...");
        backupAction = new JMenuItem("Backup as *.mvmg-NA");
        backupAction.setEnabled(false);
        settingsAction = new JMenuItem("Settings-NA");
        exitAction = new JMenuItem("Exit");
        exportAction = new JMenuItem("Export");
        cleanCharAction = new JMenuItem("Clean text...");
        
        JMenuItem cutAction = new JMenuItem("Cut");
        JMenuItem copyAction = new JMenuItem("Copy");
        JMenuItem pasteAction = new JMenuItem("Paste");
        
        fileMenu.add(openAction);
        //fileMenu.add(backupAction);
        //fileMenu.add(settingsAction);
        fileMenu.addSeparator();
        fileMenu.add(exitAction);
        
        toolsMenu.add(exportAction);
        toolsMenu.add(cleanCharAction);
        
        viewMenu.add(cutAction);
        viewMenu.add(copyAction);
        viewMenu.add(pasteAction);
        viewMenu.addSeparator();
        
        //Create a new object contactGUI, that will fill the panel contactPanel with good informations
        
        //Place contactPanel and conversPanel into the main window
        mainWindow.setLayout(new BorderLayout());
        mainWindow.add(contactPanel, BorderLayout.WEST);
        mainWindow.add(conversPanel, BorderLayout.CENTER);
        mainWindow.setVisible(true);
       
    }

}