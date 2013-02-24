package me.smith_61.rift.player;

import me.smith_61.rift.worldgroup.WorldGroup;

import org.bukkit.OfflinePlayer;

/**
 * @author Jacob
 *
 * This class manages PlayerInstances.
 */
public interface PlayerManager {

	/**
	 * Returns all created PlayerInstances for the given player. This may
	 * 	not return a PlayerInstance for every WorldGroup.
	 * 
	 * @param player The Player
	 * 
	 * @return All PlayerInstances for the Player
	 */
	public abstract PlayerInstance[] getPlayerInstances(OfflinePlayer player);
	
	/**
	 * Gets the PlayerInstance for the given Player and WorldGroup. The
	 * 	returned instance may be unique every call. Meaning two consecutive
	 * 	calls may return two different PlayerInstances. Changes made in one
	 * 	will always be visible in any other PlayerInstance for the same player
	 * 	and group.
	 * 
	 * @param player The Player
	 * @param group The WorldGroup
	 * 
	 * @return The PlayerInstance for the player and worldgroup
	 */
	public abstract PlayerInstance getPlayerInstance(OfflinePlayer player, WorldGroup group);
	
	
}
