package me.jaymesss.donkey.command;

import me.jaymesss.donkey.Donkey;
import me.jaymesss.donkey.DonkeyAPI;
import me.jaymesss.donkey.user.DonkeyUser;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SaveDataCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("donkey.player.admin")) {
            commandSender.sendMessage(ChatColor.RED + "No permission.");
            return true;
        } else {
            Donkey.getInstance().saveData();
            for (DonkeyUser donkeyUsers : Donkey.getInstance().getUserManager().getUsers().values()) {
                commandSender.sendMessage(ChatColor.YELLOW + "Successfully saved " + ChatColor.GREEN + DonkeyAPI.getPlayerFromUUID(donkeyUsers.getUserUUID()).getName() + ChatColor.YELLOW + "'s data.");
            }
            commandSender.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "You have manually saved all player data!");
            DonkeyAPI.playerPerformFormat(commandSender, "Saved all player data", "donkey.player.admin");
            return true;
        }
    }
}
