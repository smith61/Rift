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
		this.playerManager.disable();
		this.playerManager = null;
		
		this.groupManager.disable();
		this.groupManager = null;
	}

	@Override
	public void onEnable() {
		this.getDataFolder().mkdirs();
		
		this.groupManager = new RiftWorldGroupManager(this);
		this.groupManager.enable();
		
		this.playerManager = new RiftPlayerManager(this, groupManager);
		this.playerManager.enable();
		
		for(World world : this.getServer().getWorlds()) {
			System.out.println(String.format("World: %s is in WorldGroup: %s.", world.getName(), this.groupManager.getGroup(world).getName()));
		}
	}

	
}
