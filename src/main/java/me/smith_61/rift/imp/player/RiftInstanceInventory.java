package me.smith_61.rift.imp.player;

import org.bukkit.inventory.ItemStack;

import me.smith_61.rift.player.InstanceInventory;

public class RiftInstanceInventory implements InstanceInventory {

	private RiftPlayerInstance instance;
	
	protected RiftInstanceInventory(RiftPlayerInstance instance) {
		this.instance = instance;
	}
	
	public ItemStack getItem(int slot) {
		ItemStack stack = null;
		if(this.instance.isLoaded()) {
			stack = this.instance.getPlayerInstance().getInventory().getItem(slot);
		}
		else {
			stack = this.instance.getPlayerData().getItem(slot);
		}
		if(stack != null) {
			stack = new ItemStack(stack);
		}
		
		return stack;
	}

	public void setItem(int slot, ItemStack stack) {
		if(this.instance.isLoaded()) {
			this.instance.getPlayerInstance().getInventory().setItem(slot, stack);
		}
		else {
			this.instance.getPlayerData().setItem(slot, stack);
		}
	}

}
