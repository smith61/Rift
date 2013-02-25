package me.smith_61.rift.imp.commands;

import me.smith_61.rift.imp.RiftPlugin;

import org.bukkit.command.CommandSender;

import se.ranzdo.bukkit.methodcommand.Arg;
import se.ranzdo.bukkit.methodcommand.Command;

public class RiftCommands {

	public RiftCommands(RiftPlugin plugin) {
	}
	
	@Command(identifier = "rift debug", onlyPlayers = false, description = "Turns Rift debugging on or off.")
	public void enableDebugging(CommandSender sender, @Arg(name = "debug", def = "false")boolean debug) {
		sender.sendMessage("Debugging " + (debug ? "enabled" : "disabled"));
			
		RiftPlugin.getPlugin().setDebugging(debug);
	}
}
