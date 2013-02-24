package me.smith_61.rift.worldgroup;

import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

/**
 * @author Jacob
 *
 * A WorldGroup is a group of worlds. Each world will
 * 	only be in one group and will never be found in
 * 	multiple groups.
 */
public interface WorldGroup {

	/**
	 * Gets the name of the group.
	 * 
	 * @return The name of the group
	 */
	public abstract String getName();
	
	/**
	 * Gets a copy of all worlds in this group.
	 * 
	 * @return A copy of all worlds in this group
	 */
	public abstract World[] getWorlds();
	
	/**
	 * Adds a world to this group. If the world is
	 * 	not already a part of this group it will
	 * 	fire a WorldChangeGroupEvent and a
	 * 	PlayerChangeGroupEvent for any player in
	 * 	the world.
	 * 
	 * @param world The world to add
	 */
	public abstract void addWorld(World world);
	
	/**
	 * Adds multiple worlds to a group. This is
	 * 	a convenience method that wraps around
	 * 	addWorld(World) and therefore fires the
	 * 	same events.
	 * 
	 * @param worlds The worlds to add
	 */
	public abstract void addWorlds(World[] worlds);
	
	/**
	 * Gets whether this world is a part of this
	 * 	group.
	 * 
	 * @param world The world
	 * 
	 * @return If the world is a part of this group
	 */
	public abstract boolean containsWorld(World world);
	
	/**
	 * Gets the persistent storage of the plugin for this WorldGroup
	 * 
	 * @param plugin The plugin
	 * 
	 * @return The persistent storage of this plugin for this WorldGroup
	 */
	public abstract ConfigurationSection getPersistentStorage(Plugin plugin);
}
