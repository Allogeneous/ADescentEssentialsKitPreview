package me.Allogeneous.ADEKP;

import org.bukkit.ChatColor;

public class ADEKPChatMessageManager {
	
	private final String tagPlace = "{tag}";
	private final String kitNamePlace = "{kitname}";
	private final String moneyPlace = "{money}";
	private final String commandPlace = "{command}";
	
	
	private String tag;
	
	private String startChatList;
	private String endChatList;
	private String moneyAddMsg;
	private String commandAddMsg;
	
	private String cannotUseCommand;
	private String cannotUseSign;
	private String isNotAPlayer;
	
	public ADEKPChatMessageManager(String[] configMessages) {
		tag = configMessages[0];
		startChatList = configMessages[1];
		endChatList = configMessages[2];
		moneyAddMsg = configMessages[3];
		commandAddMsg = configMessages[4];
		cannotUseCommand = configMessages[5];
		cannotUseSign = configMessages[6];
		isNotAPlayer = configMessages[7];
	}
	
	public String translateADEKPMessageSyntax(String string, String kitReplacement, String moneyReplacement, String commandReplacement) {
		string = string.replace(tagPlace, tag);
		string = string.replace(kitNamePlace, kitReplacement);
		string = string.replace(moneyPlace, moneyReplacement);
		string = string.replace(commandPlace, commandReplacement);
		
		string = ChatColor.translateAlternateColorCodes('&', string);
		
		return string;
	}
	
	public String getStartChatList() {
		return startChatList;
	}
	
	public String getEndChatList() {
		return endChatList;
	}
	
	public String getMoneyAddMsg() {
		return moneyAddMsg;
	}
	
	public String getCommandAddMsg() {
		return commandAddMsg;
	}
	
	public String getCannotUseCommand() {
		return cannotUseCommand;
	}
	
	public String getCannotUseSign() {
		return cannotUseSign;
	}
	
	public String getIsNotAPlayer() {
		return isNotAPlayer;
	}

}
