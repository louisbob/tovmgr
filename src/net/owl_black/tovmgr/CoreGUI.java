package net.owl_black.tovmgr;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.owl_black.tovmgr.wizard.WizardGUI;

/**
 * This class is the core of the OwlVMGReader application
 * @author LouisBob
 * @version 0.92 - 16/06/2012
 * 
 */

public class CoreGUI extends JFrame implements ActionListener, ViewInterface {
    
	private static final long serialVersionUID = 1L;
	
	/* Environment */
	//User-interface objects declaration
    JPanel contactPanel, conversPanel;
    JSplitPane splitPane;
    JMenuItem openAction;
    JMenuItem backupAction;
    JMenuItem settingsAction;
    JMenuItem exitAction;
    JMenuItem exportAction;
    JMenuItem cleanCharAction;

	/**
	 * 	Constructor of the coreGUI class: set the look, instantiate all element of the user-interface,
	 *  and initialize the core database.
	 */
	public CoreGUI() {
        
		super();
		
		//To get MS Windows look
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Set basic properties
		this.setTitle("The Owl VMG Reader");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        this.setSize(800, 500);
        this.setLocationRelativeTo(null); //Center the window on the screen
        
		//Create menu bar
		createMenu();

		//Provide minimum sizes for the two components in the split pane
        
        /* Panel instantiation */ 
        contactPanel = new ContactPanel();
        conversPanel = new MessagePanel();
        
  		//Create a split pane with the two scroll panes in it.
  		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
  		                           contactPanel, conversPanel);
  		splitPane.setOneTouchExpandable(true);
  		splitPane.setDividerLocation(100);
        
        
        //Create a new object contactGUI, that will fill the panel contactPanel with good informations
        
        //Place contactPanel and conversPanel into the main window
  		this.add(splitPane);
  		
  		//Register listener
  		AppContext.getInstance().getVmgController().addModelChangedListener(this);
        
    }
	
	private void createMenu() {
		/* Menubar set-up */	
        JMenuBar menuBar = new JMenuBar(); // Creates a menubar for the mainWindow
        
        // Define and add two drop down menu to the menubar
        JMenu fileMenu = new JMenu("File");
        
        JMenu toolsMenu = new JMenu("Tools");
        JMenu viewMenu = new JMenu("View");
        
        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);
        menuBar.add(viewMenu);
        
        this.setJMenuBar(menuBar); // Add the menubar to the frame
        
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
        openAction.addActionListener(this);
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
	}
	
	public void displayProcessingDialog() {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if(src == openAction) {
			System.out.println("Open Wizard");
			
			WizardGUI wizard = new WizardGUI(this, true);
			wizard.setLocationRelativeTo(null);
			
			//wizard.setModal(true);
			wizard.setVisible(true);
			
		} else {
			System.out.println("Error: can't recognize event.");
		}
	}

	@Override
	public void vmgDatabaseUpdateAvailable() {
		
		System.out.println("Refreshing the view...");
		
		//Process all VMGs:
		Visitor_UpdateContactView displayer = new Visitor_UpdateContactView();
		displayer.processVmgDatabase(AppContext.getInstance().getVmgModel().vmgDatabase);
		
		((ContactPanel)contactPanel).setContactList(displayer.contactNames);
		
		System.out.println("Done!");
	}

}