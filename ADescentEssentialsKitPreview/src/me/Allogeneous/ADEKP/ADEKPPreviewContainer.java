package me.Allogeneous.ADEKP;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class ADEKPPreviewContainer {
	
	private final Inventory previewInventory;

	public ADEKPPreviewContainer(String kitName, ArrayList<ItemStack> items) {

		previewInventory = Bukkit.createInventory(null, 54, ChatColor.GOLD + "ADEKP Previewing: " + ChatColor.RED + kitName);
		
		int size = items.size();
		
		if(previewInventory.getSize() < size) {
			size = previewInventory.getSize();
		}
		
		for(int i = 0; i < size; i++) {
			previewInventory.setItem(i, items.get(i));
		}
	}
	
	public Inventory getPreviewInventry() {
		return previewInventory;
	}

}
