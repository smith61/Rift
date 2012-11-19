package me.smith_61.rift.worldgroup;

import org.bukkit.World;

public interface WorldGroupManager {

	public abstract WorldGroup getDefaultGroup();
	
	public abstract WorldGroup getGroup(String name);
	
	public abstract WorldGroup getGroup(World world);
	
	public abstract WorldGroup[] getGroups();
	
	public abstract WorldGroup createGroup(String name);
	
	public abstract WorldGroup createGroup(String name, World[] worlds);
	
	public abstract void deleteGroup(WorldGroup group);
}
