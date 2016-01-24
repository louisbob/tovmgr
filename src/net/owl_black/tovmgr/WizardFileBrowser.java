package net.owl_black.tovmgr;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class WizardFileBrowser extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;

	/* Graphical resources. */
	JLabel dragHere;
	JButton btnBrowse;
	JButton btnList;

	public static void main(String[] args) {

		JFrame frame = new JFrame("WizardFileBrowser test");
		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(new Color(238, 238, 238));

		final WizardFileBrowser fileBrowser = new WizardFileBrowser(new FileDrop.Listener() {
			public void filesDropped(java.io.File[] files) {
				for (int i = 0; i < files.length; i++) {
					try {
						System.out.println(files[i].getCanonicalPath());
					} catch (java.io.IOException e) {
					}
				}
			}
		});
		
		frame.add(fileBrowser);
		frame.setVisible(true);
		frame.revalidate();
	}

	public void setNumberOfFiles(int numberOfFiles) {
		String fileWord = numberOfFiles > 1 ? " files" : " file";

		dragHere.setText("<html><font size=+2>Drag & Drop files here</font><br><br>" + "<center><font color=gray><i>"
				+ numberOfFiles + fileWord + " added.</i></font><br></html>");

	}

	public WizardFileBrowser(FileDrop.Listener dropListener) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		dragHere = new JLabel();
		this.setNumberOfFiles(0);
		dragHere.setHorizontalAlignment(SwingConstants.CENTER);
		dragHere.setAlignmentX(CENTER_ALIGNMENT);
		dragHere.setBorder(BorderFactory.createDashedBorder(Color.GRAY, 3, 2, 3, false));
		dragHere.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		dragHere.setPreferredSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

		this.add(dragHere);

		// Add space
		this.add(Box.createRigidArea(new Dimension(10, 10)));

		// Add button
		btnList = new JButton("Edit file-list...");
		btnList.addActionListener(this);
		btnList.setAlignmentX(CENTER_ALIGNMENT);
		this.add(btnList);

		// Add space
		this.add(Box.createRigidArea(new Dimension(10, 10)));

		// Add button
		btnBrowse = new JButton("Browse...");
		btnBrowse.setAlignmentX(CENTER_ALIGNMENT);
		btnBrowse.addActionListener(this);
		this.add(btnBrowse);

		Border dragBorder = BorderFactory.createDashedBorder(new Color(15, 157, 88), 3, 2, 3, false);
		// Set drag & Drop TODO: add logging system.
		new FileDrop(null, dragHere, dragBorder, dropListener);

		// FOR DEBUG
		// this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	@Override
	public Dimension getMaximumSize() {
		return this.getPreferredSize();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		
		if(src == btnBrowse) {
			//Create a dialog to browse the VMGs files.
			VMGFileChooser vfc = new VMGFileChooser(null);
			vfc.openDiag();
		}
	};

}
