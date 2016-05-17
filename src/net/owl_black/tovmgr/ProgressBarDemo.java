package net.owl_black.tovmgr;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import net.owl_black.tovmgr.VMGController.ProgressInfo;

import java.beans.*;
 
public class ProgressBarDemo extends JDialog
                             implements ActionListener, 
                                        PropertyChangeListener {
 
	private static final long serialVersionUID = 1L;
	
	private JProgressBar progressBar;
    private SwingWorker<Void, Void> inTask;
    private JLabel lbl_fileCount;
    private JLabel lbl_fileName;
    private JButton btn_cancel;

	

	public ProgressBarDemo(JFrame iOwner, SwingWorker<Void, Void> iTask) {
		super(iOwner, "Parsing VMG files...", true); 
       
		inTask = iTask;
		
		//Center the dialog in the middle of the parent frame
		setLocationRelativeTo(iOwner);
		
		//Instantiate all UI resources.
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        
        lbl_fileCount = new JLabel("<<FILECOUNT>>");
        lbl_fileName = new JLabel("<<FILENAME>>");
        
        lbl_fileCount.setAlignmentX(CENTER_ALIGNMENT);
        lbl_fileName.setAlignmentX(CENTER_ALIGNMENT);
        
        btn_cancel = new JButton("Cancel");
        btn_cancel.setAlignmentX(CENTER_ALIGNMENT);
        btn_cancel.addActionListener(this);
        
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        panel.add(lbl_fileCount);
        panel.add(Box.createVerticalStrut(5));
        panel.add(lbl_fileName);
        panel.add(Box.createVerticalStrut(10));
        panel.add(progressBar);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btn_cancel);

        add(panel, BorderLayout.CENTER);
        pack();
        
        //Register this dialog to the task property events.
        iTask.addPropertyChangeListener(this);
        
        //Add a waiting cursor.
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }
 
    /**
     * Invoked when the user presses the cancel button.
     */
    public void actionPerformed(ActionEvent evt) {
    	
    	if(evt.getSource() == btn_cancel)
    	{
    		inTask.cancel(true);
    		this.setVisible(false);
        	this.dispose();
    	}
    }
 
    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
    	String vEventName = evt.getPropertyName();
    	
        if ("progress" == vEventName) 
        {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
        }
        else if("progress_info" == vEventName)
        {
        	ProgressInfo vPinfo = (ProgressInfo) evt.getNewValue();
        	
        	lbl_fileCount.setText("Processing file " 
        	+ vPinfo.current_file_nb + "/" + vPinfo.total_file_nb);
        	
        	lbl_fileName.setText(vPinfo.filename);
        }
        else if("finished" == vEventName)
        {
        	this.setVisible(false);
        	this.dispose();
        }
    }
}