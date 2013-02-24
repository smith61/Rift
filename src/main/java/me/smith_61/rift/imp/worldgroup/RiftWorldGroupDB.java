package me.smith_61.rift.imp.worldgroup;

public interface RiftWorldGroupDB {

	public abstract void initialize();
	
	public abstract void close();
	
	public abstract void loadGroups(RiftWorldGroupManager manager);
	
	public abstract void saveGroups(RiftWorldGroupManager manager);
}
