package me.smith_61.rift.events;

import me.smith_61.rift.worldgroup.WorldGroup;

import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Jacob
 * 
 * This event is fired whenever a world has been moved
 * 	between WorldGroups.
 * 
 */
public class WorldChangeGroupEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	
	private World world;
	
	private WorldGroup from;
	private WorldGroup to;
	
	public WorldChangeGroupEvent(World world, WorldGroup from, WorldGroup to) {
		this.world = world;
		
		this.from = from;
		this.to = to;
	}

	
	public World getWorld() {
		return this.world;
	}
	
	public WorldGroup getFrom() {
		return this.from;
	}
	
	public WorldGroup getTo() {
		return this.to;
	}
}
