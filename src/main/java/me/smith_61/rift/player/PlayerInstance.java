package me.smith_61.rift.player;

import me.smith_61.rift.worldgroup.WorldGroup;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.PlayerInventory;

/**
 * @author Jacob
 *
 * Represents an instance of a player for a specific
 *  WorldGroup. The player does not have to be online
 *  in order to be modified. Other plugins do not need
 *  to worry about a player changing groups or logging
 *  on while an isntance is being modified. It will be
 *  seemlessly handled by the plugin.
 */
public interface PlayerInstance {

	/**
	 * Gets the player this instance is for. The player does
	 * 	not have to be online.
	 * 
	 * @return The player this instance is for
	 */
	public abstract OfflinePlayer getPlayer();
	
	/**
	 * Gets the WorldGroup this instance is for.
	 * 
	 * @return The WorldGroup this instance is for
	 */
	public abstract WorldGroup getGroup();
	
	/**
	 * Gets the inventory of this instance.
	 * 
	 * @return The inventory of this instance
	 */
	public abstract InstanceInventory getInventory();
	
	
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
