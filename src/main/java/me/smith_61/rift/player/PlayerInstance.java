package me.smith_61.rift.player;

import me.smith_61.rift.worldgroup.WorldGroup;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.PlayerInventory;

public interface PlayerInstance {

	public abstract OfflinePlayer getPlayer();
	
	public abstract WorldGroup getGroup();
	
	public abstract PlayerInventory getInventory();
	
	
	// Getters
	public abstract int getHealth();
	
	public abstract int getXP();
	
	public abstract int getFoodLevel();
	
	public abstract float getSaturation();
	
	
	//Setters
	public abstract void setHealth(int health);
	
	public abstract void setXP(int xp);
	
	public abstract void setFoodLevel(int foodlvl);
	
	public abstract void setSaturation(float saturation);
}
