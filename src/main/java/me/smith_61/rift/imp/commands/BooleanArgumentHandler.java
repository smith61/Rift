package me.smith_61.rift.imp.commands;

import org.bukkit.command.CommandSender;

import se.ranzdo.bukkit.methodcommand.ArgumentHandler;
import se.ranzdo.bukkit.methodcommand.CommandArgument;
import se.ranzdo.bukkit.methodcommand.TransformError;

public class BooleanArgumentHandler extends ArgumentHandler<Boolean> {

	public BooleanArgumentHandler() {
		this.setMessage("parse_error", "[%1] is not a valid option. The parameter [%p] must be either true or false.");
	}
	
	@Override
	public Boolean transform(CommandSender sender, CommandArgument argument, String value) throws TransformError {
		value = value.toLowerCase();
		if(value.equals("true")) {
			return Boolean.TRUE;
		}
		else if(value.equals("false")) {
			return Boolean.FALSE;
		}
		else {
			throw new TransformError(argument.getMessage("parse_error", value));
		}
	}

}
