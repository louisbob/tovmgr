package net.owl_black.tovmgr.wizard;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.github.cjwizard.WizardPage;

public class WelcomePage extends WizardPage {

	private static final long serialVersionUID = 1L;

	public WelcomePage(String title, String description) {
		super(title, description);
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Welcome text
		JLabel welcome = new JLabel("Select importing policy");
		welcome.setHorizontalAlignment(JLabel.CENTER);
		
		c.anchor = GridBagConstraints.NORTH;
  	    c.fill = GridBagConstraints.HORIZONTAL;
  	    c.gridwidth = 1;
  	    c.gridx = 0;
		c.gridy = 0;
		c.ipady = 5;
		c.weightx = 1.0;
		c.weighty = 1.0;
		
		this.add(welcome, c);
		
		//Explaination text
		JTextPane  explaination = new JTextPane ();
		explaination.setText("Before importing a VMG file, you have to set if you want to differenciate "
						+ "incoming and outcoming messages (OUTBOX vs INBOX). "
						+ "This step is important because it will help the VMG reader to"
						+ " provide a fancy \"android-conversation-like\" interface.\n");
		
		StyledDocument doc = explaination.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_JUSTIFIED);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		explaination.setEditable(false);
		explaination.setOpaque(false);
		explaination.setPreferredSize(new Dimension(100, 200));
		
		c.anchor = GridBagConstraints.NORTH;
  	    c.fill = GridBagConstraints.HORIZONTAL;
  	    c.gridwidth = GridBagConstraints.RELATIVE;
  	    c.gridx = 0;
		c.gridy = 1;
		c.ipady = 5;
		c.insets = new Insets(0, 10, 0, 10);
		c.weightx = 0.0;
		c.weighty = 1.0;
		
		this.add(explaination, c);
		
		//Inbox/Outbox checkbox
		JCheckBox needDifferenciation = new JCheckBox("Separate Inbox/Outbox messages.");
		
		c.anchor = GridBagConstraints.NORTH;
  	    c.fill = GridBagConstraints.HORIZONTAL;
  	    c.gridwidth = GridBagConstraints.REMAINDER;
  	    c.gridx = 0;
		c.gridy = 2;
		c.ipady = 5;
		c.insets = new Insets(0, 10, 0, 10);
		c.weightx = 1.0;
		c.weighty = 1.0;
		
		this.add(needDifferenciation, c);
	}

}
