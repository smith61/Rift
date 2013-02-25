package me.smith_61.rift.imp;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.Configuration;

public final class ConfigValue<T> {

	public static final ConfigValue<Boolean> DEBUG = new ConfigValue<Boolean>("Debug", false);
	
	public static final ConfigValue<String> WORLDGROUP_DB = new ConfigValue<String>("WorldGroup.Database", "yaml");
	
	public static final ConfigValue<String> PLAYER_DB = new ConfigValue<String>("Player.Database", "yaml");
	public static final ConfigValue<Long> PLAYER_AUTOSAVE = new ConfigValue<Long>("Player.AutoSave", 5L);
	
	private static Map<String, ConfigValue<?>> values = new HashMap<String, ConfigValue<?>>();
	
	protected static void loadConfig(Configuration config) {
		for(Entry<String, ConfigValue<?>> entry : values.entrySet()) {
			if(!config.contains(entry.getKey())) {
				config.set(entry.getKey(), entry.getValue().getValue());
			}
			else {
				entry.getValue().value = (config.get(entry.getKey()));
			}
		}
	}
	
	private String node;
	private Object value;
	
	public ConfigValue(String node, T def) {
		if(values.containsKey(node)) {
			throw new IllegalArgumentException(String.format("Node already defined: %s.", node));
		}
		
		values.put(node, this);
		
		this.node = node;
		this.value = def;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public String getNode() {
		return this.node;
	}
	
	public T getValue() {
		return (T)this.value;
	}
	
	public int getInt() {
		if(this.value instanceof Integer) {
			return ((Integer)this.value).intValue();
		}
		else {
			return 0;
		}
	}
	
	public double getDouble() {
		if(this.value instanceof Double) {
			return ((Double)this.value).doubleValue();
		}
		else {
			return 0.0d;
		}
	}
	
	public long getLong() {
		if(this.value instanceof Long) {
			return ((Long)this.value).longValue();
		}
		else {
			return 0l;
		}
	}
	
	public boolean getBoolean() {
		if(this.value instanceof Boolean) {
			return ((Boolean)this.value).booleanValue();
		}
		else {
			return false;
		}
	}
	
	public String toString() {
		return this.node + " = " + this.value.toString();
	}
}
