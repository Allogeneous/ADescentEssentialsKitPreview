package me.Allogeneous.ADEKP;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.earth2me.essentials.Essentials;


public class ADEKPCommandListener implements CommandExecutor{
	
	private final ADEKPMain plugin;
	private ADEKPEssentialsKitDeconstructor kitDeconstructor;
	
	public ADEKPCommandListener(ADEKPMain plugin, Essentials essentialsInstance) {
		this.plugin = plugin;
		kitDeconstructor = new ADEKPEssentialsKitDeconstructor(plugin, essentialsInstance);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if(commandLabel.equalsIgnoreCase("adekpreload")){
			if(sender.hasPermission("adekp.kitpreviewreloadconfig")) {
				plugin.reloadConfigFile();
				if(sender instanceof Player) {
					sender.sendMessage("[ADEKP] config file reloaded!");
				}
			}else {
				sender.sendMessage(plugin.getMessageManager().translateADEKPMessageSyntax(plugin.getMessageManager().getCannotUseCommand(), "", "", ""));
			}
		}
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
							player.sendMessage(plugin.getMessageManager().translateADEKPMessageSyntax(plugin.getMessageManager().getStartChatList(), kitName, "", ""));
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
							player.sendMessage(plugin.getMessageManager().translateADEKPMessageSyntax(plugin.getMessageManager().getEndChatList(), kitName, "", ""));
						}
						
					}
				}else {
					player.sendMessage(plugin.getMessageManager().translateADEKPMessageSyntax(plugin.getMessageManager().getCannotUseCommand(), "", "", ""));
				}
			}else {
				sender.sendMessage(plugin.getMessageManager().translateADEKPMessageSyntax(plugin.getMessageManager().getIsNotAPlayer(), "", "", ""));
			}
			
		}
		return true;
	}

}
