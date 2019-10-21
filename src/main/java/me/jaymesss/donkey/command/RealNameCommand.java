package me.jaymesss.donkey.command;

import me.jaymesss.donkey.Donkey;
import me.jaymesss.donkey.user.DonkeyUser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RealNameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length < 1) {
            commandSender.sendMessage(ChatColor.RED + "Usage: /realname <player>");
            return true;
        }
        boolean foundUser = false;
        for (DonkeyUser donkeyUser : Donkey.getInstance().getUserManager().getUsers().values()) {
            String nick = donkeyUser.getNickName().toLowerCase();
            if (!nick.contains(args[0])) {
                continue;
            }
            foundUser = true;
            commandSender.sendMessage(ChatColor.RED + args[0] + "'s real name is " + Bukkit.getOfflinePlayer(donkeyUser.getUserUUID()).getName());
        }
        if (!foundUser) {
            commandSender.sendMessage(ChatColor.RED + "Player not found.");
        }
        return false;
    }
}
