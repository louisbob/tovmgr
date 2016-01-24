package net.owl_black.tovmgr;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
 
public class ProgressBarDemo extends JDialog
                             implements ActionListener, 
                                        PropertyChangeListener {
 

	private static final long serialVersionUID = 1L;
	private JProgressBar progressBar;
    private JButton cancelButton;
    SwingWorker<Void, Void> task;
 
    public ProgressBarDemo() {
        super();     
        
        JPanel pan = new JPanel(new BorderLayout());
 
        //Create the demo's UI.
        cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("cancel");
        cancelButton.addActionListener(this);
 
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
 
        JPanel panel = new JPanel();
        panel.add(cancelButton);
        panel.add(progressBar);
 
        pan.add(panel, BorderLayout.PAGE_START);
        pan.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        pan.setOpaque(true); //content panes must be opaque
    }
    
    public void setTask(SwingWorker<Void, Void> waitingTask) {
    	task = waitingTask;
    	task.addPropertyChangeListener(this);
    }
 
    /**
     * Invoked when the user presses the start button.
     */
    public void startProcessing() {
    	setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }
    
    public void actionPerformed(ActionEvent evt) {
        //TODO: button action
    	cancelButton.setEnabled(true);
    }
 
    /**
     * Invoked when task's progress property changes.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
        } 
    }
 
 
    /**
     * Create the GUI and show it. As with all GUI code, this must run
     * on the event-dispatching thread.
     */
    
    public static void createAndShowGUI(SwingWorker<Void, Void> waitingTask) {
        //Create and set up the window.
        JFrame frame = new JFrame("Processing VMG files...");
 
        //Create and set up the content pane.
        ProgressBarDemo newContentPane = new ProgressBarDemo();
        newContentPane.setTask(waitingTask);
        frame.setContentPane(newContentPane);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
    	/*
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });*/
    }
}