package me.Allogeneous.ADEKP;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.earth2me.essentials.Essentials;

import net.md_5.bungee.api.ChatColor;


public class ADEKPCommandListener implements CommandExecutor{
	
	private ADEKPEssentialsKitDeconstructor kitDeconstructor;
	
	public ADEKPCommandListener(Essentials essentialsInstance) {
		kitDeconstructor = new ADEKPEssentialsKitDeconstructor(essentialsInstance);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if(commandLabel.equalsIgnoreCase("ekitpreview") || commandLabel.equalsIgnoreCase("ekp") || commandLabel.equalsIgnoreCase("epk") || commandLabel.equalsIgnoreCase("epreviewkit") || commandLabel.equalsIgnoreCase("eshowkit")) {
			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(player.hasPermission("adekp.kitpreview")) {
					if(args.length == 1) {
						String kitName = args[0];
						ArrayList<ItemStack> kitItems = kitDeconstructor.getItemsFromKit(player, kitName);
						ArrayList<String> kitCommands = kitDeconstructor.getCommandsFromKit(player, kitName);
						ArrayList<String> kitMoney = kitDeconstructor.getMoneyFromKit(player, kitName);
						if(!kitItems.isEmpty()) {
							ADEKPPreviewContainer adekpPreviewContainer = new ADEKPPreviewContainer(kitName, kitItems);
							player.openInventory(adekpPreviewContainer.getPreviewInventry());
						}
					
						if((player.hasPermission("adekp.kitpreviewmoney") && !kitMoney.isEmpty()) || (player.hasPermission("adekp.kitpreviewcommands") && !kitCommands.isEmpty())) {
							player.sendMessage(ChatColor.GOLD + "[ADEKP] Additional items in the " + ChatColor.RED + kitName + ChatColor.GOLD +" kit!");
						}
							
						
						if(!kitCommands.isEmpty() && player.hasPermission("adekp.kitpreviewcommands")) {
							for(String commandString : kitCommands) {
								player.sendMessage(commandString);
							}
						}
						if(!kitMoney.isEmpty() && player.hasPermission("adekp.kitpreviewmoney")) {
							for(String moneyString : kitMoney) {
								player.sendMessage(moneyString);
							}
						}
						
						if((player.hasPermission("adekp.kitpreviewmoney") && !kitMoney.isEmpty()) || (player.hasPermission("adekp.kitpreviewcommands") && !kitCommands.isEmpty())) {
							player.sendMessage(ChatColor.GOLD + "[ADEKP] End of additional items in the " + ChatColor.RED + kitName + ChatColor.GOLD +" kit!");
						}
						
					}
				}else {
					player.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
				}
			}else {
				sender.sendMessage("You must be a Player to send this command.");
			}
			
		}
		return true;
	}

}
