package me.smith_61.rift.imp.player;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class RiftPlayerData {

	private RiftPlayerManager instance;
	private boolean isDirty;
	
	private int health;
	private int xp;
	private int foodLevel;
	private float saturation;
	
	private ItemStack[] playerInventory = new ItemStack[InventoryType.PLAYER.getDefaultSize()];
	
	
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
		return this.playerInventory[slot];
	}
	
	public void setItem(int slot, ItemStack stack) {
		this.markDirty(true);
		
		this.playerInventory[slot] = stack;
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
