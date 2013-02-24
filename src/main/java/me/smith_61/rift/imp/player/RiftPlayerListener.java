package me.smith_61.rift.imp.player;

import java.util.logging.Level;

import me.smith_61.rift.events.PlayerChangeGroupEvent;
import me.smith_61.rift.events.WorldChangeGroupEvent;
import me.smith_61.rift.imp.RiftPlugin;
import me.smith_61.rift.worldgroup.WorldGroup;
import me.smith_61.rift.worldgroup.WorldGroupManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class RiftPlayerListener implements Listener {

	private RiftPlugin plugin;
	
	private RiftPlayerManager playerManager;
	private WorldGroupManager groupManager;
	
	protected RiftPlayerListener(RiftPlugin plugin, RiftPlayerManager playerManager, WorldGroupManager groupManager) {
		this.plugin = plugin;
		
		this.playerManager = playerManager;
		this.groupManager = groupManager;
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		this.handlePlayerChangeGroup(event.getPlayer(), null, this.groupManager.getGroup(event.getPlayer().getWorld()));
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerQuit(PlayerQuitEvent event) {
		this.handlePlayerChangeGroup(event.getPlayer(), this.groupManager.getGroup(event.getPlayer().getWorld()), null);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		this.handlePlayerChangeGroup(event.getPlayer(), this.groupManager.getGroup(event.getFrom().getWorld()), this.groupManager.getGroup(event.getTo().getWorld()));
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerPortal(PlayerPortalEvent event) {
		this.handlePlayerChangeGroup(event.getPlayer(), this.groupManager.getGroup(event.getFrom().getWorld()), this.groupManager.getGroup(event.getTo().getWorld()));
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onWorldChangeGroup(WorldChangeGroupEvent event) {
		for(Player player : event.getWorld().getPlayers()) {
			this.handlePlayerChangeGroup(player, event.getFrom(), event.getTo());
		}
	}
	
	protected void handlePlayerChangeGroup(Player player, WorldGroup from, WorldGroup to) {
		if(from == to) {
			return;
		}
		
		String fromName = from != null ? from.getName() : null;
		String toName = to != null ? to.getName() : "null";
		this.plugin.getLogger().log(Level.INFO, String.format("Player changed group. From: %s. To: %s.", fromName, toName));
		if(from != null) {
			this.playerManager.getPlayerData(player, from).copyFrom(player);
		}
		if(to != null) {
			this.playerManager.getPlayerData(player, to).copyTo(player);
		}
		
		this.plugin.getServer().getPluginManager().callEvent(new PlayerChangeGroupEvent(player, from, to));
	}
}
