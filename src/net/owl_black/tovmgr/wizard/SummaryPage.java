package net.owl_black.tovmgr.wizard;

import java.util.List;

import javax.swing.JLabel;

import com.github.cjwizard.WizardPage;
import com.github.cjwizard.WizardSettings;

public class SummaryPage extends WizardPage{

	private static final long serialVersionUID = 1L;

	public SummaryPage(String title, String description) {
		super(title, description);
		// TODO Auto-generated constructor stub
		add(new JLabel("The city you entered was: "));
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
}
