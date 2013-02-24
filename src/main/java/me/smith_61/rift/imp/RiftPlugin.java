package me.smith_61.rift.imp;

import me.smith_61.rift.imp.player.RiftPlayerManager;
import me.smith_61.rift.imp.worldgroup.RiftWorldGroupManager;

import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class RiftPlugin extends JavaPlugin {

	
	private RiftWorldGroupManager groupManager;
	private RiftPlayerManager playerManager;
	
	
	@Override
	public void onDisable() {
		this.groupManager.disable();
	}

	@Override
	public void onEnable() {
		this.getDataFolder().mkdirs();
		
		this.groupManager = new RiftWorldGroupManager(this);
		this.playerManager = new RiftPlayerManager(groupManager);
		
		this.groupManager.enable();
		
		for(World world : this.getServer().getWorlds()) {
			System.out.println(String.format("World: %s is in WorldGroup: %s.", world.getName(), this.groupManager.getGroup(world).getName()));
		}
	}

	
}
