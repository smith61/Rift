package me.smith_61.rift.imp;

import java.util.logging.Level;

import me.smith_61.rift.imp.commands.RiftCommandManager;
import me.smith_61.rift.imp.player.RiftPlayerManager;
import me.smith_61.rift.imp.worldgroup.RiftWorldGroupManager;

import org.bukkit.plugin.java.JavaPlugin;

public class RiftPlugin extends JavaPlugin {

	private static RiftPlugin instance;
	
	private RiftWorldGroupManager groupManager;
	private RiftPlayerManager playerManager;
	
	public static final RiftPlugin getPlugin() {
		return instance;
	}
	
	
	@Override
	public void onDisable() {
		this.playerManager.disable();
		this.playerManager = null;
		
		this.groupManager.disable();
		this.groupManager = null;
		
		this.saveConfig();
	}

	@Override
	public void onEnable() {
		instance = this;
		
		this.getDataFolder().mkdirs();
		ConfigValue.loadConfig(this.getConfig());
		
		this.setDebugging(ConfigValue.DEBUG.getBoolean());
		
		this.groupManager = new RiftWorldGroupManager(this);
		this.groupManager.enable();
		
		this.playerManager = new RiftPlayerManager(this, groupManager);
		this.playerManager.enable();
		
		RiftCommandManager.registerCommands(this);
	}
	
	public void setDebugging(boolean debug) {
		if(debug) {
			this.getLogger().setLevel(Level.ALL);
		}
		else {
			this.getLogger().setLevel(Level.WARNING);
		}
		
		this.getConfig().set("Debug", debug);
	}
}
