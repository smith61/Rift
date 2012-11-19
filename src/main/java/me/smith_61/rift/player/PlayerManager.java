package me.smith_61.rift.player;

import me.smith_61.rift.worldgroup.WorldGroup;

import org.bukkit.OfflinePlayer;

public interface PlayerManager {

	public abstract PlayerInstance[] getPlayerInstances(OfflinePlayer player);
	
	public abstract PlayerInstance getPlayerInstance(OfflinePlayer player, WorldGroup group);
	
	
}
