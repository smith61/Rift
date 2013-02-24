package me.smith_61.rift.imp.worldgroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.smith_61.rift.imp.RiftPlugin;
import me.smith_61.rift.worldgroup.WorldGroup;
import me.smith_61.rift.worldgroup.WorldGroupManager;

import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

public class RiftWorldGroupManager implements WorldGroupManager {

	private RiftPlugin pluginInstance;
	
	private RiftWorldGroupDB worldGroupDB;
	
	private List<RiftWorldGroup> worldGroups;
	
	
	/*     Start Implementation Specific Methods     */
	
	
	public RiftWorldGroupManager(RiftPlugin instance) {
		this.pluginInstance = instance;
	}
	
	
	public void enable() {
		ConfigurationSection section = this.pluginInstance.getConfig().getConfigurationSection("WorldGroup");
		if(section == null) {
			section = this.pluginInstance.getConfig().createSection("WorldGroup");
		}
		
		String dbName = section.getString("Database", "yaml");
		
		if(dbName.equalsIgnoreCase("yaml")) {
			this.worldGroupDB = new RiftWorldGroupDBYAML(this.pluginInstance);
		}
		else {
			throw new RuntimeException("Bad WorldGroup DB Type: " + dbName);
		}
		
		this.worldGroupDB.initialize();
		
		this.worldGroups = new ArrayList<RiftWorldGroup>();
		this.worldGroupDB.loadGroups(this);
		
		//Ensure a default group exists
		this.createGroup("Default");
		
		//Verify all worlds are in a group. If not add them to the default group.
		for(World world : this.pluginInstance.getServer().getWorlds()) {
			if(this.getGroup(world) == null) {
				this.getDefaultGroup().addWorld(world);
			}
		}
	}
	
	public void disable() {
		this.worldGroupDB.saveGroups(this);
		this.worldGroupDB.close();
	}
	
	/*     End Implementation Specific Methods     */
	
	
	
	/*     Start Interface Methods     */
	
	public RiftWorldGroup getDefaultGroup() {
		return this.getGroup("Default");
	}
	
	public RiftWorldGroup getGroup(String name) {
		for(RiftWorldGroup group : this.worldGroups) {
			if(group.getName().equalsIgnoreCase(name)) {
				return group;
			}
		}
		return null;
	}

	public RiftWorldGroup getGroup(World world) {
		for(RiftWorldGroup group : this.worldGroups) {
			if(group.containsWorld(world)) {
				return group;
			}
		}
		
		//The only time it should reach here is during initialization
		return null;
	}

	public RiftWorldGroup[] getGroups() {
		return this.worldGroups.toArray(new RiftWorldGroup[this.worldGroups.size()]);
	}

	public RiftWorldGroup createGroup(String name) {
		RiftWorldGroup group = this.getGroup(name);
		if(group == null) {
			group = new RiftWorldGroup(this, name);
			this.worldGroups.add(group);
		}
		return group;
	}

	public RiftWorldGroup createGroup(String name, World[] worlds) {
		RiftWorldGroup group = this.createGroup(name);
		group.addWorlds(worlds);
		
		return group;
	}

	public void deleteGroup(WorldGroup group) {
		if(group.getName().equals("Default")) {
			return;
		}
		else {
			this.getDefaultGroup().addWorlds(group.getWorlds());
			this.worldGroups.remove(group);
		}
	}

	/*     End Interface Methods     */
}
