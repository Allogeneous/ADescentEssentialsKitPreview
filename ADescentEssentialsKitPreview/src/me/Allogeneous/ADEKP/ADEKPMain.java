package me.Allogeneous.ADEKP;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.Essentials;

public class ADEKPMain extends JavaPlugin{
	
	private ADEKPChatMessageManager messageManager;
	
	@Override
	public void onEnable() {
		if(Bukkit.getPluginManager().isPluginEnabled("Essentials")) {
			
			createConfig();
			verifyConfigVersion();
			
			String[] msgs = new String[8];
			
			msgs[0] = getConfig().getString("tag", "&6[ADEKP]");
			msgs[1] = getConfig().getString("startChatList", "{tag} Additional items int the &c{kitname}&6 kit!");
			msgs[2] = getConfig().getString("endChatList", "{tag} End of additional items int the &c{kitname}&6 kit!");
			msgs[3] = getConfig().getString("moneyAddMsg", "&6- &c{money}&6 will be added to your account!");
			msgs[4] = getConfig().getString("commandAddMsg", "&6- The command: &c{command}&6 will be run!");
			msgs[5] = getConfig().getString("cannotUseCommand", "&4You do not have access to that command.");
			msgs[6] = getConfig().getString("cannoutUseSign", "&4You cannot interact with that!");
			msgs[7] = getConfig().getString("isNotAPlayer", "You must be a Player to send this command.");
			
			messageManager = new ADEKPChatMessageManager(msgs);
			
			Essentials essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
			getLogger().info("Essentials has been found!");
			ADEKPCommandListener adekpCommandListener = new ADEKPCommandListener(this, essentials); 
			this.getCommand("ekitpreview").setExecutor(adekpCommandListener);
			this.getCommand("adekpreload").setExecutor(adekpCommandListener);
			
			ADEKPTabCompleter adekptTabCompleter = new ADEKPTabCompleter(essentials);
			this.getCommand("ekitpreview").setTabCompleter(adekptTabCompleter);
			
			ADEKPEventHandler adekpEventHandler = new ADEKPEventHandler(this, essentials);
			Bukkit.getPluginManager().registerEvents(adekpEventHandler, this);
		}else {
			Bukkit.getPluginManager().disablePlugin(this);
			getLogger().info("Essentials is not enabled, disabling plugin!");
		}
		
		
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Has been diabled!");
	}
	
	
	private void createConfig(){
		   try{
		      if (!getDataFolder().exists()) {
		        getDataFolder().mkdirs();
		      }
		      File file = new File(getDataFolder(), "config.yml");
		      if (!file.exists()){
		        saveDefaultConfig();
		      }
		    }
		    catch (Exception ex) {}
		  }
	
	private void verifyConfigVersion(){
		if(!getConfig().getString("version", "").equals("1.0.1")){
			getLogger().info("Invalid config file found, creating a new one and copying the old one...");
			try{
			      File file = new File(getDataFolder(), "config.yml");
			      File lastConfig = new File(getDataFolder(), "last_config.yml");
			      if (file.exists()){
			    	if(lastConfig.exists()){
			    		lastConfig.delete();
			    	}
			    	file.renameTo(lastConfig);
			        file.delete();
			        saveDefaultConfig();
			        getLogger().info("Config files updated!");
			      }
			    }
			    catch (Exception ex) {
			    	getLogger().info("Something went wrong creating the new config file!");
			    }  
			}
		}

	protected void reloadConfigFile() {
		createConfig();
		reloadConfig();

		verifyConfigVersion();
		
		String[] msgs = new String[8];
		
		msgs[0] = getConfig().getString("tag", "&6[ADEKP]");
		msgs[1] = getConfig().getString("startChatList", "{tag} Additional items int the &c{kitname}&6 kit!");
		msgs[2] = getConfig().getString("endChatList", "{tag} End of additional items int the &c{kitname}&6 kit!");
		msgs[3] = getConfig().getString("moneyAddMsg", "&6- &c{money}&6 will be added to your account!");
		msgs[4] = getConfig().getString("commandAddMsg", "&6- The command: &c{command}&6 will be run!");
		msgs[5] = getConfig().getString("cannotUseCommand", "&4You do not have access to that command.");
		msgs[6] = getConfig().getString("cannoutUseSign", "&4You cannot interact with that!");
		msgs[7] = getConfig().getString("isNotAPlayer", "You must be a Player to send this command.");
		
		messageManager = new ADEKPChatMessageManager(msgs);
		getLogger().info("config file reloaded!");
	}
	
	public ADEKPChatMessageManager getMessageManager() {
		return messageManager;
	}

}
