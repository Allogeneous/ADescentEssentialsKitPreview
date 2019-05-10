package me.Allogeneous.ADEKP;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.earth2me.essentials.Essentials;

import net.md_5.bungee.api.ChatColor;

public class ADEKPEventHandler implements Listener {

	private final ADEKPMain plugin;
	private final Essentials essentialsInstance;
	
	private ADEKPEssentialsKitDeconstructor kitDeconstructor;

	public ADEKPEventHandler(ADEKPMain plugin, Essentials essentialsInstance) {
		this.plugin = plugin;
		this.essentialsInstance = essentialsInstance;
		
		kitDeconstructor = new ADEKPEssentialsKitDeconstructor(plugin, this.essentialsInstance);
	}

	@EventHandler
	public void onSignCreate(SignChangeEvent event) {
		Player player = event.getPlayer();
		if (event.getLine(0).equalsIgnoreCase("[adekp]")) {
			if (player.hasPermission("adekp.kitpreviewcreatesign")) {
				event.setLine(0, ChatColor.GOLD + "=+=+=+=+=+=");
				event.setLine(2, event.getLine(1));
				event.setLine(1, ChatColor.RED + "Preview Kit:");
				event.setLine(3, ChatColor.GOLD + "=+=+=+=+=+=");
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onClickSign(PlayerInteractEvent event) {
		if(event.isCancelled()) {
			event.setCancelled(false);
		}
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (event.getClickedBlock().getState() instanceof Sign) {
				Player player = event.getPlayer();
				Sign sign = (Sign) event.getClickedBlock().getState();
				if (sign.getLine(0).equals(ChatColor.GOLD + "=+=+=+=+=+=") && sign.getLine(1).equals(ChatColor.RED + "Preview Kit:")) {
					if(event.getAction() == Action.LEFT_CLICK_BLOCK && player.hasPermission("adekp.kitpreviewcreatesign")) {
						return;
					}
					event.setCancelled(true);
					if (player.hasPermission("adekp.kitpreviewusesign")) {
						String kitName = sign.getLine(2);
						ArrayList<ItemStack> kitItems = kitDeconstructor.getItemsFromKit(player, kitName);
						ArrayList<String> kitCommands = kitDeconstructor.getCommandsFromKit(player, kitName);
						ArrayList<String> kitMoney = kitDeconstructor.getMoneyFromKit(player, kitName);
						if (!kitItems.isEmpty()) {
							ADEKPPreviewContainer adekpPreviewContainer = new ADEKPPreviewContainer(kitName, kitItems);
							player.openInventory(adekpPreviewContainer.getPreviewInventry());
						}

						if ((player.hasPermission("adekp.kitpreviewmoney") && !kitMoney.isEmpty()) || (player.hasPermission("adekp.kitpreviewcommands") && !kitCommands.isEmpty())) {
							player.sendMessage(plugin.getMessageManager().translateADEKPMessageSyntax(plugin.getMessageManager().getStartChatList(), kitName, "", ""));
						}

						if (!kitCommands.isEmpty() && player.hasPermission("adekp.kitpreviewcommands")) {
							for (String commandString : kitCommands) {
								player.sendMessage(commandString);
							}
						}
						if (!kitMoney.isEmpty() && player.hasPermission("adekp.kitpreviewmoney")) {
							for (String moneyString : kitMoney) {
								player.sendMessage(moneyString);
							}
						}

						if ((player.hasPermission("adekp.kitpreviewmoney") && !kitMoney.isEmpty()) || (player.hasPermission("adekp.kitpreviewcommands") && !kitCommands.isEmpty())) {
							player.sendMessage(plugin.getMessageManager().translateADEKPMessageSyntax(plugin.getMessageManager().getEndChatList(), kitName, "", ""));
						}

					} else {
						player.sendMessage(plugin.getMessageManager().translateADEKPMessageSyntax(plugin.getMessageManager().getCannotUseSign(), "", "", ""));
					}
				}
			}

		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Inventory inventory = event.getInventory();
		if (inventory.getName().startsWith(ChatColor.GOLD + "ADEKP Previewing: " + ChatColor.RED)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		Inventory inventory = event.getInventory();
		if (inventory.getName().startsWith(ChatColor.GOLD + "ADEKP Previewing: " + ChatColor.RED)) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					if (event.getPlayer() instanceof Player) {
						Player player = (Player) event.getPlayer();
						player.updateInventory();
					}
				}
			}, 1L);
		}
	}

}
