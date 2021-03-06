package me.smith_61.rift.events;

import me.smith_61.rift.worldgroup.WorldGroup;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Jacob
 * 
 * This Event is fired whenever a player changes
 * 	between two WorldGroups.
 * 
 * On Player login the from group will be null
 * 	and on player logout the to group will be null
 */
public class PlayerChangeGroupEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

	
	private Player player;
	
	private WorldGroup from;
	private WorldGroup to;

	public PlayerChangeGroupEvent(Player player, WorldGroup from, WorldGroup to) {
		this.player = player;
		this.from = from;
		this.to = to;
	}

	public Player getPlayer() {
		return this.player;
	}

	public WorldGroup getFrom() {
		return this.from;
	}

	public WorldGroup getTo() {
		return this.to;
	}
}
