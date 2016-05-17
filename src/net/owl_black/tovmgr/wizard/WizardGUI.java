
/**
 * Copyright 2008  Eugene Creswick
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.owl_black.tovmgr.wizard;

import java.awt.Frame;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.UIManager;

import com.github.cjwizard.APageFactory;
import com.github.cjwizard.StackWizardSettings;
import com.github.cjwizard.WizardContainer;
import com.github.cjwizard.WizardListener;
import com.github.cjwizard.WizardPage;
import com.github.cjwizard.WizardSettings;
import com.github.cjwizard.pagetemplates.TitledPageTemplate;

import net.owl_black.tovmgr.AppContext;

/**
 * This demo class uses a JDialog to hold the wizard.
 * 
 * It show three pages: 1) You can enter a city name. 2) You must enter another
 * city that must be different to the first city. 3) It shows the last city
 * entered.
 * 
 * The city is name is saved in the settings with the same key so when you enter
 * a city name in the second page, you overwrite it.
 * 
 * @author rcreswick
 *
 */
public class WizardGUI extends JDialog {

	private static final long serialVersionUID = 1L;
	private WelcomePage welcomePage;
	private VmgBrowsingPage browsingPage;
	
	public static void main(String[] args) {
		// create the dialog, and show it:
		WizardGUI test = new WizardGUI(null, false);
		test.setVisible(true);
	}

	public WizardGUI(Frame owner, boolean modality) {
		super(owner, modality);
		
		//Set the windows like look (TODO: optionnal)
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		// first, build the wizard. The TestFactory defines the
		// wizard content and behavior.
		final WizardContainer wc = new WizardContainer(new VmgWizardFactory(), new TitledPageTemplate(),
				new StackWizardSettings());

		// add a wizard listener to update the dialog titles and notify the
		// surrounding application of the state of the wizard:
		wc.addWizardListener(new WizardListener() {
			@Override
			public void onCanceled(List<WizardPage> path, WizardSettings settings) {
				WizardGUI.this.dispose();
			}

			@Override
			public void onFinished(List<WizardPage> path, WizardSettings settings) {
				
				WizardGUI.this.dispose();
				
				//Update the model:
				ArrayList<File> inboxFiles = browsingPage.getInboxList();
				if( (inboxFiles != null) && (inboxFiles.size() != 0) ) {
					AppContext.getInstance().getVmgModel().setInboxFiles(inboxFiles);
				}
			}

			@Override
			public void onPageChanged(WizardPage newPage, List<WizardPage> path) {
				// Set the dialog title to match the description of the new
				// page:
				WizardGUI.this.setTitle(newPage.getDescription());
			}
		});

		// Set up the standard bookkeeping stuff for a dialog, and
		// add the wizard to the JDialog:
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.getContentPane().add(wc);
		
		//TODO: must be fixed
		//this.pack();
		this.setSize(400, 350);
	}


	/**
	 * Implementation of PageFactory to generate the wizard pages needed for the
	 * wizard.
	 */
	private class VmgWizardFactory extends APageFactory {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.github.cjwizard.PageFactory#createPage(java.util.List,
		 * com.github.cjwizard.WizardSettings)
		 */
		@Override
		public WizardPage createPage(List<WizardPage> path, WizardSettings settings) {

			// Get the next page to display. The path is the list of all wizard
			// pages that the user has proceeded through from the start of the
			// wizard, so we can easily see which step the user is on by taking
			// the length of the path. This makes it trivial to return the next
			// WizardPage:
			WizardPage page = buildPage(path.size(), settings);

			// if we wanted to, we could use the WizardSettings object like a
			// Map<String, Object> to change the flow of the wizard pages.
			// In fact, we can do arbitrarily complex computation to determine
			// the next wizard page.

			return page;
		}

		private WizardPage buildPage(int pageCount, final WizardSettings settings) {
			switch (pageCount) {
			case 0:
				welcomePage = new WelcomePage("Welcome to the import wizard", "VMG Wizard");
				return welcomePage;
			case 1:
				// You don't need to use anonymous classes.
				browsingPage = new VmgBrowsingPage(settings);
				return browsingPage;
			}
			return null;
		}

	}
}
