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
	
	/*     Start Implementation Specific Methods     */
	
	
	protected RiftWorldGroupDBYAML(RiftPlugin plugin) {
		this.yamlFile = new File(plugin.getDataFolder(), "WorldGroups.yml");
	}
	
	
	/*     End Implementation Specific Methods     */
	
	
	/*     Start Interface Methods     */
	
	
	public void initialize() {
		this.config = YamlConfiguration.loadConfiguration(yamlFile);
	}

	public void close() {
		try {
			this.config.save(this.yamlFile);
		} catch (IOException ignore) {
		}
	}

	public void loadGroups(RiftWorldGroupManager manager) {
		Server server = Bukkit.getServer();
		Set<World> usedWorlds = new HashSet<World>();
		
		for(String key : this.config.getKeys(false)) {
			if(this.config.isConfigurationSection(key)) {
				ConfigurationSection section = this.config.getConfigurationSection(key);
				
				RiftWorldGroup group = manager.createGroup(key);
				
				for(String worldName : section.getStringList("Worlds")) {
					World world = server.getWorld(worldName);
					if(world != null && !usedWorlds.contains(world)) {
						group.addWorld(world);
						usedWorlds.add(world);
					}
				}
			}
		}
	}

	public void saveGroups(RiftWorldGroupManager manager) {
		for(RiftWorldGroup group : manager.getGroups()) {
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
