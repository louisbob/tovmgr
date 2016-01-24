package net.owl_black.tovmgr;

import java.awt.Toolkit;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.SwingWorker;

import net.owl_black.vmgparser.VmgObj;
import net.owl_black.vmgparser.VmgParser;
import net.owl_black.vmgparser.VmgScanner;

public class VMGController implements ModelChangedListener {
	
	public VMGController() {
		//Handle null model
		AppContext ac = AppContext.getInstance();
		ac.getVmgModel().addModelChangedListener(this);
		
		//vmgModel.addModelChangedListener(this);
	}
	@Override
	public void inboxFilesChanged() {
		// TODO Auto-generated method stub
		System.out.println("Inbox files changed!");
		
		//1. Create a thread
		ProcessVmgTask vmgTask = new ProcessVmgTask();
		
		//1. display popup for computing
		//ProgressBarDemo.createAndShowGUI(vmgTask);
		
		//2. parse VMG
		vmgTask.execute();
		
		//3. update the model
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
	}
	
	class ProcessVmgTask extends SwingWorker<Void, Void> {
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            //Initialize progress property.
            setProgress(0);
            
            //Get data:
            ArrayList<File> inboxFiles = AppContext.getInstance().getVmgModel().getInboxFiles();
            ArrayList<VmgObj> vmgList = new ArrayList<VmgObj>();
            
            for (File f : inboxFiles) {
    			try {
    				System.out.print("Parsing " + f.getName() + "...");
    				File fil = new File("D:\\louisbob\\programming\\Resources\\smspapa\\+33608898623_Bonjo_001.vmg");
					VmgParser parser = new VmgParser(fil, VmgScanner.UTF8);//TODO: detect encoding
					vmgList.add(
							parser.vmg_object(true));
					
					System.out.print("Done!\n");
					
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
            
            System.out.println("Ended!");
            
            /*
            while (progress < 100) {
                //Sleep for up to one second.
            	
                
                //Make random progress.
                progress += random.nextInt(10);
                setProgress(Math.min(progress, 100));
            }*/
            return null;
        }
 
        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            System.out.println("Processing finished!");
        }
    }

	
}
