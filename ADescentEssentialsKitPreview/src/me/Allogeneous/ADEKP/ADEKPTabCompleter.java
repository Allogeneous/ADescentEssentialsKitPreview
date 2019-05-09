package me.Allogeneous.ADEKP;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.earth2me.essentials.Essentials;

public class ADEKPTabCompleter implements TabCompleter{
	
	private final Essentials essentialsInstance;
	
	public ADEKPTabCompleter(Essentials essentialsInstance) {
		this.essentialsInstance = essentialsInstance;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String commandLable, String[] args) {
		if(command.getName().equalsIgnoreCase("ekitpreview")){
			
			if(args.length == 1){
				try {
					String kits = this.essentialsInstance.getKits().listKits(essentialsInstance, null);
					String[] kitList = kits.toLowerCase(Locale.ENGLISH).split(" ");
					return Arrays.asList(kitList);
				} catch (Exception e) {}
			}
		}
		return null;
	}

}
