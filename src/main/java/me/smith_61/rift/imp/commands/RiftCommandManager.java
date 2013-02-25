package me.smith_61.rift.imp.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import se.ranzdo.bukkit.methodcommand.CommandHandler;

import me.smith_61.rift.imp.RiftPlugin;

public class RiftCommandManager {

	private static final List<Class<?>> commandClasses;
	private static CommandHandler handler;
	
	static {
		commandClasses = new ArrayList<Class<?>>();
		
		commandClasses.add(RiftCommands.class);
	}
	
	public static void registerCommands(RiftPlugin plugin) {
		handler = new CommandHandler(plugin);
		handler.registerArgumentHandler(boolean.class, new BooleanArgumentHandler());
		
		for(Class<?> clazz : commandClasses) {
			plugin.getLogger().log(Level.INFO, String.format("Adding commands from Class: %s", clazz.getName()));
			Object command = registerCommand(plugin, clazz);
			if(command != null) {
				handler.registerCommands(command);
			}
			else {
				plugin.getLogger().log(Level.WARNING, String.format("Failed to create instance of Class: %s.", clazz.getName()));
			}
		}
	}
	
	private static Object registerCommand(RiftPlugin plugin, Class<?> clazz) {
		try {
			return clazz.getConstructor(RiftPlugin.class).newInstance(plugin);
		}
		catch (Throwable t) {
			plugin.getLogger().log(Level.WARNING, String.format("Error creating command: %s.", clazz), t);
		}
		return null;
	}
}
