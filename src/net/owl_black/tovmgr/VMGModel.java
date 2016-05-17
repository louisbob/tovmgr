package net.owl_black.tovmgr;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import net.owl_black.vmgparser.VmgObj;

public class VMGModel {
	
	ArrayList<File> inboxFiles;
	ArrayList<File> outboxFiles;
	ArrayList<VmgObj> vmgDatabase;

	private final Collection<ModelChangedListener> listeners = new ArrayList<ModelChangedListener>();
	
	public VMGModel() {
		inboxFiles = new ArrayList<File>();
		outboxFiles = new ArrayList<File>();
		vmgDatabase = new ArrayList<VmgObj>();
	}
	
	public VMGModel(Collection<VmgObj> vmgCollection) {
		inboxFiles = new ArrayList<File>();
		outboxFiles = new ArrayList<File>();
		vmgDatabase = new ArrayList<VmgObj>(vmgCollection);
	}
	
	//Listener management
	public void addModelChangedListener(ModelChangedListener modelChangedListener) {
		listeners.add(modelChangedListener);
	}
	
	public void removeModelChangedListener(ModelChangedListener modelChangedListener) {
		listeners.remove(modelChangedListener);
	}
	
	private void fireInboxFilesChanged() {
		for (ModelChangedListener l : this.listeners) {
			l.inboxFilesChanged();
		}
	}
	
	private void fireOutboxFilesChanged() {
		for (ModelChangedListener l : this.listeners) {
			l.outboxFilesChanged();
		}
	}
	
	private void fireVmgDatabaseChanged() {
		for (ModelChangedListener l : this.listeners) {
			l.vmgDatabaseChanged();
		}
	}

	//Getter and Setter
	
	// DATABASE
	public ArrayList<VmgObj> getVmgDatabase() {
		return vmgDatabase;
	}

	public void setVmgDatabase(ArrayList<VmgObj> vmgDatabase) {
		this.vmgDatabase = vmgDatabase;
		fireVmgDatabaseChanged();
	}
	
	public void appendVmgDatabase(ArrayList<VmgObj> vmgDatabase) {
		this.vmgDatabase.addAll(vmgDatabase);
		fireVmgDatabaseChanged();
	}
	
	public void removeVmgDatabase(ArrayList<VmgObj> vmgDatabase) {
		this.vmgDatabase.removeAll(vmgDatabase); //TODO: check the behaviour of remove.
		fireVmgDatabaseChanged();
	}
	
	public void clearVmgDatabase() {
		this.vmgDatabase.clear();
		fireVmgDatabaseChanged();
	}
	
	// INBOX FILES
	public ArrayList<File> getInboxFiles() {
		return inboxFiles;
	}

	public void setInboxFiles(ArrayList<File> inboxFiles) {
		this.inboxFiles = null;
		this.inboxFiles = inboxFiles;
		fireInboxFilesChanged();
	}
	
	public void appendInboxFiles(ArrayList<File> inboxFiles) {
		this.inboxFiles.addAll(inboxFiles);
		fireInboxFilesChanged();
	}

	// OUTBOX FILES
	public ArrayList<File> getOutboxFiles() {
		return outboxFiles;
	}

	public void setOutboxFiles(ArrayList<File> outboxFiles) {
		this.outboxFiles = outboxFiles;
		fireOutboxFilesChanged();
	}

}
