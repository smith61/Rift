package me.smith_61.rift.worldgroup;

import org.bukkit.World;

/**
 * @author Jacob
 *
 * This class is a manager for all worldgroups.
 * 
 */
public interface WorldGroupManager {

	/**
	 * Gets the default group that worlds will be
	 * 	added to if they are not already a part of
	 * 	a group.
	 * 
	 * @return The default group
	 */
	public abstract WorldGroup getDefaultGroup();
	
	/**
	 * Gets the group with the given name or null
	 * 	if no group exists.
	 * 
	 * @param name The name of the group
	 * 
	 * @return The group with the given name or null if one does not exist
	 */
	public abstract WorldGroup getGroup(String name);
	
	/**
	 * Gets the group that the world is a part of.
	 * 
	 * @param world The world
	 * 
	 * @return The group containing the world
	 */
	public abstract WorldGroup getGroup(World world);
	
	/**
	 * Gets all created groups.
	 * 
	 * @return All created groups
	 */
	public abstract WorldGroup[] getGroups();
	
	/**
	 * Gets a group with the specified name or creates
	 * 	one if one does not exist.
	 * 
	 * In order to recreate a group you must first delete
	 * 	the group then recreate it.
	 * 
	 * @param name The name of the group
	 * 
	 * @return The group with the given name
	 */
	public abstract WorldGroup createGroup(String name);
	
	/**
	 * Gets a group with the given name. If one already
	 * 	exists with the given name it will be returned.
	 * 	The worlds will not be added to it.
	 * 
	 * If one does not exist it will create it and add
	 * 	the worlds to it.
	 * 
	 * @param name The name of the group
	 * @param worlds The worlds to add to the group
	 * 
	 * @return The group with the given name
	 */
	public abstract WorldGroup createGroup(String name, World[] worlds);
	
	/**
	 * Deletes a group. All worlds contained in the group
	 * 	will be moved to the default group before the group
	 * 	is deleted.
	 * 
	 * @param group The group to delete
	 */
	public abstract void deleteGroup(WorldGroup group);
}
