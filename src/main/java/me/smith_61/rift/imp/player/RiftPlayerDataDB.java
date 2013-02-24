package me.smith_61.rift.imp.player;

import me.smith_61.rift.worldgroup.WorldGroup;

import org.bukkit.OfflinePlayer;

public interface RiftPlayerDataDB {
	
	public void open();
	
	public void close();
	
	
	public RiftPlayerData loadData(RiftPlayerManager manager, OfflinePlayer player, WorldGroup group);
	
	public void saveData(OfflinePlayer player, WorldGroup group, RiftPlayerData data);
}
