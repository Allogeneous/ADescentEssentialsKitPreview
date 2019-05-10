package me.Allogeneous.ADEKP;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.Kit;
import com.earth2me.essentials.MetaItemStack;
import com.earth2me.essentials.User;
import com.earth2me.essentials.textreader.IText;
import com.earth2me.essentials.textreader.KeywordReplacer;
import com.earth2me.essentials.textreader.SimpleTextInput;


public class ADEKPEssentialsKitDeconstructor {
	
	private final ADEKPMain plugin;
	private final Essentials essentialsInstance;
	
	public ADEKPEssentialsKitDeconstructor(ADEKPMain plugin, Essentials essentialsInstance) {
		this.plugin = plugin;
		this.essentialsInstance = essentialsInstance;
	}
	
	public ArrayList<ItemStack> getItemsFromKit(Player player, String kitName){
		ArrayList<ItemStack> kitItems;
		try {
			Kit kit = new Kit(kitName, essentialsInstance);
			User user = essentialsInstance.getUser(player);
			kitItems = getKitItems(user, kit.getItems());
		} catch (Exception e) {
			return new ArrayList<ItemStack>();
		}
		return kitItems;
	}
	
	public ArrayList<String> getMoneyFromKit(Player player, String kitName){
		ArrayList<String> kitMoney;
		try {
			Kit kit = new Kit(kitName, essentialsInstance);
			User user = essentialsInstance.getUser(player);
			kitMoney = getKitMoney(user, kit.getItems());
		} catch (Exception e) {
			return new ArrayList<String>();
		}
		return kitMoney;
	}
	
	public ArrayList<String> getCommandsFromKit(Player player, String kitName){
		ArrayList<String> kitCommands;
		try {
			Kit kit = new Kit(kitName, essentialsInstance);
			User user = essentialsInstance.getUser(player);
			kitCommands = getKitCommands(user, kit.getItems());
		} catch (Exception e) {
			return new ArrayList<String>();
		}
		return kitCommands;
	}
	
	private ArrayList<String> getKitCommands(final User user, final List<String> items) throws Exception {
		ArrayList<String> kitCommands = new ArrayList<>();
        try {
            IText input = new SimpleTextInput(items);
            IText output = new KeywordReplacer(input, user.getSource(), essentialsInstance, true, true);

            for (String kitItem : output.getLines()) {
            	 if (kitItem.startsWith("/")) {
            		 kitCommands.add(plugin.getMessageManager().translateADEKPMessageSyntax(plugin.getMessageManager().getCommandAddMsg(), "", "", kitItem));
                 }

            }
        } catch (Exception e) {
        	e.printStackTrace();
            return new ArrayList<String>();
        }
        return kitCommands;
    }
	
	private ArrayList<String> getKitMoney(final User user, final List<String> items) throws Exception {
		ArrayList<String> kitMoney = new ArrayList<>();
        try {
            IText input = new SimpleTextInput(items);
            IText output = new KeywordReplacer(input, user.getSource(), essentialsInstance, true, true);

            for (String kitItem : output.getLines()) {
                if (kitItem.startsWith(essentialsInstance.getSettings().getCurrencySymbol())) {
                    kitMoney.add(plugin.getMessageManager().translateADEKPMessageSyntax(plugin.getMessageManager().getMoneyAddMsg(), "", kitItem, ""));
                }

            }
        } catch (Exception e) {
        	e.printStackTrace();
            return new ArrayList<String>();
        }
        return kitMoney;
    }
	
	private ArrayList<ItemStack> getKitItems(final User user, final List<String> items) throws Exception {
		ArrayList<ItemStack> kitItems = new ArrayList<>();
        try {
            IText input = new SimpleTextInput(items);
            IText output = new KeywordReplacer(input, user.getSource(), essentialsInstance, true, true);

            final boolean allowUnsafe = essentialsInstance.getSettings().allowUnsafeEnchantments();
            for (String kitItem : output.getLines()) {
                if (kitItem.startsWith(essentialsInstance.getSettings().getCurrencySymbol())) {
                    continue;
                }

                if (kitItem.startsWith("/")) {
                    continue;
                }

                final String[] parts = kitItem.split(" +");
                final ItemStack parseStack = essentialsInstance.getItemDb().get(parts[0], parts.length > 1 ? Integer.parseInt(parts[1]) : 1);

                if (parseStack.getType() == Material.AIR) {
                    continue;
                }

                final MetaItemStack metaStack = new MetaItemStack(parseStack);

                if (parts.length > 2) {
                    metaStack.parseStringMeta(null, allowUnsafe, parts, 2, essentialsInstance);
                }
                kitItems.add(metaStack.getItemStack());
            }
        } catch (Exception e) {
            return new ArrayList<ItemStack>();
        }
        return kitItems;
    }
	
	

}
