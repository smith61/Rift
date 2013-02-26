package me.smith_61.rift.imp;

import java.io.File;
import java.net.URLClassLoader;
import java.util.logging.Level;

import me.smith_61.rift.imp.commands.RiftCommandManager;
import me.smith_61.rift.imp.player.RiftPlayerManager;
import me.smith_61.rift.imp.worldgroup.RiftWorldGroupManager;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.plugin.java.PluginClassLoader;

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
		
		if(ConfigValue.DEBUG.getBoolean()) {
			runIntegrationTests();
		}
	}
	
	@Override
	public void saveConfig() {
		ConfigValue.saveConfig(this.getConfig());
		
		super.saveConfig();
	}
	
	public void setDebugging(boolean debug) {
		if(debug) {
			this.getLogger().setLevel(Level.ALL);
		}
		else {
			this.getLogger().setLevel(Level.WARNING);
		}
		ConfigValue.DEBUG.setValue(debug);
	}
	
	//This is used in conjunction with Rift-Test to run jUnit tests in a bukkit environment
	private void runIntegrationTests() {
		try {
			File integrationFile = new File(this.getDataFolder(), "Rift-Test.jar");
			if(!integrationFile.exists() || integrationFile.isDirectory()) {
				return;
			}
			else {
				((PluginClassLoader)this.getClass().getClassLoader()).addURL(integrationFile.toURI().toURL());
			}
			Class<?> integrationTest = Class.forName("me.smith_61.rift.imp.RiftTest");
			
			Runnable runnable = (Runnable)integrationTest.getConstructor(RiftPlugin.class, File.class).newInstance(this, integrationFile);
			
			runnable.run();
		}
		catch(Throwable ignore) {}
	}
}
