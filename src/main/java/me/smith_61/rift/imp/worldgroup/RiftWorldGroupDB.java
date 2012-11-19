package me.smith_61.rift.imp.worldgroup;

public interface RiftWorldGroupDB {

	public abstract void initialize();
	
	public abstract void close();
	
	public abstract RiftWorldGroup[] loadGroups();
	
	public abstract void saveGroups(RiftWorldGroup[] groups);
}
