package net.owl_black.tovmgr;

public interface ModelChangedListener {
	public void inboxFilesChanged();
	public void outboxFilesChanged();
	public void vmgDatabaseChanged();
}
