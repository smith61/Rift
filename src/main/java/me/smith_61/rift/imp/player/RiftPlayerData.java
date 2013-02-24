package me.smith_61.rift.imp.player;

import me.smith_61.rift.worldgroup.WorldGroup;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class RiftPlayerData {

	private RiftPlayerManager instance;
	private OfflinePlayer player;
	private WorldGroup group;
	
	private boolean isDirty;
	
	private int health;
	private int xp;
	private int foodLevel;
	private float saturation;
	
	private ItemStack[] playerInventory = new ItemStack[InventoryType.PLAYER.getDefaultSize()];
	private ItemStack[] armorInventory = new ItemStack[4];
	
	protected RiftPlayerData(RiftPlayerManager instance, OfflinePlayer player, WorldGroup group) {
		this.instance = instance;
		this.player = player;
		this.group = group;
		
		this.isDirty = false;
		
		this.health = 0;
		this.xp = 0;
		this.foodLevel = 0;
		this.saturation = 0;
	}
	
	public OfflinePlayer getPlayer() {
		return this.player;
	}
	
	public WorldGroup getGroup() {
		return this.group;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.markDirty(true);
		
		this.health = health;
	}
	
	public int getXp() {
		return xp;
	}
	
	public void setXp(int xp) {
		this.markDirty(true);
		
		this.xp = xp;
	}
	
	public int getFoodLevel() {
		return foodLevel;
	}
	
	public void setFoodLevel(int foodlvl) {
		this.markDirty(true);
		
		this.foodLevel = foodlvl;
	}

	public float getSaturation() {
		return saturation;
	}

	public void setSaturation(float saturation) {
		this.markDirty(true);
		
		this.saturation = saturation;
	}
	
	public ItemStack getItem(int slot) {
		ItemStack[] contents = this.playerInventory;
		if(slot >= contents.length) {
			slot -= contents.length;
			contents = this.armorInventory;
		}
		return contents[slot];
	}
	
	public void setItem(int slot, ItemStack stack) {
		this.markDirty(true);
		
		ItemStack[] contents = this.playerInventory;
		if(slot >= contents.length) {
			slot -= contents.length;
			contents = this.armorInventory;
		}
		contents[slot] = stack;
	}
	
	public void copyTo(Player player) {
		player.setHealth(this.getHealth());
		player.setFoodLevel(this.getFoodLevel());
		player.setSaturation(this.getSaturation());
		player.setTotalExperience(this.getXp());
		
		PlayerInventory inv = player.getInventory();
		inv.setContents(this.playerInventory);
		inv.setArmorContents(this.armorInventory);
	}
	
	public void copyFrom(Player player) {
		this.setHealth(player.getHealth());
		this.setFoodLevel(player.getFoodLevel());
		this.setSaturation(player.getSaturation());
		this.setXp(player.getTotalExperience());
		
		PlayerInventory inv = player.getInventory();
		for(int slot = 0; slot < inv.getSize() + 4; slot++) {
			ItemStack item = inv.getItem(slot);
			if(item != null) {
				item = new ItemStack(item);
			}
			this.setItem(slot, item);
		}
	}
	
	public int getInventorySize() {
		return this.playerInventory.length + this.armorInventory.length;
	}
	
	private void markDirty(boolean dirty) {
		if(this.isDirty == dirty) {
			return;
		}
		else if(dirty) {
			instance.markDirty(this);
		}
		this.isDirty = dirty;
	}
}
