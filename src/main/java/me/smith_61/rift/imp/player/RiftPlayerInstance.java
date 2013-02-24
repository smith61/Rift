package me.smith_61.rift.imp.player;

import java.lang.ref.WeakReference;

import me.smith_61.rift.player.InstanceInventory;
import me.smith_61.rift.player.PlayerInstance;
import me.smith_61.rift.worldgroup.WorldGroup;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class RiftPlayerInstance implements PlayerInstance {

	
	private RiftPlayerManager manager;
	
	private OfflinePlayer player;
	private WorldGroup group;
	
	private RiftInstanceInventory inventory;
	
	//Using a WeakReference allows the playerData to be reclaimed 
	private WeakReference<RiftPlayerData> playerData;
	
	/*     Start Implementation Specific Methods     */
	
	protected RiftPlayerInstance(RiftPlayerManager manager, OfflinePlayer player, WorldGroup group) {
		this.manager = manager;
		this.player = player;
		this.group = group;
		
		this.playerData = new WeakReference<RiftPlayerData>(manager.getPlayerData(player, group));
		
		this.inventory = new RiftInstanceInventory(this);
	}
	
	protected RiftPlayerData getPlayerData() {
		RiftPlayerData data = this.playerData.get();
		if(data == null) {
			data = this.manager.getPlayerData(this.player, this.group);
			this.playerData = new WeakReference<RiftPlayerData>(data);
		}
		return data;
	}
	
	protected boolean isLoaded() {
		Player player = this.getPlayerInstance();
		return player != null && this.group.containsWorld(player.getWorld());
	}
	
	protected Player getPlayerInstance() {
		return this.player.getPlayer();
	}
	
	@Override
	public int hashCode() {
		return this.getPlayer().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof RiftPlayerInstance)) {
			return false;
		}
		RiftPlayerInstance instance = (RiftPlayerInstance)obj;
		
		return instance.getPlayerData() == this.getPlayerData();
	}
	
	
	/*     End Implementation Specific Methods     */
	
	
	
	/*     Start Interface Methods     */
	

	public OfflinePlayer getPlayer() {
		return this.player;
	}

	public WorldGroup getGroup() {
		return this.group;
	}

	public InstanceInventory getInventory() {
		return this.inventory;
	}

	public int getHealth() {
		if(this.isLoaded()) {
			return this.getPlayerInstance().getHealth();
		}
		else {
			return this.getPlayerData().getHealth();
		}
	}

	public int getXP() {
		if(this.isLoaded()) {
			return this.getPlayerInstance().getTotalExperience();
		}
		else {
			return this.getPlayerData().getXp();
		}
	}

	public int getFoodLevel() {
		if(this.isLoaded()) {
			return this.getPlayerInstance().getFoodLevel();
		}
		else {
			return this.getPlayerData().getFoodLevel();
		}
	}
	
	public float getSaturation() {
		if(this.isLoaded()) {
			return this.getPlayerInstance().getSaturation();
		}
		else {
			return this.getPlayerData().getSaturation();
		}
	}

	public void setHealth(int health) {
		if(this.isLoaded()) {
			this.getPlayerInstance().setHealth(health);
		}
		else {
			this.getPlayerData().setHealth(health);
		}
	}

	public void setXP(int xp) {
		if(this.isLoaded()) {
			this.getPlayerInstance().setTotalExperience(xp);
		}
		else {
			this.getPlayerData().setXp(xp);
		}
	}

	public void setFoodLevel(int foodlvl) {
		if(this.isLoaded()) {
			this.getPlayerInstance().setFoodLevel(foodlvl);
		}
		else {
			this.getPlayerData().setFoodLevel(foodlvl);
		}
	}
	
	public void setSaturation(float saturation) {
		if(this.isLoaded()) {
			this.getPlayerInstance().setSaturation(saturation);
		}
		else {
			this.getPlayerData().setSaturation(saturation);
		}
	}

	/*     End Interface Methods     */
}
