package me.smith_61.rift.imp.worldgroup;

import java.util.ArrayList;
import java.util.List;

import me.smith_61.rift.events.WorldChangeGroupEvent;
import me.smith_61.rift.worldgroup.WorldGroup;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

public class RiftWorldGroup implements WorldGroup {

	
	private RiftWorldGroupManager groupManager;
	
	private String name;
	private List<World> worldsList;
	
	private ConfigurationSection persistentStorage;
	
	/*     Start Implementation Specific Methods     */
	
	protected RiftWorldGroup(RiftWorldGroupManager manager, String name) {
		this.groupManager = manager;
		
		this.name = name;
		
		this.worldsList = new ArrayList<World>();
	}
	
	//Called from RiftWorldGroupManager when a world is unloaded
	protected void removeWorld(World world) {
		if(this.containsWorld(world)) {
			this.worldsList.remove(world);
			
			//We still must fire an event about the world changing groups
			Bukkit.getServer().getPluginManager().callEvent(new WorldChangeGroupEvent(world, this, null));
		}
	}
	
	protected void setStorage(ConfigurationSection section) {
		if(this.persistentStorage == null) {
			this.persistentStorage = section;
		}
	}
	/*     End Implementation Specific Methods     */
	
	
	/*     Start Interface Methods     */
	
	
	public String getName() {
		return this.name;
	}

	public World[] getWorlds() {
		return this.worldsList.toArray(new World[this.worldsList.size()]);
	}

	public void addWorld(World world) {
		RiftWorldGroup oldGroup = this.groupManager.getGroup(world);
		if(oldGroup == this) {
			return;
		}
		
		this.worldsList.add(world);
		
		if(oldGroup != null) {
			oldGroup.worldsList.remove(world);
		}
		Bukkit.getServer().getPluginManager().callEvent(new WorldChangeGroupEvent(world, oldGroup, this));
	}

	public void addWorlds(World[] worlds) {
		for(World world : worlds) {
			this.addWorld(world);
		}
	}

	public boolean containsWorld(World world) {
		return this.worldsList.contains(world);
	}
	
	public ConfigurationSection getPersistentStorage(Plugin plugin) {
		ConfigurationSection section = this.persistentStorage.getConfigurationSection(plugin.getName());
		if(section == null) {
			section = this.persistentStorage.createSection(plugin.getName());
		}
		
		return section;
	}

	/*     End Interface Methods     */
}
