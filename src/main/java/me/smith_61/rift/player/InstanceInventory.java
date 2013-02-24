package me.smith_61.rift.player;

import org.bukkit.inventory.ItemStack;

/**
 * @author Jacob
 *
 * This is a really slimmed down version of a
 * 	player inventory. It returns copies of the
 * 	contained ItemStack to maintain Inventory
 * 	integrity since ItemStacks are not immutable.
 */
public interface InstanceInventory {

	/**
	 * Gets a copy of the item in the given slot or null
	 * 	if the slot is empty.
	 * 
	 * @param slot The slot of the item
	 * 
	 * @return A copy of the item in the slot or null if it empty
	 */
	public ItemStack getItem(int slot);
	
	/**
	 * Sets the slot to the given item. A null value makes the
	 * 	slot empty.
	 * 
	 * @param slot The slot to put the stack
	 * @param stack The stack to put in the slot
	 */
	public void setItem(int slot, ItemStack stack);
}
