package net.owl_black.tovmgr;

import java.awt.Toolkit;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.SwingWorker;

import net.owl_black.vmgparser.VmgObj;
import net.owl_black.vmgparser.VmgParser;
import net.owl_black.vmgparser.VmgScanner;

public class VMGController implements ModelChangedListener {
	
	private final Collection<ViewInterface> listeners = new ArrayList<ViewInterface>();
	
	public VMGController() {
		//Handle null model
		AppContext ac = AppContext.getInstance();
		ac.getVmgModel().addModelChangedListener(this);
		
		//vmgModel.addModelChangedListener(this);
	}
	@Override
	public void inboxFilesChanged() 
	{
		// TODO Auto-generated method stub
		System.out.println("Inbox files changed!");
		
		//1. Create a vmg task.
		ProcessVmgTask vmgTask = new ProcessVmgTask();
		
		//2. Create a progress window for the user
		ProgressBarDemo progressWindow = new ProgressBarDemo(
				AppContext.getInstance().getCoreGUI(), vmgTask);
		
		//2. parse VMG
		vmgTask.execute();
				
		progressWindow.setVisible(true);
	}

	@Override
	public void outboxFilesChanged() {
		// TODO Auto-generated method stub
		System.out.println("Outbox files changed!");
		
	}

	@Override
	public void vmgDatabaseChanged() {
		// TODO Auto-generated method stub
		System.out.println("VmgDatabaseChanged  changed!");
		
		//1. update the view
		for (ViewInterface l : listeners) {
			l.vmgDatabaseUpdateAvailable();
		}
	}
	
	//Listener management
	public void addModelChangedListener(ViewInterface modelUpdateListener) {
		listeners.add(modelUpdateListener);
	}
	
	public void removeModelChangedListener(ViewInterface modelUpdateListener) {
		listeners.remove(modelUpdateListener);
	}
	
	public void fireModelUpdateOccured() {
		for (ViewInterface l : listeners) {
			l.vmgDatabaseUpdateAvailable();
		}
	}
	
	//Processing management
	class ProgressInfo
	{
		public ProgressInfo(String iFilename, int iFileNumber, int iTotalFiles) {
			total_file_nb = iTotalFiles;
			current_file_nb = iFileNumber;
			filename = iFilename;
		}
		
		public int total_file_nb;
		public int current_file_nb;
		public String filename;
	}
	
	class ProcessVmgTask extends SwingWorker<Void, Void> {
		ArrayList<VmgObj> vmgList;
		
        /*
         * Main task. Executed in background thread.
         */
		
        @Override
        public Void doInBackground() {
        	
            //Initialize progress bar stuff.
        	
            setProgress(0);
            
            //Get data:
            ArrayList<File> inboxFiles = AppContext.getInstance().getVmgModel().getInboxFiles();
            vmgList = new ArrayList<VmgObj>();
            
            //Set the progress for the progress dialog.
            int vTotalFiles = inboxFiles.size();
            int vFileCounter = 1;
            
            for (File f : inboxFiles) {
            	
            	if (Thread.currentThread().isInterrupted()) 
            	{
                    return null; //TODO: handle this correctly (freeing resources)
                }
            	
            	setProgress(vFileCounter*100/vTotalFiles);
            	
            	ProgressInfo vPinfo = new ProgressInfo(f.getName(), vFileCounter, vTotalFiles);
            	
            	firePropertyChange("progress_info", null , vPinfo);
            	
    			try 
    			{
    				System.out.print("Parsing " + f.getName() + "...");
					VmgParser parser = new VmgParser(f, VmgScanner.UTF8);//TODO: detect encoding
					vmgList.add(
							parser.vmg_object(true));
					
					System.out.print("Done!\n");
					
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			vFileCounter++;
			}
            
            System.out.println("Update the model...");
            AppContext.getInstance().getVmgModel().setVmgDatabase(vmgList);
            System.out.println("Ended!");
            
            return null;
        }
 
        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            
            if(this.isCancelled())
            {
            	System.out.println("Parsing cancelled!");
            }
            else
            {
            	firePropertyChange("finished", null , null);
            	System.out.println("Processing finished!");
            }
        }
    }

	
}
