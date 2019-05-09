package me.Allogeneous.ADEKP;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.Essentials;

public class ADEKPMain extends JavaPlugin{
	
	@Override
	public void onEnable() {
		if(Bukkit.getPluginManager().isPluginEnabled("Essentials")) {
			Essentials essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
			Bukkit.getLogger().log(Level.INFO, "[ADEKP] Essentials has been found!");
			ADEKPCommandListener adekpCommandListener = new ADEKPCommandListener(essentials); 
			this.getCommand("ekitpreview").setExecutor(adekpCommandListener);
			
			ADEKPTabCompleter adekptTabCompleter = new ADEKPTabCompleter(essentials);
			this.getCommand("ekitpreview").setTabCompleter(adekptTabCompleter);
			
			ADEKPEventHandler adekpEventHandler = new ADEKPEventHandler(this, essentials);
			Bukkit.getPluginManager().registerEvents(adekpEventHandler, this);
		}else {
			Bukkit.getPluginManager().disablePlugin(this);
			Bukkit.getLogger().log(Level.WARNING, "[ADEKP] Essentials is not enabled, disabling plugin!");
		}
		
		
	}
	
	@Override
	public void onDisable() {
		Bukkit.getLogger().log(Level.INFO, "[ADEKP] Has been diabled!");
	}
	
	

}
