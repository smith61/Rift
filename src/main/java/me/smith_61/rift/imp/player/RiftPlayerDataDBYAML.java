package me.smith_61.rift.imp.player;

import java.io.File;
import java.io.IOException;

import me.smith_61.rift.imp.RiftPlugin;
import me.smith_61.rift.worldgroup.WorldGroup;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class RiftPlayerDataDBYAML implements RiftPlayerDataDB {

	private RiftPlugin plugin;
	
	private File yamlDir;
	
	private ConfigurationSection defaults;
	
	protected RiftPlayerDataDBYAML(RiftPlugin plugin) {
		this.plugin = plugin;
		
		this.yamlDir = new File(plugin.getDataFolder(), "players");
	}
	
	public void open() {
		this.yamlDir.mkdirs();
		
		this.defaults = YamlConfiguration.loadConfiguration(this.plugin.getResource("defaults/PlayerData.yml"));
	}

	public void close() {
	}

	public RiftPlayerData loadData(RiftPlayerManager manager, OfflinePlayer player, WorldGroup group) {
		File yamlFile = new File(this.yamlDir, player.getName().toLowerCase() + ".yml");
		ConfigurationSection section = null;
		if(yamlFile.exists()) {
			section = YamlConfiguration.loadConfiguration(yamlFile).getConfigurationSection(group.getName());
		}
		if(section == null) {
			//If the section does not exist the defaults exist in in the WorldGroup persistence storage
			section = group.getPersistentStorage(this.plugin);
		}
		
		//Add any defaults if they do not already exist in the configuration section
		setDefaults(section);
		
		RiftPlayerData data = new RiftPlayerData(manager, player, group);
		data.setHealth(section.getInt("health"));
		data.setFoodLevel(section.getInt("foodlevel"));
		data.setSaturation((float) section.getDouble("saturation"));
		data.setXp(section.getInt("xp"));
		
		ConfigurationSection items = section.getConfigurationSection("items");
		if(items != null) {
			for(String key : items.getKeys(false)) {
				int slot = Integer.parseInt(key);
				
				data.setItem(slot, items.getItemStack(key));
			}
		}
		
		return data;
	}

	public void saveData(OfflinePlayer player, WorldGroup group, RiftPlayerData data) {
		File yamlFile = new File(this.yamlDir, player.getName().toLowerCase() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(yamlFile);
		
		ConfigurationSection section = config.getConfigurationSection(group.getName());
		if(section == null) {
			section = config.createSection(group.getName());
		}
		
		section.set("health", data.getHealth());
		section.set("foodlevel", data.getFoodLevel());
		section.set("saturation", data.getSaturation());
		section.set("xp", data.getXp());
		
		ConfigurationSection items = section.createSection("items");
		for(int i=0; i<data.getInventorySize(); i++) {
			ItemStack stack = data.getItem(i);
			if(stack != null) {
				items.set(Integer.toString(i), stack);
			}
		}
		
		try {
			config.save(yamlFile);
		}
		catch (IOException e) {
		}
	}
	
	private void setDefaults(ConfigurationSection section) {
		for(String key : this.defaults.getKeys(true)) {
			if(!section.contains(key)) {
				section.set(key, this.defaults.get(key));
			}
		}
	}

}
