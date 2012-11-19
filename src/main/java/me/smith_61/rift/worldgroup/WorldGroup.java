package me.smith_61.rift.worldgroup;

import org.bukkit.World;

public interface WorldGroup {

	public abstract String getName();
	
	public abstract World[] getWorlds();
	
	public abstract void addWorld(World world);
	
	public abstract void addWorlds(World[] worlds);
	
	public abstract boolean containsWorld(World world);
	
	
	
}
