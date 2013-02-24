package me.smith_61.rift.imp.player;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import me.smith_61.rift.imp.RiftPlugin;
import me.smith_61.rift.player.PlayerManager;
import me.smith_61.rift.worldgroup.WorldGroup;
import me.smith_61.rift.worldgroup.WorldGroupManager;

import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class RiftPlayerManager implements PlayerManager {

	private RiftPlugin plugin;
	private WorldGroupManager groupManager;
	private int taskID;
	
	private List<RiftPlayerData> dirtyData;
	
	private RiftPlayerDataDB database;
	private Map<String, SoftReference<RiftPlayerData>> playerData;
	
	/*     Start Implementation specific methods     */
	
	public RiftPlayerManager(RiftPlugin plugin, WorldGroupManager groupManager) {
		this.plugin = plugin;
		this.groupManager = groupManager;
		
		this.dirtyData = new ArrayList<RiftPlayerData>();
		this.playerData = new HashMap<String, SoftReference<RiftPlayerData>>();
	}
	
	public void enable() {
		ConfigurationSection section = this.plugin.getConfig().getConfigurationSection("Player");
		if(section == null) {
			section = this.plugin.getConfig().createSection("Player");
		}
		
		String dbType = section.getString("Database", "yaml");
		if(dbType.equals("yaml")) {
			this.database = new RiftPlayerDataDBYAML(plugin);
		}
		else {
			throw new RuntimeException("Bad Player Database type: " + dbType);
		}
		
		this.database.open();
		
		this.taskID = this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
			
			public void run() {
				RiftPlayerManager.this.flush();
			}
		}, 0L, section.getLong("AutoSave", 5) * 60 * 20);
		
		RiftPlayerListener listener = new RiftPlayerListener(this.plugin, this, this.groupManager);
		this.plugin.getServer().getPluginManager().registerEvents(listener, this.plugin);
		
		//We need to add any players that are currently on the server. As the plugin may while the server is running
		for(World world : this.plugin.getServer().getWorlds()) {
			for(Player player : world.getPlayers()) {
				listener.handlePlayerChangeGroup(player, null, this.groupManager.getGroup(player.getWorld()));
			}
		}
	}
	
	public void disable() {
		this.plugin.getServer().getScheduler().cancelTask(this.taskID);
		
		this.flush();
		this.database.close();
		
		this.playerData.clear();
	}
	
	protected void flush() {
		for(RiftPlayerData dirty : this.dirtyData) {
			this.database.saveData(dirty.getPlayer(), dirty.getGroup(), dirty);
		}
		this.dirtyData.clear();
	}
	
	protected void markDirty(RiftPlayerData data) {
		dirtyData.add(data);
	}
	
	protected RiftPlayerData getPlayerData(OfflinePlayer player, WorldGroup group) {
		SoftReference<RiftPlayerData> ref = this.playerData.get(player.getName().toLowerCase() + ":" + group.getName().toLowerCase());
		RiftPlayerData data = null;
		
		if(ref == null || (data = ref.get()) == null) {
			this.plugin.getLogger().log(Level.INFO, String.format("Cache miss for Player: %s for Group: %s.", player.getName(), group.getName()));
			data = this.database.loadData(this, player, group);
			
			this.playerData.put(player.getName().toLowerCase() + ":" + group.getName().toLowerCase(), new SoftReference<RiftPlayerData>(data));
		}
		
		return data;
	}
	
	/*     End Implementation specific methods     */
	
	
	
	
	/*     Start Interface methods     */
	
	
	public RiftPlayerInstance[] getPlayerInstances(OfflinePlayer player) {
		List<RiftPlayerInstance> instances = new ArrayList<RiftPlayerInstance>();
		for(WorldGroup group : this.groupManager.getGroups()) {
			instances.add(this.getPlayerInstance(player, group));
		}
		
		return instances.toArray(new RiftPlayerInstance[instances.size()]);
	}

	
	public RiftPlayerInstance getPlayerInstance(OfflinePlayer player, WorldGroup group) {
		return new RiftPlayerInstance(this, player, group);
	}
	
	
	/*     End Interface Methods     */
}
