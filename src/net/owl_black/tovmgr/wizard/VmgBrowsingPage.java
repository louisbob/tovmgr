package net.owl_black.tovmgr.wizard;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.github.cjwizard.WizardPage;
import com.github.cjwizard.WizardSettings;

import net.owl_black.tovmgr.FileDrop;
import net.owl_black.tovmgr.WizardFileBrowser;

/**
 * This private class implement a WizardPage that show the previous city
 * and also validate that the user enter a new city that is different
 * before going to the next page.
 * 
 */
public class VmgBrowsingPage extends WizardPage {

		/**
		* 
		*/
		private static final long serialVersionUID = 1L;
		
		/* Data */
		ArrayList<File> inboxList;
		ArrayList<File> outboxList;
		
		/* Graphical Resources */
		WizardFileBrowser fileDropPanel;

		/**
		 * Create a new ValidationPage that shows the previous city name and
		 * force you to enter a different city name.
		 * 
		 * @param settings
		 *            THe current settings to get the previous city name.
		 */
		public VmgBrowsingPage(WizardSettings settings) {
			// We set the title and the description of the page.
			super("Browse VMGs files...", "VMG Wizard");
			
			inboxList = new ArrayList<File>();
			outboxList = new ArrayList<File>();

			// get a value out of the settings, and display it on the second
			// page:
			this.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			
			//Welcome text
			c.anchor = GridBagConstraints.NORTH;
	  	    c.fill = GridBagConstraints.HORIZONTAL;
	  	    c.gridwidth = GridBagConstraints.RELATIVE;
	  	    c.gridheight = GridBagConstraints.RELATIVE;
	  	    c.gridx = 0;
			c.gridy = 1;
			c.ipady = 0;
			c.insets = new Insets(0, 10, 0, 10);
			c.weightx = 1.0;
			c.weighty = 0.0;
			
			JTextPane  explaination = new JTextPane ();
			
			explaination.setText("Drag & Drop the files you want to add or select browse. You can either select");
			
			StyledDocument doc = explaination.getStyledDocument();
			SimpleAttributeSet center = new SimpleAttributeSet();
			StyleConstants.setAlignment(center, StyleConstants.ALIGN_JUSTIFIED);
			doc.setParagraphAttributes(0, doc.getLength(), center, false);
			explaination.setEditable(false);
			explaination.setOpaque(false);
			
			this.add(explaination, c);
			
			//Add drag & drop files
			c.anchor = GridBagConstraints.NORTH;
	  	    c.fill = GridBagConstraints.HORIZONTAL;
	  	    c.gridwidth = GridBagConstraints.REMAINDER;
	  	    c.gridheight = GridBagConstraints.REMAINDER;
	  	    c.gridx = 0;
			c.gridy = 2;
			c.ipady = 0;
			c.insets = new Insets(10, 10, 10, 10);
			c.weightx = 1.0;
			c.weighty = 1.0;
			
			fileDropPanel = new WizardFileBrowser(dropListener);
			
			this.add(fileDropPanel, c);
			
			//For DEBUG
			//this.setBorder(BorderFactory.createLineBorder(Color.BLUE));
			//explaination.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
		}

		/**
		 * Check if the previous city name is the same of the current page.
		 * 
		 * Also, if the cities are the same it show a dialog telling it.
		 * 
		 * @see com.github.cjwizard.WizardPage#onNext(com.github.cjwizard.WizardSettings)
		 * 
		 * @return true if they are different (so the page can change) or
		 *         false if they are different (so the wizard don't advance
		 *         to next page).
		 * 
		 */
		@Override
		public boolean onNext(WizardSettings settings) {

			/*
			// Check if the current city is equal to the previous.
			if (settings.get("city").equals(this.field.getText())) {

				// Show a message (but do it in with the gui thread.
				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {
						JOptionPane.showMessageDialog(ValidationPage.this,
								"You have specified the same city than before!", "Same city!",
								JOptionPane.ERROR_MESSAGE);
					}
				});

				return false;

			} else {

				return true;

			}*/
			return true;
		}
		
		/**
		 * This is the last page in the wizard, so we will enable
		 * the finish button and disable the "Next >" button just
		 * before the page is displayed:
		 */
		public void rendering(List<WizardPage> path, WizardSettings settings) {
			super.rendering(path, settings);
			setFinishEnabled(true);
			setNextEnabled(false);
		}
		
		// Callback when files are drop into the specified zone.
		private FileDrop.Listener dropListener = new FileDrop.Listener() {
			public void filesDropped(java.io.File[] files) {
				//TODO: correct the model of the programme: we need to separate according to MVC
				//Add files to the current list:
				inboxList.addAll(Arrays.asList(files));
				fileDropPanel.setNumberOfFiles(inboxList.size());
				
				for (int i = 0; i < files.length; i++) {
					try {
						System.out.println(files[i].getCanonicalPath());
					} catch (java.io.IOException e) {
					}
				}
			}
		};
		
		//Getter andn Setter
		public ArrayList<File> getInboxList() {
			return inboxList;
		}

		public ArrayList<File> getOutboxList() {
			return outboxList;
		}
}
