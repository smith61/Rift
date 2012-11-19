package me.smith_61.rift.imp.worldgroup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.smith_61.rift.imp.RiftPlugin;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class RiftWorldGroupDBYAML implements RiftWorldGroupDB {
	
	private File yamlFile;
	
	private YamlConfiguration config;
	
	private RiftWorldGroupManager groupManager;
	
	/*     Start Implementation Specific Methods     */
	
	
	protected RiftWorldGroupDBYAML(RiftPlugin plugin, RiftWorldGroupManager manager) {
		this.yamlFile = new File(plugin.getDataFolder(), "WorldGroups.yml");
		
		this.groupManager = manager;
	}
	
	
	/*     End Implementation Specific Methods     */
	
	
	/*     Start Interface Methods     */
	
	
	public void initialize() {
		this.config = YamlConfiguration.loadConfiguration(yamlFile);
	}

	public void close() {
		try {
			this.config.save(this.yamlFile);
		} catch (IOException e) {
			//Ignore
		}
	}

	public RiftWorldGroup[] loadGroups() {
		Server server = Bukkit.getServer();
		Set<World> usedWorlds = new HashSet<World>();
		
		List<RiftWorldGroup> groups = new ArrayList<RiftWorldGroup>();
		
		for(String key : this.config.getKeys(false)) {
			if(this.config.isConfigurationSection(key)) {
				ConfigurationSection section = this.config.getConfigurationSection(key);
				
				List<String> worldNames = section.getStringList("Worlds");
				List<World> worlds = new ArrayList<World>();
				
				for(String worldName : worldNames) {
					World world = server.getWorld(worldName);
					if(world != null && !usedWorlds.contains(world)) {
						worlds.add(world);
						usedWorlds.add(world);
					}
				}
				
				RiftWorldGroup group = new RiftWorldGroup(this.groupManager, key, worlds.toArray(new World[worlds.size()]));
				
				groups.add(group);
			}
		}
		
		return groups.toArray(new RiftWorldGroup[groups.size()]);
	}

	public void saveGroups(RiftWorldGroup[] groups) {
		for(RiftWorldGroup group : groups) {
			ConfigurationSection section = this.config.createSection(group.getName());
			
			List<String> worldNames = new ArrayList<String>();
			
			for(World world : group.getWorlds()) {
				worldNames.add(world.getName());
			}
			
			section.set("Worlds", worldNames);
		}
	}

	
	/*     End Interface Methods     */
}
