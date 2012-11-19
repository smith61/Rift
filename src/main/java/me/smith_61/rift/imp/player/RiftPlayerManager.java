package me.smith_61.rift.imp.player;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.smith_61.rift.player.PlayerManager;
import me.smith_61.rift.worldgroup.WorldGroup;
import me.smith_61.rift.worldgroup.WorldGroupManager;

import org.bukkit.OfflinePlayer;

public class RiftPlayerManager implements PlayerManager {

	private WorldGroupManager groupManager;
	
	private List<RiftPlayerData> dirtyData;
	
	private Map<String, SoftReference<RiftPlayerData>> playerData;
	
	/*     Start Implementation specific methods     */
	
	public RiftPlayerManager(WorldGroupManager groupManager) {
		this.groupManager = groupManager;
	}
	
	protected void markDirty(RiftPlayerData data) {
		dirtyData.add(data);
	}
	
	protected RiftPlayerData getPlayerData(OfflinePlayer player, WorldGroup group) {
		return this.playerData.get(player.getName() + ":" + group.getName()).get();
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
