package me.jaymesss.donkey.command;

import me.jaymesss.donkey.DonkeyAPI;
import me.jaymesss.donkey.listener.GgListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class SendPurchaseCommand implements CommandExecutor {

    public static void playerPurchaseSend(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.GREEN + "Thanks for shopping!");
        ItemStack border = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta borderMeta = border.getItemMeta();
        borderMeta.setDisplayName(DonkeyAPI.getColor() + DonkeyAPI.getServerName());
        border.setItemMeta(borderMeta);

        ItemStack thankyou = new ItemStack(Material.WOOL, 1, (short) 5);
        ItemMeta thankyouMeta = thankyou.getItemMeta();
        thankyouMeta.setDisplayName(DonkeyAPI.getColor2() + ChatColor.BOLD + "Thanks for shopping!");
        ArrayList<String> thankyouLore = new ArrayList<String>();
        thankyouLore.add("");
        thankyouLore.add(ChatColor.WHITE + "This is to confirm your purchase on " + DonkeyAPI.getColor2() + DonkeyAPI.getWebstore() + ChatColor.WHITE + " has gone through.");
        thankyouLore.add("");
        thankyouLore.add(ChatColor.GRAY.toString() + ChatColor.ITALIC + "Thanks for shopping!");
        thankyouLore.add("");
        thankyouMeta.setLore(thankyouLore);
        thankyou.setItemMeta(thankyouMeta);

        inventory.setItem(0, border);
        inventory.setItem(1, border);
        inventory.setItem(2, border);
        inventory.setItem(3, border);
        inventory.setItem(4, border);
        inventory.setItem(5, border);
        inventory.setItem(6, border);
        inventory.setItem(7, border);
        inventory.setItem(8, border);
        inventory.setItem(9, border);
        inventory.setItem(10, border);
        inventory.setItem(11, border);
        inventory.setItem(12, border);
        inventory.setItem(13, thankyou);
        inventory.setItem(14, border);
        inventory.setItem(15, border);
        inventory.setItem(16, border);
        inventory.setItem(17, border);
        inventory.setItem(18, border);
        inventory.setItem(19, border);
        inventory.setItem(20, border);
        inventory.setItem(21, border);
        inventory.setItem(22, border);
        inventory.setItem(23, border);
        inventory.setItem(24, border);
        inventory.setItem(25, border);
        inventory.setItem(26, border);

        player.openInventory(inventory);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            commandSender.sendMessage(ChatColor.RED + "Only console can execute this command.");
            return true;
        }
        if (args.length < 1) {
            commandSender.sendMessage(ChatColor.RED + "Usage: /sendpurchase <player>");
            return true;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
        if (offlinePlayer.isOnline()) {
            Player player = offlinePlayer.getPlayer();
            playerPurchaseSend(player);
            if (DonkeyAPI.getString("gg-train").equalsIgnoreCase("enabled")) {
                GgListener.startGgTrain();
                Bukkit.broadcastMessage(DonkeyAPI.getColor() + "[" + DonkeyAPI.getServerName() + "] " + DonkeyAPI.getDisplayName(player) + ChatColor.WHITE + " has started a gg train! If you type " + ChatColor.YELLOW + "'gg'" + ChatColor.WHITE + " within the next minute, you will get a " + DonkeyAPI.getString("reward-name") + ChatColor.WHITE + '.');
            }
        } else {
        }
        return false;
    }
}
